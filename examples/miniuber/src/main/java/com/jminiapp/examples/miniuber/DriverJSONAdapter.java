package com.jminiapp.examples.miniuber;

import com.jminiapp.core.adapters.JSONAdapter;

/**
 * JSON adapter for Driver objects.
 */
public class DriverJSONAdapter implements JSONAdapter<Driver> {

    @Override
    public Class<Driver> getstateClass() {
        return Driver.class;
    }
}
