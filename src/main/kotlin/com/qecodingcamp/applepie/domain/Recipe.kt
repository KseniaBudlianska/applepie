package com.qecodingcamp.applepie.domain

import java.util.UUID

data class Recipe(
    val id: UUID,
    val name: String
)

data class RecipeCreationDto(
    val recipeName: String
)
