package com.qecodingcamp.applepie.v2.integrated

import com.qecodingcamp.applepie.adapter.recipe.RecipeProvider
import com.qecodingcamp.applepie.domain.Recipe
import com.qecodingcamp.applepie.domain.RecipeCreationDto
import io.mockk.every
import io.mockk.mockk
import io.restassured.RestAssured
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
    fun getAllRecipes() {
        // Arrange
        val expectedRecipes = listOf(recipe)
        every { recipeProvider.readRecipes() } returns expectedRecipes

        // Act
        RestAssured.given()
            .`when`()
            .get("/find/v1")
            .then()
        // Assert
            .statusCode(200)
            .body("[0].id", equalTo(recipe.id.toString()))
            .body("[0].name", equalTo(recipe.name))
    }

    @Test
    fun getRecipesByName() {
        // Arrange
        val expectedRecipes = listOf(recipe)
        every { recipeProvider.findRecipesByName(recipe.name) } returns expectedRecipes

        // Act
        RestAssured.given()
            .`when`()
            .get("/find/v2?name=${recipe.name}")
            .then()
        // Assert
            .statusCode(200)
            .body("[0].id", equalTo(recipe.id.toString()))
            .body("[0].name", equalTo(recipe.name))
    }

    @Test
    fun getRecipesById() {
        // Arrange
        every { recipeProvider.findRecipesById(recipe.id) } returns recipe

        // Act
        RestAssured.given()
            .`when`()
            .get("/find/v3?id=${recipe.id}")
            .then()
            // Assert
            .statusCode(200)
            .body("id", equalTo(recipe.id.toString()))
            .body("name", equalTo(recipe.name))
    }

    @Test
    fun createRecipe() {
        // Arrange
        val createdRecipe = RecipeCreationDto(recipe.name)
        every { recipeProvider.createRecipe(createdRecipe) } returns recipe

        // Act
        RestAssured.given()
            .`when`()
            .contentType("application/json")
            .body(createdRecipe)
            .post()
            .then()
            .statusCode(200)
    }

    @Test
    fun deleteRecipe() {
        // Arrange
        val createdRecipe = RecipeCreationDto(recipe.name)
        every { recipeProvider.createRecipe(createdRecipe) } returns recipe

        // Act
        RestAssured.given()
            .`when`()
            .delete("/${randomUUID()}")
            .then()
            .statusCode(200)
    }
}