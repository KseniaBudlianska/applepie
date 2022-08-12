package com.qecodingcamp.applepie

import com.qecodingcamp.applepie.domain.MassMeasurementUnit
import com.qecodingcamp.applepie.domain.MeasurementUnit.GRAM
import com.qecodingcamp.applepie.domain.MeasurementUnit.LITER
import com.qecodingcamp.applepie.domain.MeasurementUnit.MILLILITER
import com.qecodingcamp.applepie.domain.MeasurementUnit.OUNCE
import com.qecodingcamp.applepie.domain.MeasurementUnit.TBSP
import com.qecodingcamp.applepie.domain.VolumeMeasurementUnit
import com.qecodingcamp.applepie.util.SmartUnitConverter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.InputMismatchException

class SmartUnitConverterTest {

    private val converter = SmartUnitConverter()

    @Test
    fun shouldConvertOneHundredGramsToOunces() {
        val ounces = converter.smartConvert(100.0, GRAM, OUNCE)

        assertEquals(3.53, ounces)
    }

    @Test
    fun shouldConvertZeroGramsToOunces() {
        val ounces = converter.smartConvert(0.0, GRAM, OUNCE)

        assertEquals(0.0, ounces)
    }

    @Test
    fun shouldConvertTenGramsToOunces() {
        val ounces = converter.smartConvert(10.0, GRAM, OUNCE)

        assertEquals(0.35, ounces)
    }

    @Test
    fun shouldFailToConvertNegativeGramsToOunces() {
        assertThrows(NumberFormatException::class.java) {
            converter.smartConvert(-28.34952, GRAM, OUNCE)
        }
    }

    @Test
    fun shouldConvertOneHundredOuncesToGrams() {
        val ounces = converter.smartConvert(100.0, OUNCE, GRAM)

        assertEquals(2834.95, ounces)
    }

    @Test
    fun shouldConvertZeroOuncesToGrams() {
        val ounces = converter.smartConvert(0.0, OUNCE, GRAM)

        assertEquals(0.0, ounces)
    }

    @Test
    fun shouldConvertTenOuncesToGrams() {
        val ounces = converter.smartConvert(10.0, OUNCE, GRAM)

        assertEquals(283.5, ounces)
    }

    @Test
    fun shouldFailToConvertNegativeOuncesToGrams() {
        assertThrows(NumberFormatException::class.java) {
            converter.smartConvert(-28.34952, OUNCE, GRAM)
        }
    }

    @Test
    fun shouldConvertOneHundredMillilitersToLiters() {
        val ounces = converter.smartConvert(100.0, MILLILITER, LITER)

        assertEquals(0.1, ounces)
    }

    @Test
    fun shouldConvertZeroMillilitersToLiters() {
        val ounces = converter.smartConvert(0.0, MILLILITER, LITER)

        assertEquals(0.0, ounces)
    }

    @Test
    fun shouldConvertTenMillilitersToLiters() {
        val ounces = converter.smartConvert(10.0, MILLILITER, LITER)

        assertEquals(0.01, ounces)
    }

    @Test
    fun shouldFailToConvertNegativeMillilitersToLiters() {
        assertThrows(NumberFormatException::class.java) {
            converter.smartConvert(-28.34952, MILLILITER, LITER)
        }
    }

    @Test
    fun shouldConvertOneHundredLitersToMilliliters() {
        val ounces = converter.smartConvert(100.0, LITER, MILLILITER)

        assertEquals(100000.0, ounces)
    }

    @Test
    fun shouldConvertZeroLitersToMilliliters() {
        val ounces = converter.smartConvert(0.0, LITER, MILLILITER)

        assertEquals(0.0, ounces)
    }

    @Test
    fun shouldConvertTenLitersToMilliliters() {
        val ounces = converter.smartConvert(10.0, LITER, MILLILITER)

        assertEquals(10000.0, ounces)
    }

    @Test
    fun shouldFailToConvertNegativeLitersToMilliliters() {
        assertThrows(NumberFormatException::class.java) {
            converter.smartConvert(-28.34952, LITER, MILLILITER)
        }
    }

    @Test
    fun shouldFailToConvertLitersToLiters() {
        assertThrows(IllegalStateException::class.java) {
            converter.smartConvert(28.34952, LITER, LITER)
        }
    }

    @Test
    fun shouldFailToConvertLitersToTbsp() {
        assertThrows(IllegalStateException::class.java) {
            converter.smartConvert(20.0, LITER, TBSP)
        }
    }

    @Test
    fun shouldSmarterConvertOneLiterToMilliliter() {
        val ounces = converter.smarterConverter(1.0, VolumeMeasurementUnit.LITER, VolumeMeasurementUnit.MILLILITER)

        assertEquals(1000.0, ounces)
    }

    @Test
    fun shouldSmarterConvertTenLiterToMilliliter() {
        val ounces = converter.smarterConverter(10.0, VolumeMeasurementUnit.LITER, VolumeMeasurementUnit.MILLILITER)

        assertEquals(10000.0, ounces)
    }

    @Test
    fun shouldSmarterConvertTenFluidOuncesToLiter() {
        val ounces = converter.smarterConverter(10.0, VolumeMeasurementUnit.FLUID_OUNCE, VolumeMeasurementUnit.LITER)

        assertEquals(0.3, ounces)
    }

//    @Test
//    fun shouldSmarterConvertOneGramToKilogram() {
//        val ounces = converter.smarterConverter(1.0, MassMeasurementUnit.GRAM, MassMeasurementUnit.KILOGRAM)
//
//        assertEquals(1000.0, ounces)
//    }

    @Test
    fun shouldSmarterConvertTenGramToKilogram() {
        val ounces = converter.smarterConverter(10.0, MassMeasurementUnit.GRAM, MassMeasurementUnit.KILOGRAM)

        assertEquals(0.01, ounces)
    }

    @Test
    fun shouldSmarterConvertOneGramToGram() {
        val ounces = converter.smarterConverter(1.0, MassMeasurementUnit.GRAM, MassMeasurementUnit.GRAM)

        assertEquals(1.0, ounces)
    }

    @Test
    fun shouldSmarterConvertTenOuncesToGram() {
        val ounces = converter.smarterConverter(10.0, MassMeasurementUnit.OUNCE, MassMeasurementUnit.GRAM)

        assertEquals(283.5, ounces)
    }

    @Test
    fun shouldSmarterConvertTenKilogramsToOunces() {
        val ounces = converter.smarterConverter(10.0, MassMeasurementUnit.KILOGRAM, MassMeasurementUnit.OUNCE)

        assertEquals(352.74, ounces)
    }

    @Test
    fun shouldSmarterConvertTenKilogramsToLiters() {
        assertThrows(InputMismatchException::class.java) {
            converter.smarterConverter(10.0, MassMeasurementUnit.KILOGRAM, VolumeMeasurementUnit.LITER)
        }
    }
}
