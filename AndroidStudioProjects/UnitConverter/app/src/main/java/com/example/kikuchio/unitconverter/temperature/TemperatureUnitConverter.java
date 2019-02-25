package com.example.kikuchio.unitconverter.temperature;

import com.example.kikuchio.unitconverter.UnitConverter;

import java.util.HashMap;
import java.util.Map;

public class TemperatureUnitConverter implements UnitConverter<TemperatureUnit> {

    private static Map<TemperatureUnit, Function<Float, Float>> toCelsiusMap = new HashMap<>();
    private static Map<TemperatureUnit, Function<Float, Float>> fromCelsiusMap = new HashMap<>();

    static {
        toCelsiusMap.put(TemperatureUnit.FAHRENHEIT, f -> (f - 32) * (float)(5.0/9.0));
        toCelsiusMap.put(TemperatureUnit.KELVIN, k -> (k - 273.15f));
        fromCelsiusMap.put(TemperatureUnit.FAHRENHEIT, c -> (c * (float)(9.0/5.0)) + 32);
        fromCelsiusMap.put(TemperatureUnit.KELVIN, c -> (c + 273.15f));
    }

    @Override
    public float convert(TemperatureUnit fromUnit, float fromMagnitude, TemperatureUnit toUnit) {
        if(fromUnit.equals(toUnit))
            return fromMagnitude;
        if(TemperatureUnit.CELSIUS.equals(fromUnit))
            return fromCelsiusMap.get(toUnit).apply(fromMagnitude);
        if(TemperatureUnit.CELSIUS.equals(toUnit))
            return toCelsiusMap.get(fromUnit).apply(fromMagnitude);
        Float asCelsius = toCelsiusMap.get(fromUnit).apply(fromMagnitude);
        return fromCelsiusMap.get(toUnit).apply(asCelsius);
    }

    private interface Function<I, O> {
        O apply(I in);
    }
}
