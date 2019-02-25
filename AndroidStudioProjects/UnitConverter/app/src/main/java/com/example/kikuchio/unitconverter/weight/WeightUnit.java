package com.example.kikuchio.unitconverter.weight;

public enum WeightUnit {

    GRAMS("Grams", "g"), POUNDS("Pounds", "lbs"), OUNCES("Ounces", "oz"), MILLIGRAMS("Milligrams", "mg"),
    KILOGRAMS("Kilograms", "kg"), CARAT("Carat", "ct"),
    TONS("Tons", "t"), QUARTER("Quarter", "qr"), STONE("Stone", "stn"), GRAIN("Grain", "grn");

    private String shortName;
    private String fullName;

    WeightUnit(String fullName, String shortName) {
        this.fullName = fullName;
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return fullName + " (" + shortName + ")";
    }
}
