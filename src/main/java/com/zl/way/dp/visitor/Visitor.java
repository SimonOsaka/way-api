package com.zl.way.dp.visitor;

/**
 * 访问者<br>
 * 如果有特殊处理，创建新接口继承此接口
 * 
 * @author xuzhongliang
 */
public interface Visitor {
    /**
     * 访问元素<br>
     * 无返回，业务具体处理在visit方法内，根据element参数处理业务逻辑
     * 
     * @param element
     */
    void visit(Element element);

}
