package com.example.kikuchio.unitconverter.weight;

import com.example.kikuchio.unitconverter.UnitClassFragment;
import com.example.kikuchio.unitconverter.UnitConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WeightClassFragment extends UnitClassFragment<WeightUnit> {

    @Override
    protected List<WeightUnit> availableUnits() {
        return Collections.unmodifiableList(Arrays.asList(WeightUnit.values()));
    }

    @Override
    protected boolean acceptsNegatives() {
        return false;
    }

    @Override
    protected String unitClass() {
        return "WEIGHT";
    }

    @Override
    protected UnitConverter<WeightUnit> converter() {
        return new WeightUnitConverter();
    }
}
