package com.qecodingcamp.applepie.v2.adapter

import com.qecodingcamp.applepie.Tables
import com.qecodingcamp.applepie.adapter.recipe.RecipePostgresAdapter
import com.qecodingcamp.applepie.domain.RecipeCreationDto
import com.qecodingcamp.applepie.tables.Recipe.RECIPE
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class RecipePostgresAdapterTest {

    @Autowired
    private lateinit var dsl: DSLContext

    @Autowired
    private lateinit var recipeAdapter: RecipePostgresAdapter

    companion object {

        //https://rieckpil.de/testing-spring-boot-applications-with-kotlin-and-testcontainers/
        //docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres
        @Container
        val container = PostgreSQLContainer<Nothing>("postgres").apply {
            withDatabaseName("recipes")
            withUsername("ksenia")
            withPassword("ksenia")
        }
    }

    private val recipeId = UUID.randomUUID()
    private val recipeName = "Recipe Name"

    @BeforeEach
    fun setup() {
        dsl.truncate(Tables.RECIPE).execute()
    }

    @Test
    fun shouldGetRecipes() {
        //arrange
        dsl.insertInto(RECIPE).set(mapOf(RECIPE.NAME to recipeName)).execute()

        //act
        val recipes = recipeAdapter.readRecipes()

        //assert
        Assertions.assertTrue(recipes.size == 1)
        Assertions.assertTrue(recipes[0].name == recipeName)
    }

    @Test
    fun shouldGetEmptyRecipeWhenNotSet() {
        //act
        val recipes = recipeAdapter.readRecipes()

        //assert
        Assertions.assertTrue(recipes.isEmpty())
    }

    @Test
    fun shouldCreateRecipe() {
        // act
        recipeAdapter.createRecipe(RecipeCreationDto(recipeName = recipeName))

        // assert
        val recipeRecords = dsl.select(*RECIPE.fields()).from(RECIPE).fetch()

        Assertions.assertTrue(recipeRecords.count() == 1)
        Assertions.assertTrue(recipeRecords.first()[RECIPE.NAME] == recipeName)
    }

    @Test
    fun shouldDeleteRecipeById() {
        // arrange
        dsl.insertInto(RECIPE).set(mapOf(RECIPE.ID to recipeId, RECIPE.NAME to recipeName)).execute()

        // act
        recipeAdapter.deleteRecipeById(recipeId)

        // assert
        val recipeRecords = dsl.select(*RECIPE.fields()).from(RECIPE).where(RECIPE.ID.eq(recipeId)).fetch()

        Assertions.assertTrue(recipeRecords.isEmpty())
    }

    @Test
    fun shouldFindRecipeById() {
        // arrange
        dsl.insertInto(RECIPE).set(mapOf(RECIPE.ID to recipeId, RECIPE.NAME to recipeName)).execute()

        // act
        val recipe = recipeAdapter.findRecipesById(recipeId)

        // assert
        Assertions.assertTrue(recipe!!.name == recipeName)
    }

    @Test
    fun shouldFindRecipeByIdWhenNoRecipeExist() {
        // act
        val recipe = recipeAdapter.findRecipesById(recipeId)

        // assert
        Assertions.assertTrue(recipe == null)
    }

    @Test
    fun shouldFindRecipeByName() {
        // arrange
        dsl.insertInto(RECIPE).set(mapOf(RECIPE.NAME to recipeName)).execute()

        // act
        val recipes = recipeAdapter.findRecipesByName(recipeName)

        // assert
        Assertions.assertTrue(recipes.first()!!.name == recipeName)
    }

    @Test
    fun shouldFindRecipeByNameWhenNoRecipeExist() {
        // act
        val recipes = recipeAdapter.findRecipesByName(recipeName)

        // assert
        Assertions.assertTrue(recipes.isEmpty())
    }
}
