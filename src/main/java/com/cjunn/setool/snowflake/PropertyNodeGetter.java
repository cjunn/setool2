package com.cjunn.setool.snowflake;

import org.springframework.core.env.Environment;

/**
 * @ClassName PropertyNodeGetter
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/7 16:09
 * @Version
 */
public class PropertyNodeGetter implements NodeGetter {
    private final int nodeNum;
    public PropertyNodeGetter(int nodeNum){
        this.nodeNum=nodeNum;
    }
    @Override
    public int getNode() {
        return nodeNum;
    }

}
