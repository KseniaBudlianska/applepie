package com.qecodingcamp.applepie.util

import com.qecodingcamp.applepie.domain.MeasurementUnit
import com.qecodingcamp.applepie.domain.MeasurementUnit.GRAM
import com.qecodingcamp.applepie.domain.MeasurementUnit.LITER
import com.qecodingcamp.applepie.domain.MeasurementUnit.MILLILITER
import com.qecodingcamp.applepie.domain.MeasurementUnit.OUNCE
import com.qecodingcamp.applepie.domain.Unit
import java.util.InputMismatchException
import kotlin.math.pow
import kotlin.math.roundToInt

class SmartUnitConverter {

    fun smarterConverter(amount: Double, originUnit: Unit, convertedUnit: Unit): Double {
        val inboundConversionFactor = originUnit.getFactor()  // Unit -> Base
        val outboundConversionFactor = 1 / convertedUnit.getFactor() // Base -> New unit
        if (originUnit.javaClass != convertedUnit.javaClass) {
            throw InputMismatchException("Not the same types.")
        } else return roundToTwoDecimalPlaces(amount * inboundConversionFactor * outboundConversionFactor, 2)
    }

    fun smartConvert(amount: Double, originUnit: MeasurementUnit, convertedUnit: MeasurementUnit): Double {
        return when {
            (originUnit == GRAM) && (convertedUnit == OUNCE) -> convert(amount, ConverterType.GRAMS_TO_OUNCES)
            (originUnit == OUNCE) && (convertedUnit == GRAM) -> convert(amount, ConverterType.OUNCES_TO_GRAMS)
            (originUnit == MILLILITER) && (convertedUnit == LITER) -> convert(amount, ConverterType.MILLILITERS_TO_LITERS)
            (originUnit == LITER) && (convertedUnit == MILLILITER) -> convert(amount, ConverterType.LITERS_TO_MILLILITERS)
            else -> throw IllegalStateException("Not supported conversion: from $originUnit to $convertedUnit.")
        }
    }

    private fun convert(weight: Double, converterType: ConverterType): Double {
        return if (weight >= 0) {
            val newWeight = weight * converterType.conversionRatio
            roundToTwoDecimalPlaces(newWeight, decimalPlaces = 2)
        } else {
            throw NumberFormatException("Negative weight cannot be converted.")
        }
    }

    private fun roundToTwoDecimalPlaces(number: Double, decimalPlaces: Int): Double {
        val shift = 10.0.pow(decimalPlaces.toDouble())
        return (number * shift).roundToInt().toDouble() / shift
    }

    private enum class ConverterType(val conversionRatio: Double) {
        MILLILITERS_TO_LITERS(0.001),
        LITERS_TO_MILLILITERS(1000.0),
        GRAMS_TO_OUNCES(0.035274),
        OUNCES_TO_GRAMS(28.34952)
    }
}
