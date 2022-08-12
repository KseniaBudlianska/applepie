package com.qecodingcamp.applepie.domain

enum class MeasurementUnit {
    UNIT,
    TBSP,
    CUP,
    GRAM,
    OUNCE,
    LITER,
    MILLILITER
}

enum class VolumeMeasurementUnit(
    override val inboundConversionFactor: Double
): Unit {
    LITER(1000.0),
    MILLILITER(1.0),
    FLUID_OUNCE(29.5735);
}

enum class MassMeasurementUnit(
    override val inboundConversionFactor: Double
): Unit {
    GRAM(0.001),
    KILOGRAM(1.0),
    OUNCE(0.0283495);
}

interface Unit {
    val inboundConversionFactor: Double

    fun getFactor(): Double {
        return this.inboundConversionFactor
    }
}
