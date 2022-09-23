package com.qecodingcamp.applepie.v2.integrated

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import java.io.File

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

    // cover all functions, check only status code
    
}