package com.example.kikuchio.unitconverter.temperature;

public enum TemperatureUnit {

    CELSIUS("Celsius", "C"), FAHRENHEIT("Fahrenheit", "F"), KELVIN("Kelvin", "K");

    private String shortName;
    private String fullName;

    TemperatureUnit(String fullName, String shortName) {
        this.fullName = fullName;
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return fullName + " (" + shortName + ")";
    }

}
