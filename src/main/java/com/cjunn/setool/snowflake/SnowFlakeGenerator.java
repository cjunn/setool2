package com.cjunn.setool.snowflake;

import com.cjunn.setool.core.generator.IGenerator;

public class SnowFlakeGenerator implements IGenerator<Long> {
    public static final int NODE_SHIFT = 10;
    public static final int SEQ_SHIFT = 12;
    public static final short MAX_NODE = 1024;
    public static final short MAX_SEQUENCE = 4096;
    private short sequence;
    private long referenceTime;
    private int node;
    private NodeGetter nodeGetter;
    public SnowFlakeGenerator(){

    }

    public SnowFlakeGenerator(NodeGetter nodeGetter){
        this.nodeGetter=nodeGetter;
    }

    public void setNode(int node) {
        if (node >= 0 && node <= MAX_NODE) {
            this.node = node;
        } else {
            throw new IllegalArgumentException(String.format("node must be between %s and %s", 0, MAX_NODE));
        }
    }
    public void setNodeGetter(NodeGetter nodeGetter) {
        this.nodeGetter = nodeGetter;
    }


    public synchronized long calNext() {
        long nowTimeStamp = System.currentTimeMillis();
        if (nowTimeStamp < this.referenceTime) {
            throw new RuntimeException(String.format("Last referenceTime %s is after reference time %s", this.referenceTime, nowTimeStamp));
        } else {
            if (nowTimeStamp > this.referenceTime) {
                this.sequence = 0;
            } else {
                if (this.sequence >= MAX_SEQUENCE) {
                    throw new RuntimeException("Sequence exhausted at " + this.sequence);
                }
                ++this.sequence;
            }
            this.referenceTime = nowTimeStamp;
            return nowTimeStamp << NODE_SHIFT << SEQ_SHIFT | (long)(this.getNode() << SEQ_SHIFT) | ((long)this.sequence);
        }
    }

    public int getNode() {
        return this.nodeGetter != null ? this.nodeGetter.getNode() : this.node;
    }

    @Override
    public Long generate(Class<?> clz) {
        return this.next();
    }

    @Override
    public Long next() {
        return calNext();
    }
}
