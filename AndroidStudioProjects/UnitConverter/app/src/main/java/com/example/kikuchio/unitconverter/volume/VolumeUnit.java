package com.example.kikuchio.unitconverter.volume;

public enum VolumeUnit {

    LITERS("Liters", "L"), OUNCES("Ounces", "oz"), GALLONS("Gallons", "gal"), QUARTS("Quarts", "qt"), PINTS("Pint", "pt"),
    MILLILITERS("Milliliters", "ml"), CUPS("Cup", "cup"), BARRELS("Barrel", "bbl"), DESSERT_SPN("Dessert Spoon", "dsp");

    private String shortName;
    private String fullName;

    VolumeUnit(String fullName, String shortName) {
        this.fullName = fullName;
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return fullName + " (" + shortName + ")";
    }
}
