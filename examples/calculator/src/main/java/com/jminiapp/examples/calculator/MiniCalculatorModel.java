package com.jminiapp.examples.calculator;

public class MiniCalculatorModel { 
    private double result;

    public MiniCalculatorModel() {
        this.result = 0.0;
    }

    public MiniCalculatorModel(double result) {
        this.result = result;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public double add(double number1, double number2) {
        result = number1 + number2;
        return result;
    }

    public double subtract(double number1, double number2) {
        result = number1 - number2;
        return result;
    }

    public double multiply(double number1, double number2) {
        result = number1 * number2;
        return result;
    }

    public double divide(double number1, double number2) {
        if (number2 == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        result = number1 / number2;
        return result;
    }

    public double addToResult(double number) {
        result += number;
        return result;
    }

    public double subtractFromResult(double number) {
        result -= number;
        return result;
    }

    public double multiplyResultBy(double number) {
        result *= number;
        return result;
    }

    public double divideResultBy(double number) {
        if (number == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        result /= number;
        return result;
    }

    public void reset() {
        this.result = 0.0;
    }

    @Override
    public String toString() {
        return "MiniCalculatorModel{result=" + result + "}";
    }
}