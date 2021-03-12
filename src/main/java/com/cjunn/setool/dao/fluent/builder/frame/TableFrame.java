package com.cjunn.setool.dao.fluent.builder.frame;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.dao.fluent.builder.RightBiExps;
import com.cjunn.setool.dao.fluent.builder.SpaceBiExps;
import com.cjunn.setool.dao.fluent.support.IFluentSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TableFrame
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 13:57
 * @Version
 */
public class TableFrame<T extends BaseModel> extends AbstractOptFrame<TableFrame> implements
                                                                          SelectFrameProvider<T>,
                                                                          WhereFrameProvider<T>,
                                                                          UpdateFrameProvider<T>,
                                                                          DeleteFrameProvider<T>,
                                                                          ExtFrameProvider<T>,
                                                                          ActuatorProvider<T>{

    private IFluentSupport iFluentSupport;

    public Class<T> getClz() {
        return clz;
    }


    private Class<T> clz;

    public Map<String, Object> getPlaceHolderAndValue() {
        return placeHolderAndValue;
    }


    private Map<String,Object> placeHolderAndValue=new HashMap<>();
    private Map<String,Integer> placeHolderSerialize=new HashMap<>();
    private UpdateFrame updateFrame;
    private DeleteFrame deleteFrame;
    private SelectFrame selectFrame;
    private WhereFrame whereFrame;
    private ExtFrame extFrame;
    public TableFrame(Class<T> clz, IFluentSupport iFluentSupport) {
        super(null);
        this.clz=clz;
        this.iFluentSupport=iFluentSupport;
    }



    @Override
    public String exportQL() {
        RightBiExps rightBiExps=new RightBiExps();
        rightBiExps.accept(new SpaceBiExps(selectFrame))
                   .accept(new SpaceBiExps(whereFrame))
                   .accept(new SpaceBiExps(extFrame));
        return rightBiExps.exportQL();
    }

    @Override
    public SelectFrame<T> select(){
        return selectFrame=new SelectFrame<T>(this,clz);
    }
    @Override
    public WhereFrame<T> where(){
        return whereFrame=new WhereFrame<T>(this);
    }
    @Override
    public UpdateFrame update(){
        return updateFrame=new UpdateFrame(this);
    }
    @Override
    public DeleteFrame delete(){
        return deleteFrame=new DeleteFrame(this);
    }
    @Override
    public ExtFrame ext(){
        return extFrame=new ExtFrame(this);
    }

    @Override
    public List<T> selectCmd(){
        if(selectFrame==null){
            selectFrame=new SelectFrame(this,clz);
            selectFrame.all();
        }
        return iFluentSupport.selectCmd(this);
    }
    @Override
    public T selectOneCmd(){
        if(selectFrame==null){
            selectFrame=new SelectFrame(this,clz);
            selectFrame.all();
        }
        updateFrame=null;
        deleteFrame=null;
        extFrame=extFrame==null?new ExtFrame<T>(this):extFrame;
        extFrame.limitOne();
        return iFluentSupport.selectOneCmd(this);
    }
    @Override
    public Integer countCmd(){
        selectFrame= selectFrame==null?new SelectFrame(this,clz):selectFrame;
        selectFrame.count();
        return iFluentSupport.countCmd(this);
    }
    @Override
    public Integer updateModel(){
        return iFluentSupport.updateModel(this);
    }
    @Override
    public Integer updateWhere(){
        return iFluentSupport.updateWhere(this);
    }
    @Override
    public Integer deleteByPk(){
        return iFluentSupport.updateWhere(this);
    }
    @Override
    public Integer deleteWhere(){
        return iFluentSupport.updateWhere(this);
    }

    protected String registPlaceHolderAndValue(String name,Object value){
        Integer compute = placeHolderSerialize.compute(name, (s, integer) -> integer == null ? 0 : ++integer);
        String placeHolder = name + compute.toString();
        placeHolderAndValue.put(placeHolder, value);
        return "#{"+placeHolder+"}";
    }

}
