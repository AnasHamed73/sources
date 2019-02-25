package com.example.kikuchio.unitconverter.volume;

import com.example.kikuchio.unitconverter.UnitConverter;

import java.util.HashMap;
import java.util.Map;

public class VolumeUnitConverter implements UnitConverter<VolumeUnit> {

    private static Map<VolumeUnit, Float> toLiterMultipliers = new HashMap<>();

    static {
        toLiterMultipliers.put(VolumeUnit.GALLONS, 3.785411784f);
        toLiterMultipliers.put(VolumeUnit.QUARTS, 0.946352946f);
        toLiterMultipliers.put(VolumeUnit.PINTS, 0.473176473f);
        toLiterMultipliers.put(VolumeUnit.CUPS, 0.2365882365f);
        toLiterMultipliers.put(VolumeUnit.BARRELS, 119.2404712f);
        toLiterMultipliers.put(VolumeUnit.OUNCES, 0.0295735296f);
        toLiterMultipliers.put(VolumeUnit.DESSERT_SPN, 0.0098578432f);
        toLiterMultipliers.put(VolumeUnit.MILLILITERS, 0.001f);


    }

    @Override
    public float convert(VolumeUnit fromUnit, float fromMagnitude, VolumeUnit toUnit) {
        if(fromUnit.equals(toUnit))
            return fromMagnitude;
        float asMeters = fromMagnitude;
        if(!VolumeUnit.LITERS.equals(fromUnit))
            asMeters = fromMagnitude * toLiterMultipliers.get(fromUnit);
        if(VolumeUnit.LITERS.equals(toUnit))
            return asMeters;
        return asMeters * (1 / toLiterMultipliers.get(toUnit));
    }
}
