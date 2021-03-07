package com.cjunn.setool.utils;

public class Pair<A, B>  {
    private final A val0;
    private final B val1;
    public Pair(A value0, B value1) {
        this.val0 = value0;
        this.val1 = value1;
    }
    public static <A, B> Pair<A, B> with(A value0, B value1) {
        return new Pair(value0, value1);
    }
    public A getValue0() {
        return this.val0;
    }
    public B getValue1() {
        return this.val1;
    }
}
