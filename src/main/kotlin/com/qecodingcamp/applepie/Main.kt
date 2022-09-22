package com.qecodingcamp.applepie

import com.qecodingcamp.applepie.domain.Ingredient
import com.qecodingcamp.applepie.domain.MeasurementUnit
import com.qecodingcamp.applepie.domain.RecipeCreationDto
import com.qecodingcamp.applepie.adapter.recipe.RecipeCsvAdapter
import com.qecodingcamp.applepie.service.RecipeService
import java.io.File

fun main() {
    val file = File("recipeRepo.csv")
    val recipeAdapter = RecipeCsvAdapter(file)
    val service = RecipeService(recipeAdapter)


    val name = "Squash Fudge Brownies"
    val browniesRecipe = RecipeCreationDto(
        recipeName = name,
        ingredients = mutableListOf()
    )
    service.createRecipe(browniesRecipe)
    println("Brownies: " + service.readRecipes())

    val ingredient = Ingredient(
        amount = 400.00,
        unit = MeasurementUnit.GRAM,
        name = "Suger"
    )
//    service.addIngredient(name, ingredient)

    println("Brownies: " + service.readRecipes())
//    service.updateRecipe(browniesRecipe)
//    println("brownies: " + service.readRecipes())
//    service.deleteRecipe(browniesRecipe)
//    println("brownies: " + service.readRecipes())

    service.findRecipeByName(browniesRecipe.recipeName)

    service.findRecipeByName("Test")
}
