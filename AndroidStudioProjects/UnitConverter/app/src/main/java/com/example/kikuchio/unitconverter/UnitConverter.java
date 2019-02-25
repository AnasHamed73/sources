package com.example.kikuchio.unitconverter;

public interface UnitConverter<T extends Enum<?>> {

    float convert(T fromUnit, float fromMagnitude, T toUnit);
}
