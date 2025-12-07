package com.jminiapp.examples.calculator;

import com.jminiapp.core.adapters.JSONAdapter;

public class CalculatorJSONAdapter implements JSONAdapter<MiniCalculatorModel> {

    @Override
    public Class<MiniCalculatorModel> getstateClass() {
        return MiniCalculatorModel.class;
    }
}