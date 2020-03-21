package com.zl.way.dp.decorator;

public class DecoratorDemo {
    public static void main(String[] args) {
        DecoratorComponent<String, String> component = new ConcreteComponent();
        component.operation("入参");
        System.out.println("--------------------");
        DecoratorComponent<String, String> decorator = new ConcreteDecorator(component);
        decorator.operation("入参");
    }

}

class ConcreteComponent implements DecoratorComponent<String, String> {
    @Override
    public String operation(String input) {
        System.out.println("执行ConcreteComponent类operation方法!");
        return null;
    }
}

class ConcreteDecorator extends Decorator<String, String> {
    public ConcreteDecorator(DecoratorComponent component) {
        super(component);
    }

    @Override
    public String operation(String input) {
        this.beforeOperation();
        super.operation(input);
        this.afterOperation();

        return null;
    }

    private void beforeOperation() {
        System.out.println("beforeOperation...");
    }

    private void afterOperation() {
        System.out.println("afterOperation...");
    }
}