package com.qecodingcamp.applepie.v2.adapter

import com.qecodingcamp.applepie.adapter.recipe.RecipeCsvAdapter
import com.qecodingcamp.applepie.domain.RecipeCreationDto
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File
import java.util.UUID.randomUUID

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RecipeCsvAdapterTest {

    private val file = File("testRecipeRepository.csv")
    private val csvAdapter = RecipeCsvAdapter(file)
    private val recipeId = randomUUID()
    private val recipeName = "Banana cake"

    @BeforeEach
    fun setup() {
        file.createNewFile()
    }

    @AfterEach
    fun cleanup() {
        file.delete()
    }

    @Test
    fun readRecipes() {
        // arrange
        file.writeText("${recipeId},$recipeName")

        // act
        val recipes = csvAdapter.readRecipes()

        // assert
        println(recipes)
        assertTrue(recipes.size == 1)
        assertTrue(recipes[0].id == recipeId)
        assertTrue(recipes[0].name == recipeName)
    }

    @Test
    fun createRecipe() {
        // act
        csvAdapter.createRecipe(
            RecipeCreationDto(
                recipeName = recipeName
            )
        )
        val lines = file.readLines()

        // assert
        println(lines)
        assertTrue(lines.size == 1)
        assertTrue(lines[0].split(",")[1] == recipeName)
    }

    @Test
    fun deleteRecipeByIdToAnEmptyFile() {
        // arrange
        file.writeText("${recipeId},$recipeName")

        // act
        csvAdapter.deleteRecipeById(recipeId)
        val lines = file.readLines()

        // assert
        println(lines)
        assertTrue(lines.isEmpty())
    }

    @Test
    fun deleteRecipeById() {
        // arrange
        val anotherRecipeId = randomUUID()

        file.writeText("${recipeId},$recipeName")
        file.appendText("\n${anotherRecipeId},$recipeName")

        // act
        csvAdapter.deleteRecipeById(recipeId)
        val lines = file.readLines()

        // assert
        println(lines)
        assertTrue(lines.size == 1)
        assertTrue(lines[0].split(",")[0] == anotherRecipeId.toString())
    }

    @Test
    fun findRecipeById() {
        // arrange
        file.writeText("${recipeId},$recipeName")

        // act
        val recipe = csvAdapter.findRecipesById(recipeId)

        // assert
        println(recipe)
        assertTrue(recipe!!.id == recipeId)
        assertTrue(recipe.name == recipeName)
    }

    @Test
    fun findRecipeByName() {
        // arrange
        file.writeText("${recipeId},$recipeName")

        // act
        val recipes = csvAdapter.findRecipesByName(recipeName)

        // assert
        println(recipes)
        assertTrue(recipes.first()!!.name == recipeName)
        assertTrue(recipes.first()!!.id == recipeId)
    }
}
