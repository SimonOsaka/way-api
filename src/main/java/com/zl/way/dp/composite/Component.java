package com.zl.way.dp.composite;

public interface Component {
    void add(Component c);

    void remove(Component c);

    void operation();
}
