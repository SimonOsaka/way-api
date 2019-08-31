package com.zl.way.dp.decorator;

public class Decorator<I, O> implements DecoratorComponent<I, O> {
    private DecoratorComponent<I, O> component;

    public Decorator(DecoratorComponent<I, O> component) {
        this.component = component;
    }

    @Override
    public O operation(I params) {
        return component.operation(params);
    }
}
