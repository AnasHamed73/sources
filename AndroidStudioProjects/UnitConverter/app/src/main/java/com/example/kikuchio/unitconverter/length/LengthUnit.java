package com.example.kikuchio.unitconverter.length;

public enum LengthUnit {

    METERS("Meters", "m"), INCHES("Inches", "in"), FEET("feet", "ft"),
    MILES("Miles", "mi"), YARDS("Yards", "yd"), KILOMETERS("Kilometers", "km"),
    CENTIMETERS("Centimeters", "cm");

    private String shortName;
    private String fullName;

    LengthUnit(String fullName, String shortName) {
        this.fullName = fullName;
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return fullName + " (" + shortName + ")";
    }
}
