package com.cjunn.setool.dao.fluent.builder.frame;

import com.cjunn.setool.dao.fluent.builder.RightBiExps;
import com.cjunn.setool.dao.fluent.builder.SpaceBiExps;
import com.cjunn.setool.dao.fluent.support.IFluentSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TableFrame
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 13:57
 * @Version
 */
public class TableFrame extends AbstractOptFrame<TableFrame> implements SelectFrameProvider,
                                                                          WhereFrameProvider,
                                                                          UpdateFrameProvider,
                                                                          DeleteFrameProvider,
                                                                          ExtFrameProvider,
                                                                          ActuatorProvider{

    private IFluentSupport iFluentSupport;
    private Class<?> clz;
    private Map<String,Object> placeHolderAndValue=new HashMap<>();
    private Map<String,Integer> placeHolderSerialize=new HashMap<>();
    private UpdateFrame updateFrame;
    private DeleteFrame deleteFrame;
    private SelectFrame selectFrame;
    private WhereFrame whereFrame;
    private ExtFrame extFrame;
    public TableFrame(Class<?> clz, IFluentSupport iFluentSupport) {
        super(null);
        this.clz=clz;
        this.iFluentSupport=iFluentSupport;
    }

    public void execute(){
        iFluentSupport.execute(this);
    }

    @Override
    public String exportQL() {
        RightBiExps rightBiExps=new RightBiExps();
        rightBiExps.accept(new SpaceBiExps(selectFrame))
                   .accept(new SpaceBiExps(whereFrame))
                   .accept(new SpaceBiExps(extFrame));


        return rightBiExps.exportQL();
    }

    public SelectFrame select(){
        return selectFrame=new SelectFrame(this,clz);
    }
    public WhereFrame where(){
        return whereFrame=new WhereFrame(this);
    }
    public UpdateFrame update(){
        return updateFrame=new UpdateFrame(this);
    }
    public DeleteFrame delete(){
        return deleteFrame=new DeleteFrame(this);
    }
    public ExtFrame ext(){
        return extFrame=new ExtFrame(this);
    }

    protected String registPlaceHolderAndValue(String name,Object value){
        Integer compute = placeHolderSerialize.compute(name, (s, integer) -> integer == null ? 0 : ++integer);
        String placeHolder = name + compute.toString();
        placeHolderAndValue.put(placeHolder, value);
        return "#{"+placeHolder+"}";
    }

}
