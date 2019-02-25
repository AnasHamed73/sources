package com.example.kikuchio.unitconverter.length;

import com.example.kikuchio.unitconverter.UnitClassFragment;
import com.example.kikuchio.unitconverter.UnitConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LengthClassFragment extends UnitClassFragment<LengthUnit> {
    private static final String CLASS = "LENGTH";

    @Override
    protected List<LengthUnit> availableUnits() {
        return Collections.unmodifiableList(Arrays.asList(LengthUnit.values()));
    }

    @Override
    protected boolean acceptsNegatives() {
        return false;
    }

    @Override
    protected String unitClass() {
        return CLASS;
    }

    @Override
    protected UnitConverter<LengthUnit> converter() {
        return new LengthUnitConverter();
    }
}
