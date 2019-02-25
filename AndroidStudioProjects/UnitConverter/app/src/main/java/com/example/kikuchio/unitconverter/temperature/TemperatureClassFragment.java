package com.example.kikuchio.unitconverter.temperature;

import com.example.kikuchio.unitconverter.UnitClassFragment;
import com.example.kikuchio.unitconverter.UnitConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TemperatureClassFragment extends UnitClassFragment {
    @Override
    protected List availableUnits() {
        return Collections.unmodifiableList(Arrays.asList(TemperatureUnit.values()));
    }

    @Override
    protected boolean acceptsNegatives() {
        return true;
    }

    @Override
    protected String unitClass() {
        return "TEMPERATURE";
    }

    @Override
    protected UnitConverter converter() {
        return new TemperatureUnitConverter();
    }
}
