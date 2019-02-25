package com.example.kikuchio.unitconverter.volume;

import com.example.kikuchio.unitconverter.UnitClassFragment;
import com.example.kikuchio.unitconverter.UnitConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VolumeClassFragment extends UnitClassFragment<VolumeUnit> {
    @Override
    protected List<VolumeUnit> availableUnits() {
        return Collections.unmodifiableList(Arrays.asList(VolumeUnit.values()));
    }

    @Override
    protected boolean acceptsNegatives() {
        return false;
    }

    @Override
    protected String unitClass() {
        return "VOLUME";
    }

    @Override
    protected UnitConverter<VolumeUnit> converter() {
        return new VolumeUnitConverter();
    }
}
