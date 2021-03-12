package com.cjunn.setool.dao.fluent.builder.frame;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.dao.fluent.builder.*;
import com.cjunn.setool.dao.fluent.builder.condition.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @ClassName AndOrFrame
 * @Description 流式QL语句构建条件面板
 * @Author cjunn
 * @Date 2021/3/10 22:17
 * @Version
 */
public class AndOrFrame <T extends BaseModel> extends AbstractOptFrame<TableFrame>
                                                                    implements ExtFrameProvider<T>,
                                                                                ActuatorProvider<T> {
    private WhereFrame parentFrame;
    private BiExps topBiExps;
    private BiExps lasBiExps;

    public AndOrFrame(BiExps leftBiExps,WhereFrame parentFrame,TableFrame topFrame) {
        super(topFrame);
        this.topBiExps=leftBiExps;
        this.lasBiExps=topBiExps;
        this.parentFrame=parentFrame;
    }

    private WhereFrame parentFrame(){
        return parentFrame;
    }

    @Override
    public String exportQL() {
        return topBiExps.exportQL();
    }



    ////////////////////////////AND操作////////////////////////////////////////
    public AndOrFrame<T> andLike(String name,Object value){
        return andLike(new SimpleQlField(name), Arrays.asList(new SimpleQlParameter(registPlaceHolder(name, value))));
    }
    public AndOrFrame<T> andIn(String name, List<Object> values){
        return andIn(new SimpleQlField(name), value2param(name,values));
    }
    public AndOrFrame<T> andBetween(String name,Object start,Object end){
        List<QlParameter> qlParameters = new ArrayList<>();
        qlParameters.add(new SimpleQlParameter(registPlaceHolder(name, start)));
        qlParameters.add(new SimpleQlParameter(registPlaceHolder(name, end)));
        return andBetween(new SimpleQlField(name), qlParameters);
    }
    public AndOrFrame<T> andNotIn(String name, List<Object> values){
        return andNotIn(new SimpleQlField(name), value2param(name,values));
    }
    public AndOrFrame<T> andEq(String name,Object value){
        return andEq(new SimpleQlField(name), Arrays.asList(new SimpleQlParameter(registPlaceHolder(name, value))));
    }
    public AndOrFrame<T> andTrue(){
        andLastBiAccept(new AndExps(new TrueCtdExps()));
        return this;
    }
    public AndOrFrame<T> andFalse(){
        andLastBiAccept(new AndExps(new FalseCtdExps()));
        return this;
    }
    public AndOrFrame<T> andGoe(String name,Object value){
        return andGoe(new SimpleQlField(name), Arrays.asList(new SimpleQlParameter(registPlaceHolder(name, value))));
    }
    public AndOrFrame<T> andGt(String name,Object value){
        return andGt(new SimpleQlField(name), Arrays.asList(new SimpleQlParameter(registPlaceHolder(name, value))));
    }
    public AndOrFrame<T> andLoe(String name,Object value){
        return andLoe(new SimpleQlField(name), Arrays.asList(new SimpleQlParameter(registPlaceHolder(name, value))));
    }
    public AndOrFrame<T> andLt(String name,Object value){
        return andLt(new SimpleQlField(name), Arrays.asList(new SimpleQlParameter(registPlaceHolder(name, value))));
    }
    public AndOrFrame<T> andNe(String name,Object value){
        return andNe(new SimpleQlField(name), Arrays.asList(new SimpleQlParameter(registPlaceHolder(name, value))));
    }
    public AndOrFrame<T> andIsNotNull(String name){
        return andIsNotNull(new SimpleQlField(name));
    }
    public AndOrFrame<T> andIsNull(String name){
        return andIsNull(new SimpleQlField(name));
    }
    protected AndOrFrame andEq(QlField qlField, List<QlParameter> qlParameters){
        andLastBiAccept(new AndExps(new EqCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> andGoe(QlField qlField, List<QlParameter> qlParameters){
        andLastBiAccept(new AndExps(new GoeCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> andGt(QlField qlField, List<QlParameter> qlParameters){
        andLastBiAccept(new AndExps(new GtCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> andLoe(QlField qlField, List<QlParameter> qlParameters){
        andLastBiAccept(new AndExps(new LoeCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> andLt(QlField qlField, List<QlParameter> qlParameters){
        andLastBiAccept(new AndExps(new LtCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> andNe(QlField qlField, List<QlParameter> qlParameters){
        andLastBiAccept(new AndExps(new NeCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> andIsNotNull(QlField qlField){
        andLastBiAccept(new AndExps(new IsNotNullCdtExps(qlField)));
        return this;
    }
    protected AndOrFrame<T> andIsNull(QlField qlField){
        andLastBiAccept(new AndExps(new IsNullCdtExps(qlField)));
        return this;
    }
    protected AndOrFrame<T> andNotIn(QlField qlField, List<QlParameter> qlParameters){
        andLastBiAccept(new AndExps(new NotInCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> andBetween(QlField qlField, List<QlParameter> qlParameters){
        andLastBiAccept(new AndExps(new BetweenCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> andIn(QlField qlField, List<QlParameter> qlParameters){
        andLastBiAccept(new AndExps(new InCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> andLike(QlField qlField, List<QlParameter> qlParameters){
        andLastBiAccept(new AndExps(new LikeCdtExps(qlField, qlParameters)));
        return this;
    }

    public AndOrFrame<T> andInside(Consumer<AndOrFrame> consumer){
        RightBiExps rightBiExps = new RightBiExps();
        consumer.accept(new InnerAndOrFrame<T>(rightBiExps,this.topFrame()));
        andLastBiAccept(new AndExps(new BracketExps(rightBiExps)));
        return this;
    }

    ///////////////////////////OR操作//////////////////////////////////////
    public AndOrFrame<T> orLike(String name,Object value){
        return orLike(new SimpleQlField(name), Arrays.asList(new SimpleQlParameter(registPlaceHolder(name, value))));
    }
    public AndOrFrame<T> orIn(String name, List<Object> values){
        return orIn(new SimpleQlField(name), value2param(name,values));
    }
    public AndOrFrame<T> orBetween(String name,Object start,Object end){
        List<QlParameter> qlParameters = new ArrayList<>();
        qlParameters.add(new SimpleQlParameter(registPlaceHolder(name, start)));
        qlParameters.add(new SimpleQlParameter(registPlaceHolder(name, end)));
        return orBetween(new SimpleQlField(name), qlParameters);
    }
    public AndOrFrame<T> orNotIn(String name, List<Object> values){
        return orNotIn(new SimpleQlField(name), value2param(name,values));
    }
    public AndOrFrame<T> orEq(String name,Object value){
        return orEq(new SimpleQlField(name), Arrays.asList(new SimpleQlParameter(registPlaceHolder(name, value))));
    }
    public AndOrFrame<T> orTrue(){
        orLastBiAccept(new OrExps(new TrueCtdExps()));
        return this;
    }
    public AndOrFrame<T> orFalse(){
        orLastBiAccept(new OrExps(new FalseCtdExps()));
        return this;
    }
    public AndOrFrame<T> orGoe(String name,Object value){
        return orGoe(new SimpleQlField(name), Arrays.asList(new SimpleQlParameter(registPlaceHolder(name, value))));
    }
    public AndOrFrame<T> orGt(String name,Object value){
        return orGt(new SimpleQlField(name), Arrays.asList(new SimpleQlParameter(registPlaceHolder(name, value))));
    }
    public AndOrFrame<T> orLoe(String name,Object value){
        return orLoe(new SimpleQlField(name), Arrays.asList(new SimpleQlParameter(registPlaceHolder(name, value))));
    }
    public AndOrFrame<T> orLt(String name,Object value){
        return orLt(new SimpleQlField(name), Arrays.asList(new SimpleQlParameter(registPlaceHolder(name, value))));
    }
    public AndOrFrame<T> orNe(String name,Object value){
        return orNe(new SimpleQlField(name), Arrays.asList(new SimpleQlParameter(registPlaceHolder(name, value))));
    }
    public AndOrFrame<T> orIsNotNull(String name){
        return orIsNotNull(new SimpleQlField(name));
    }
    public AndOrFrame<T> orIsNull(String name){
        return orIsNull(new SimpleQlField(name));
    }

    protected AndOrFrame<T> orEq(QlField qlField, List<QlParameter> qlParameters){
        orLastBiAccept(new OrExps(new EqCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> orGoe(QlField qlField, List<QlParameter> qlParameters){
        orLastBiAccept(new OrExps(new GoeCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> orGt(QlField qlField, List<QlParameter> qlParameters){
        orLastBiAccept(new OrExps(new GtCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> orLoe(QlField qlField, List<QlParameter> qlParameters){
        orLastBiAccept(new OrExps(new LoeCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> orLt(QlField qlField, List<QlParameter> qlParameters){
        orLastBiAccept(new OrExps(new LtCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> orNe(QlField qlField, List<QlParameter> qlParameters){
        orLastBiAccept(new OrExps(new NeCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> orIsNotNull(QlField qlField){
        orLastBiAccept(new OrExps(new IsNotNullCdtExps(qlField)));
        return this;
    }
    protected AndOrFrame<T> orIsNull(QlField qlField){
        orLastBiAccept(new OrExps(new IsNullCdtExps(qlField)));
        return this;
    }
    protected AndOrFrame<T> orNotIn(QlField qlField, List<QlParameter> qlParameters){
        orLastBiAccept(new OrExps(new NotInCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> orBetween(QlField qlField, List<QlParameter> qlParameters){
        orLastBiAccept(new OrExps(new BetweenCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> orIn(QlField qlField, List<QlParameter> qlParameters){
        orLastBiAccept(new OrExps(new InCdtExps(qlField, qlParameters)));
        return this;
    }
    protected AndOrFrame<T> orLike(QlField qlField, List<QlParameter> qlParameters){
        orLastBiAccept(new OrExps(new LikeCdtExps(qlField, qlParameters)));
        return this;
    }

    public AndOrFrame<T> orInside(Consumer<AndOrFrame> consumer){
        RightBiExps rightBiExps = new RightBiExps();
        consumer.accept(new InnerAndOrFrame<T>(rightBiExps,this.topFrame()));
        orLastBiAccept(new OrExps(new BracketExps(rightBiExps)));
        return this;
    }

    public AndOrFrame<T> and(){
        return parentFrame().and();
    }
    public AndOrFrame<T> or(){
        return parentFrame().or();
    }


    protected List<QlParameter> value2param(String name, List<Object> values){
        List<QlParameter> qlParameters = values.stream()
                .map(o -> registPlaceHolder(name, o))
                .map(s -> new SimpleQlParameter(s))
                .collect(Collectors.toList());
        return qlParameters;
    }
    protected String registPlaceHolder(String name,Object value){
        return this.topFrame().registPlaceHolderAndValue(name,value);
    }
    protected void andLastBiAccept(AndExps andExps){
        lasBiExps=lasBiExps.accept(andExps);
    }
    protected void orLastBiAccept(OrExps orExps){
        lasBiExps=lasBiExps.accept(orExps);
    }


}
