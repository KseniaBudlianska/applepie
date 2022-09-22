package com.qecodingcamp.applepie.v2.service

import com.qecodingcamp.applepie.adapter.recipe.RecipeProvider
import com.qecodingcamp.applepie.domain.Recipe
import com.qecodingcamp.applepie.domain.RecipeCreationDto
import com.qecodingcamp.applepie.service.RecipeService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.UUID.randomUUID

internal class RecipeServiceTest {

    private val recipeProvider = mockk<RecipeProvider>()
    private val recipeService = RecipeService(recipeProvider)
    private val recipeCreationDto = RecipeCreationDto("My Recipe")
    private val recipe = Recipe(randomUUID(), "My Recipe")

    @Test
    fun createRecipe() {
        // arrange
        every { recipeProvider.createRecipe(recipeCreationDto) } returns recipe

        // act
        val returnedRecipe = recipeService.createRecipe(recipeCreationDto)

        // assert
        verify(exactly = 1) { recipeProvider.createRecipe(recipeCreationDto)}
        assertEquals(recipe, returnedRecipe)
    }

    @Test
    fun readRecipes() {
        // arrange
        val expectedRecipes = listOf(recipe)

        every { recipeProvider.readRecipes() } returns expectedRecipes

        //act
        val returnedRecipes = recipeService.readRecipes()

        // assert
        assertTrue(expectedRecipes == returnedRecipes)
    }

    @Test
    fun findRecipeByNameReturnsEmptyList() {
        // arrange
        val recipeName = "Whatever name"
        every { recipeProvider.findRecipesByName(recipeName) } returns emptyList()

        // act
        val recipes = recipeService.findRecipeByName(recipeName)

        // arrange
        assertEquals(recipes.size, 0)
        verify(exactly = 1) { recipeProvider.findRecipesByName(recipeName) }
    }

    @Test
    fun findRecipeByNameReturnsRecipe() {
        // arrange
        val recipeName = "My Recipe"
        val recipesToReturn = listOf(recipe)

        every { recipeProvider.findRecipesByName(recipeName) } returns recipesToReturn

        // act
        val recipesFound = recipeService.findRecipeByName(recipeName)

        // assert
        verify(exactly = 1) { recipeProvider.findRecipesByName(recipeName)  }
        assertEquals(recipesFound, recipesToReturn)
    }
}
