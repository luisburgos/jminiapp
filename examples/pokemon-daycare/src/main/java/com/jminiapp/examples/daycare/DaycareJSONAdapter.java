package com.jminiapp.examples.daycare;

import com.jminiapp.core.adapters.JSONAdapter;

/**
 * Adapter to persist the full DaycareState object.
 */
public class DaycareJSONAdapter implements JSONAdapter<DaycareState> {

    @Override
    public Class<DaycareState> getstateClass() {
        return DaycareState.class;
    }
}