package com.cjunn.setool.snowflake;

public class DefaultNodeGetter implements NodeGetter {
    public DefaultNodeGetter(int node){
        this.node=node;
    }
    private int node=0;
    @Override
    public int getNode() {
        return 0;
    }
}
