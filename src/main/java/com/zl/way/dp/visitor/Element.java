package com.zl.way.dp.visitor;

/**
 * 元素<br>
 * 如果有特殊处理，创建新接口继承此接口
 * 
 * @author xuzhongliang
 */
public interface Element {
    /**
     * 接收访问者
     * 
     * @param visitor
     */
    void accept(Visitor visitor);

}
