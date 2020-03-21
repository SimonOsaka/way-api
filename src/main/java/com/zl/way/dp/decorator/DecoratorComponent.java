package com.zl.way.dp.decorator;

public interface DecoratorComponent<I, O> {
    O operation(I params);
}
