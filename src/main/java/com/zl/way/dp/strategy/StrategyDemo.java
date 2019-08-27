package com.zl.way.dp.strategy;

public class StrategyDemo {

    public static void main(String[] args) {
        StrategyContext<String, String> sc = new StrategyContext<>();
        sc.setStrategy(new BracketsStrategy());
        String str = sc.execute("- -");
        System.out.println(str);

        sc.setStrategy(new QuotesStrategy());
        str = sc.execute("- -");
        System.out.println(str);
    }
}

class BracketsStrategy implements Strategy<String, String> {
    @Override
    public String strategyMethod(String params) {
        return "b" + params + "b";
    }

}

class QuotesStrategy implements Strategy<String, String> {
    @Override
    public String strategyMethod(String params) {
        return "Y" + params + "Y";
    }
}