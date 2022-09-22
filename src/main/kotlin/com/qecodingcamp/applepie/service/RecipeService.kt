package com.qecodingcamp.applepie.service

import com.qecodingcamp.applepie.adapter.recipe.RecipeProvider
import com.qecodingcamp.applepie.domain.Recipe
import com.qecodingcamp.applepie.domain.RecipeCreationDto
import org.springframework.stereotype.Service
import java.util.*

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

    /*fun updateRecipe(recipe: Recipe) {
        recipeAdapter.updateRecipe(recipe)
    }*/

    /*fun updateRecipe(oldRecipe: Recipe, newRecipe: Recipe) {
        recipeAdapter.updateRecipe(oldRecipe, newRecipe)
    }*/

    fun deleteRecipe(id: UUID) {
        recipeAdapter.deleteRecipeById(id)
    }

    /*fun deleteRecipe(recipe: Recipe) {
        recipeAdapter.deleteRecipe(recipe)
    }*/

    fun findRecipeByName(recipe: String) : List<Recipe?> {
        return recipeAdapter.findRecipesByName(recipe)
    }

    fun findRecipeById(id: UUID) : Recipe? {
        return recipeAdapter.findRecipesById(id)
    }

    //FIXME: Duplicated recipe, change to unique id reference for updating
    /*fun addIngredient(recipeName: String, ingredient: Ingredient) {
        val oldRecipe = findRecipeByName(recipeName).first()
        val newRecipe = oldRecipe?.copy() //TODO: fixme here
        newRecipe?.ingredients?.add(ingredient)

        oldRecipe?.let { old ->
            newRecipe?.let { new ->
                updateRecipe(old, new)
            }
        }
    }*/
}
