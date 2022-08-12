package com.qecodingcamp.applepie.util

import kotlin.math.pow
import kotlin.math.roundToInt

class UnitConverter {

    private val gramToOunceConversionRatio = 28.34952

    fun convertGramsToOunces(weightInGrams: Double): Double {
        return if (weightInGrams >= 0) {
            val weight = weightInGrams / gramToOunceConversionRatio
            roundToTwoDecimalPlaces(weight, decimalPlaces = 2)
        } else {
            throw NumberFormatException("Negative weight cannot be converted.")
        }
    }

    fun convertOuncesToGrams(weightInOunces: Double): Double {
        return if (weightInOunces >= 0) {
            val weight = weightInOunces * gramToOunceConversionRatio
            roundToTwoDecimalPlaces(weight, decimalPlaces = 2)
        } else {
            throw NumberFormatException("Negative weight cannot be converted.")
        }
    }

    private fun roundToTwoDecimalPlaces(number: Double, decimalPlaces: Int): Double {
        val shift = 10.0.pow(decimalPlaces.toDouble())
        return (number * shift).roundToInt().toDouble() / shift
    }
}
