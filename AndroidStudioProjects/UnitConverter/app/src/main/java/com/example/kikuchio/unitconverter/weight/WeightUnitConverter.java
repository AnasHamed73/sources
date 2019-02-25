package com.example.kikuchio.unitconverter.weight;

import com.example.kikuchio.unitconverter.UnitConverter;

import java.util.HashMap;
import java.util.Map;

public class WeightUnitConverter implements UnitConverter<WeightUnit> {

    private static Map<WeightUnit, Float> toGramsMultipliers = new HashMap<>();

    static {
        toGramsMultipliers.put(WeightUnit.MILLIGRAMS, 0.001f);
        toGramsMultipliers.put(WeightUnit.KILOGRAMS, 1000f);
        toGramsMultipliers.put(WeightUnit.POUNDS, 453.59237f);
        toGramsMultipliers.put(WeightUnit.OUNCES, 28.349523125f);
        toGramsMultipliers.put(WeightUnit.CARAT, 0.2f);
        toGramsMultipliers.put(WeightUnit.TONS, 907184.74f);
        toGramsMultipliers.put(WeightUnit.QUARTER, 11339.80925f);
        toGramsMultipliers.put(WeightUnit.STONE, 5669.904625f);
        toGramsMultipliers.put(WeightUnit.GRAIN, 0.06479891f);
    }

    @Override
    public float convert(WeightUnit fromUnit, float fromMagnitude, WeightUnit toUnit) {
        if(fromUnit.equals(toUnit))
            return fromMagnitude;
        float asMeters = fromMagnitude;
        if(!WeightUnit.GRAMS.equals(fromUnit))
            asMeters = fromMagnitude * toGramsMultipliers.get(fromUnit);
        if(WeightUnit.GRAMS.equals(toUnit))
            return asMeters;
        return asMeters * (1 / toGramsMultipliers.get(toUnit));
    }
}
