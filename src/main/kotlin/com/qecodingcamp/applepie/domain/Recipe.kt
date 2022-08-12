package com.qecodingcamp.applepie.domain

import java.util.UUID

data class Recipe(
    val id: UUID,
    val recipeName: String,
    val ingredients: MutableList<Ingredient> = mutableListOf()
)

data class RecipeCreationDto(
    val recipeName: String,
    val ingredients: MutableList<Ingredient> = mutableListOf()
)

data class Ingredient(
    val amount: Double,
    val name: String,
    val unit: MeasurementUnit
)
