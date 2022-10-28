package com.qecodingcamp.applepie.service

import com.qecodingcamp.applepie.adapter.recipe.RecipeProvider
import com.qecodingcamp.applepie.domain.Recipe
import com.qecodingcamp.applepie.domain.RecipeCreationDto
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RecipeService(
    private val recipeAdapter: RecipeProvider
) {

    fun createRecipe(recipe: RecipeCreationDto): Recipe {
        return recipeAdapter.createRecipe(recipe)
    }

    fun readRecipes(): List<Recipe> {
        return recipeAdapter.readRecipes()
    }

    fun deleteRecipeById(id: UUID) {
        recipeAdapter.deleteRecipeById(id)
    }

    fun findRecipeByName(recipe: String) : List<Recipe?> {
        return recipeAdapter.findRecipesByName(recipe)
    }

    fun findRecipesById(id: UUID) : Recipe? {
        return recipeAdapter.findRecipesById(id)
    }
}
