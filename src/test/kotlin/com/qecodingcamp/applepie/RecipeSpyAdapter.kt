package com.qecodingcamp.applepie

import com.qecodingcamp.applepie.adapter.recipe.RecipeProvider
import com.qecodingcamp.applepie.domain.Recipe
import com.qecodingcamp.applepie.domain.RecipeCreationDto
import java.util.*

class RecipeSpyAdapter : RecipeProvider {

    lateinit var createdRecipe: RecipeCreationDto
    lateinit var repositoryRecipes: List<Recipe>
    lateinit var updatedRecipe: Recipe
    lateinit var deletedRecipe: Recipe
    lateinit var deletedRecipeId: UUID
    lateinit var recipeToBeSearched: String
    lateinit var recipeIdToBeSearched: UUID
    var foundRecipe: List<Recipe?> = emptyList()
    var foundRecipeById: Recipe = Recipe(UUID.randomUUID(), "")

    override fun createRecipe(recipeCreation: RecipeCreationDto): Recipe {
        createdRecipe = recipeCreation
        return Recipe(
            id = UUID.randomUUID(),
            recipeName = recipeCreation.recipeName,
            ingredients = recipeCreation.ingredients
        )
    }

    override fun readRecipes(): List<Recipe> {
        return repositoryRecipes
    }

    /*override fun updateRecipe(recipe: Recipe) {
        updatedRecipe = recipe
    }

    override fun updateRecipe(oldRecipe: Recipe, newRecipe: Recipe) {
        updatedRecipe = newRecipe
    }

    override fun deleteRecipe(recipe: Recipe) {
        deletedRecipe = recipe
    }*/

    override fun deleteRecipe(id: UUID) {
        deletedRecipeId = id
    }

    override fun findRecipesByName(recipeName: String): List<Recipe?> {
        recipeToBeSearched = recipeName
        return foundRecipe
    }

    override fun findRecipesById(id: UUID): Recipe {
        recipeIdToBeSearched = id
        return foundRecipeById
    }
}
