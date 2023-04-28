package com.qecodingcamp.applepie.controller.recipe

import com.qecodingcamp.applepie.domain.Recipe
import com.qecodingcamp.applepie.domain.RecipeCreationDto
import com.qecodingcamp.applepie.service.RecipeService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(path = ["/recipes"])
class RecipeController(
    private val recipeService: RecipeService
) {

    @GetMapping(path = ["/find/recipes"])
    fun getRecipes(): List<Recipe>  {
        return recipeService.readRecipes()
    }

    @GetMapping(path = ["/find/recipesByName"])
    fun getRecipesByName(
        @RequestParam("name") name: String
    ) : List<Recipe?> {
        return recipeService.findRecipeByName(name)
    }

    @GetMapping(path = ["/find/recipeById"])
    fun getRecipeById(
        @RequestParam("id") id: UUID
    ) : ResponseEntity<Recipe> {
        return recipeService.findRecipesById(id)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.status(NOT_FOUND).build()
        //todo add .body("There is no such recipe with id: '$id'.")
    }

    @PostMapping
    fun createRecipe(
        @RequestBody recipe: RecipeCreationDto
    ): UUID {
        val recipe = recipeService.createRecipe(recipe)
        return recipe.id
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteRecipe(
        @PathVariable("id") id: UUID
    ) : ResponseEntity<String> {
        return try {
            recipeService.deleteRecipeById(id)
            ResponseEntity.ok("Successfully removed recipe by id: '$id'.")
        } catch (ex: Exception) {
            ResponseEntity.status(NOT_FOUND).body("There is no recipe with id: '$id'.")
        }
    }
}
