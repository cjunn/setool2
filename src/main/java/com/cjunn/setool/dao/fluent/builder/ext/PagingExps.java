package com.cjunn.setool.dao.fluent.builder.ext;

import com.cjunn.setool.dao.fluent.builder.Exps;

/**
 * @ClassName PagingExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 22:52
 * @Version
 */
public class PagingExps implements Exps {
    private Integer page;
    private Integer rows;

    public void setLimitOne(boolean limitOne) {
        this.limitOne = limitOne;
    }

    private boolean limitOne=false;
    public PagingExps(){

    }

    public void setPageRow(Integer page,Integer row){
        this.page=page;
        this.rows=rows;
        this.limitOne=false;
    }



    @Override
    public String exportQL() {
        if(limitOne==true){
            return "LIMIT 1";
        }
        if(page!=null&&rows!=null){
            int i = (page - 1) * rows;
            return "LIMIT "+i+" "+rows;
        }
        return "";
    }
}
