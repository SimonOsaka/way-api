package com.zl.way.dp.strategy;

/**
 * 策略模式
 * 
 * @author xuzhongliang
 */
public class StrategyContext<P, R> {
    private Strategy<P, R> strategy;

    public void setStrategy(Strategy<P, R> strategy) {
        this.strategy = strategy;
    }

    public R execute(P params) {
        return this.strategy.strategyMethod(params);
    }
}
