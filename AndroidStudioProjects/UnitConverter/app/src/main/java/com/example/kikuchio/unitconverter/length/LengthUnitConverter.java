package com.example.kikuchio.unitconverter.length;

import com.example.kikuchio.unitconverter.UnitConverter;

import java.util.HashMap;
import java.util.Map;

public class LengthUnitConverter implements UnitConverter<LengthUnit> {

    private static Map<LengthUnit, Float> toMeterMultipliers = new HashMap<>();

    static {
        toMeterMultipliers.put(LengthUnit.INCHES, 0.0254f);
        toMeterMultipliers.put(LengthUnit.FEET, 0.3048f);
        toMeterMultipliers.put(LengthUnit.MILES, 1609.344f);
        toMeterMultipliers.put(LengthUnit.YARDS, 0.9144f);
        toMeterMultipliers.put(LengthUnit.CENTIMETERS, 0.01f);
        toMeterMultipliers.put(LengthUnit.KILOMETERS, 1000f);
    }

    @Override
    public float convert(LengthUnit fromUnit, float fromMagnitude, LengthUnit toUnit) {
        if(fromUnit.equals(toUnit))
            return fromMagnitude;
        float asMeters = fromMagnitude;
        if(!LengthUnit.METERS.equals(fromUnit))
            asMeters = fromMagnitude * toMeterMultipliers.get(fromUnit);
        if(LengthUnit.METERS.equals(toUnit))
            return asMeters;
        return asMeters * (1 / toMeterMultipliers.get(toUnit));
    }

}
