package com.jminiapp.examples.damagecalculator;

import com.jminiapp.core.persistence.JSONAdapter;
import java.util.List;

public class DamageJSONAdapter implements JSONAdapter<DamageState> {

    @Override
    public Class<DamageState> getStateClass() {
        return DamageState.class;
    }

    @Override
    public List<DamageState> getStateObjectsToSave(DamageState state) {
        return List.of(state);
    }
}
