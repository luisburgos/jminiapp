p public static void main(String[] args) {
        JMiniAppRunner
                .forApp(DamageCalculatorApp.class)
                .withState(DamageState.class)
                .withAdapters(new DamageJSONAdapter())
                .run(args);
    }
}
