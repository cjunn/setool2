package com.cjunn.setool.dao.fluent.builder.frame;

import com.cjunn.setool.dao.fluent.builder.*;
import com.cjunn.setool.dao.fluent.builder.ql.WhereExps;

/**
 * @ClassName WhereFrame
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 14:23
 * @Version
 */
public class WhereFrame extends AbstractOptFrame<TableFrame> implements SelectFrameProvider{
    private static final String AND = "AND";
    private static final String OR = "OR";
    private String lineNowType;
    private BiExps lineLastBiExps;
    private BiExps topLastBigExps=new RightBiExps();
    private Exps topExps=new WhereExps(topLastBigExps);

    public WhereFrame(TableFrame topFrame) {
        super(topFrame);
    }

    public void packExps(){
        if(lineLastBiExps==null){
            return ;
        }
        if(AND.equals(lineNowType)){
            BracketExps bracketExps = new BracketExps(lineLastBiExps);
            topLastBigExps = topLastBigExps.accept(new AndExps(bracketExps));
        }else if(OR.equals(lineNowType)){
            BracketExps bracketExps = new BracketExps(lineLastBiExps);
            topLastBigExps = topLastBigExps.accept(new OrExps(bracketExps));
        }
        return ;
    }

    public AndOrFrame and(){
        this.lineNowType=AND;

        AndOrFrame andOrFrame = new AndOrFrame(lineLastBiExps = new RightBiExps(), this, topFrame());
        packExps();
        return andOrFrame;

    }
    public AndOrFrame or(){
        this.lineNowType=OR;
        packExps();
        return new AndOrFrame(lineLastBiExps=new RightBiExps(),this,topFrame());
    }

    @Override
    public String exportQL() {
        return topExps.exportQL();
    }
}
