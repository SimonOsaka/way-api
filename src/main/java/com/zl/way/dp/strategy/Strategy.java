package com.zl.way.dp.strategy;

public interface Strategy<P, R> {
    R strategyMethod(P params);
}
