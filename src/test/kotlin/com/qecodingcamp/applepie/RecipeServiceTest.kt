package com.qecodingcamp.applepie

import com.qecodingcamp.applepie.domain.Ingredient
import com.qecodingcamp.applepie.domain.MeasurementUnit
import com.qecodingcamp.applepie.domain.Recipe
import com.qecodingcamp.applepie.domain.RecipeCreationDto
import com.qecodingcamp.applepie.service.RecipeService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.UUID
import java.util.Collections.emptyList

class RecipeServiceTest {

    private val mockAdapter = RecipeSpyAdapter()
    private val recipeService = RecipeService(mockAdapter)

    @Test
    fun createRecipe() {
        val recipe = RecipeCreationDto("My Recipe")

        recipeService.createRecipe(recipe)

        val createdRecipe = mockAdapter.createdRecipe

        assertTrue(createdRecipe == recipe)
    }

    @Test
    fun readRecipe() {
        val recipes = listOf(
            Recipe(UUID.randomUUID(),"My recipe 1"),
            Recipe(UUID.randomUUID(),"My recipe 1"),
            Recipe(UUID.randomUUID(),"My recipe 1"),
            Recipe(UUID.randomUUID(),"My recipe 1")
        )
        mockAdapter.repositoryRecipes = recipes

        val returnedRecipes = recipeService.readRecipes()

        assertTrue(returnedRecipes == recipes)
    }

    @Test
    fun updateRecipe() {
        val recipe = Recipe(UUID.randomUUID(),"My recipe 1")

        recipeService.updateRecipe(recipe)

        val updatedRecipe = mockAdapter.updatedRecipe

        assertTrue(recipe == updatedRecipe)
    }

    @Test
    fun updateFromOldToNewRecipe() {
        val oldRecipe = Recipe(UUID.randomUUID(), "My recipe 1")
        val newRecipe = Recipe(UUID.randomUUID(),"My recipe 1")

        recipeService.updateRecipe(oldRecipe, newRecipe)

        val updatedRecipe = mockAdapter.updatedRecipe

        assertTrue(newRecipe == updatedRecipe)
    }

    @Test
    fun deleteRecipe() {
        val recipe = Recipe(UUID.randomUUID(),"My recipe 1")

        recipeService.deleteRecipe(recipe)

        val deletedRecipe = mockAdapter.deletedRecipe

        assertTrue(recipe == deletedRecipe)
    }

    @Test
    fun findRecipeByNameReturnsNullIfNoRecipe() {
        val returnedRecipes = recipeService.findRecipeByName("My recipe 2")

        assertEquals(returnedRecipes, emptyList<Recipe>())
        assertEquals(mockAdapter.recipeToBeSearched, "My recipe 2")
    }

    @Test
    fun findRecipeByNameReturnsRecipeIfValidRecipe() {
        val recipeName = "My recipe 1"
        val recipeToBeReturn = listOf(Recipe(UUID.randomUUID(), recipeName))

        mockAdapter.foundRecipe = recipeToBeReturn

        val recipe = recipeService.findRecipeByName(recipeName)

        assertEquals(recipeToBeReturn, recipe)
        assertEquals(mockAdapter.recipeToBeSearched, recipeName)
    }

    @Test
    fun findRecipeByNameReturnsNullIfNotValidRecipe() {
        val recipeName = "My recipe 1"
        val recipeToBeReturn = Recipe(UUID.randomUUID(), recipeName)

        mockAdapter.foundRecipe = emptyList()

        val recipe = recipeService.findRecipeByName(recipeToBeReturn.recipeName + "!")

        assertEquals(emptyList<Recipe>(), recipe)
        assertEquals(mockAdapter.recipeToBeSearched, recipeName + "!")
    }

    @Test
    fun addIngredientToRecipe() {
        //Arrange
        val recipeName = "My recipe"
        val oldRecipe = Recipe(UUID.randomUUID(), recipeName)
        val ingredient = Ingredient(1.89, "My ingredient", MeasurementUnit.GRAM)
        mockAdapter.foundRecipe = listOf(oldRecipe)

        //Act
        recipeService.addIngredient(recipeName, ingredient)

        //Assert
        //Capture which recipe was saved: verify the recipe is updated
        assertEquals(mockAdapter.updatedRecipe.recipeName, recipeName)
        assertEquals(mockAdapter.updatedRecipe.ingredients.first(), ingredient)
    }
}
