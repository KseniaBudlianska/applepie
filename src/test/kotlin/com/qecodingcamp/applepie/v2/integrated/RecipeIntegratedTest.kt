package com.qecodingcamp.applepie.v2.integrated

import com.qecodingcamp.applepie.adapter.recipe.RecipeProvider
import com.qecodingcamp.applepie.domain.Recipe
import com.qecodingcamp.applepie.domain.RecipeCreationDto
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.test.context.ContextConfiguration
import java.io.File
import java.util.UUID.randomUUID

@ContextConfiguration(classes = [RecipeIntegratedTest.RecipeTestConfig::class])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecipeIntegratedTest {

    @LocalServerPort
    private var port = 0
    private val baseUri = "http://localhost/recipes"
    private val getRecipesPath = "/find/v1"
    private val getRecipesByNamePath = "/find/v2"
    private val getRecipesByIdPath = "/find/v3"
    private val file = File("recipeRepo.csv")

    @TestConfiguration
    open class RecipeTestConfig {

        @Bean
        @Primary
        open fun mockRecipeProvider() = mockk<RecipeProvider>(relaxed = true)

    }

    @Autowired
    lateinit var recipeProvider: RecipeProvider

    @BeforeEach
    fun init() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
        RestAssured.baseURI = baseUri
        RestAssured.port = port
        file.createNewFile()
    }

    private val recipe = Recipe(randomUUID(), "My recipe")

    @Test
    fun getAllRecipesTest() {
        // Arrange
        val expectedRecipes = listOf(recipe)
        every { recipeProvider.readRecipes() } returns expectedRecipes

        // Act
        given()
            .`when`()
            .get(getRecipesPath)
            .then()
        // Assert
            .statusCode(200)
            .body("[0].id", equalTo(recipe.id.toString()))
            .body("[0].name", equalTo(recipe.name))
    }

    @Test
    fun getRecipesByNameTest() {
        // Arrange
        val expectedRecipes = listOf(recipe)
        every { recipeProvider.findRecipesByName(recipe.name) } returns expectedRecipes

        // Act
        given()
            .`when`()
            .queryParam("name", recipe.name)
            .get(getRecipesByNamePath)
            .then()
        // Assert
            .statusCode(200)
            .body("[0].id", equalTo(recipe.id.toString()))
            .body("[0].name", equalTo(recipe.name))
    }

    @Test
    fun getRecipeByNameErrorTest() {
        val expectedRecipes = emptyList<Recipe>()
        every { recipeProvider.findRecipesByName(recipe.name) } returns expectedRecipes

        // Act
        given()
            .`when`()
            .queryParam("name", recipe.name)
            .get(getRecipesByNamePath)
            .then()
            // Assert
            .statusCode(200)
            .body(equalTo(expectedRecipes.toString()))

        verify(exactly = 1) { recipeProvider.findRecipesByName(recipe.name) }
    }

    @Test
    fun getRecipesByIdTest() {
        // Arrange
        every { recipeProvider.findRecipesById(recipe.id) } returns recipe

        // Act
        given()
            .`when`()
            .queryParam("id", recipe.id)
            .get(getRecipesByIdPath)
            .then()
        // Assert
            .statusCode(200)
            .body("id", equalTo(recipe.id.toString()))
            .body("name", equalTo(recipe.name))
    }

    @Test
    fun createRecipeTest() {
        // Arrange
        val createdRecipe = RecipeCreationDto(recipe.name)
        every { recipeProvider.createRecipe(createdRecipe) } returns recipe

        // Act
        given()
            .`when`()
            .contentType("application/json")
            .body(createdRecipe)
            .post()
            .peek()
            .then()
        // Assert
            .statusCode(200)
            .body(equalTo("\"${recipe.id}\""))

        verify(exactly = 1) {recipeProvider.createRecipe(createdRecipe)}
    }

    @Test
    fun deleteRecipeTest() {
        // Arrange
        val createdRecipe = RecipeCreationDto(recipe.name)
        every { recipeProvider.createRecipe(createdRecipe) } returns recipe

        // Act
        given()
            .`when`()
            .delete("/${recipe.id}")
            .then()
        // Assert
            .statusCode(200)

        verify(exactly = 1) {recipeProvider.deleteRecipeById(recipe.id)}
    }
}