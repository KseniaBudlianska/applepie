package com.qecodingcamp.applepie

import com.qecodingcamp.applepie.util.UnitConverter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class UnitConverterTest {

    private val converter = UnitConverter()

    @Test
    fun shouldConvertOneHundredGramsToOunces() {
        val ounces = converter.convertGramsToOunces(100.0)

        assertEquals(3.53, ounces)
    }

    @Test
    fun shouldConvertZeroGramsToOunces() {
        val ounces = converter.convertGramsToOunces(0.0)

        assertEquals(0.0, ounces)
    }

    @Test
    fun shouldConvertTenGramsToOunces() {
        val ounces = converter.convertGramsToOunces(10.0)

        assertEquals(0.35, ounces)
    }

    @Test
    fun shouldFailToConvertNegativeGramsToOunces() {
        assertThrows(NumberFormatException::class.java) {
            converter.convertGramsToOunces(-28.34952)
        }
    }

    // -------


    @Test
    fun shouldConvertOneHundredOuncesToGrams() {
        val ounces = converter.convertOuncesToGrams(100.0)

        assertEquals(283.5, ounces)
    }

    @Test
    fun shouldConvertZeroOuncesToGrams() {
        val ounces = converter.convertOuncesToGrams(0.0)

        assertEquals(0.0, ounces)
    }

    @Test
    fun shouldConvertTenOuncesToGrams() {
        val ounces = converter.convertOuncesToGrams(10.0)

        assertEquals(2834.95, ounces)
    }

    @Test
    fun shouldFailToConvertNegativeOuncesToGrams() {
        assertThrows(NumberFormatException::class.java) {
            converter.convertOuncesToGrams(-28.34952)
        }
    }
}
