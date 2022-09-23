package com.qecodingcamp.applepie.controller.recipe

import com.qecodingcamp.applepie.domain.Recipe
import com.qecodingcamp.applepie.domain.RecipeCreationDto
import com.qecodingcamp.applepie.service.RecipeService
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

    @GetMapping(path = ["/find/v1"])
    fun getRecipes(): List<Recipe>  {
        return recipeService.readRecipes()
    }

    @GetMapping(path = ["/find/v2"])
    fun getRecipesByName(
        @RequestParam("name") name: String
    ) : List<Recipe?> {
        return recipeService.findRecipeByName(name)
    }

    @GetMapping(path = ["/find/v3"])
    fun getRecipeById(
        @RequestParam("id") id: UUID
    ) : Recipe? {
        return recipeService.findRecipesById(id)

        // todo notify that there is no such recipe -> "There is no such recipe with id: '$id'."
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
    ): String {
        recipeService.deleteRecipeById(id)
        return "Successfully removed recipe by id: '$id'."

        // todo notify that there is no such recipe
    }
}
