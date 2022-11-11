package com.qecodingcamp.applepie.adapter.recipe

import com.qecodingcamp.applepie.domain.Recipe
import com.qecodingcamp.applepie.domain.RecipeCreationDto
import java.util.*

interface RecipeProvider {

    fun createRecipe(recipeCreation: RecipeCreationDto): Recipe

    fun readRecipes(): List<Recipe>

    fun deleteRecipeById(id: UUID)

    fun findRecipesByName(recipeName: String) : List<Recipe?>

    fun findRecipesById(id: UUID) : Recipe?
}
