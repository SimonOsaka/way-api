package com.zl.way.dp.visitor;

public class VisitorDemo {
    public static void main(String[] args) {

        VisitorStructure vs = new VisitorStructure();
        vs.add(new ConcreteElementA());
        vs.add(new ConcreteElementB());

        vs.accept(new ConcreteVisitorA());

        vs.accept(new ConcreteVisitorB());
    }
}

class ConcreteElementA implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

class ConcreteElementB implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

class ConcreteVisitorA implements Visitor {
    @Override
    public void visit(Element element) {
        System.out.println("Visitor A: " + element);
    }

}

class ConcreteVisitorB implements Visitor {
    @Override
    public void visit(Element element) {
        System.out.println("Visitor B: " + element);
    }
}