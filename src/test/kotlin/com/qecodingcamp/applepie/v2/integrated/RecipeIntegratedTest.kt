package com.qecodingcamp.applepie.v2.integrated

import com.qecodingcamp.applepie.domain.RecipeCreationDto
import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import java.io.File
import java.util.UUID.randomUUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecipeIntegratedTest {

    @LocalServerPort
    private var port = 0
    private val baseUri = "http://localhost/recipes"
    private val file = File("recipeRepo.csv")

    @BeforeEach
    fun init() {
        RestAssured.baseURI = baseUri
        RestAssured.port = port
        file.createNewFile()
    }

    @Test
    fun getAllRecipes() {
        RestAssured.given()
            .`when`()
            .get("/find/v1")
            .then()
            .statusCode(200)
    }

    @Test
    fun getRecipesByName() {
        RestAssured.given()
            .`when`()
            .get("/find/v2?name=test")
            .then()
            .statusCode(200)
    }

    @Test
    fun getRecipesById() {
        RestAssured.given()
            .`when`()
            .get("/find/v3?id=${randomUUID()}")
            .then()
            .statusCode(200)
    }

    @Test
    fun createRecipe() {
        val recipe = RecipeCreationDto(recipeName = "name")
        RestAssured.given()
            .`when`()
            .contentType("application/json")
            .body(recipe)
            .post()
            .then()
            .statusCode(200)
    }

    @Test
    fun deleteRecipe() {
        RestAssured.given()
            .`when`()
            .delete("/${randomUUID()}")
            .then()
            .statusCode(200)
    }
}