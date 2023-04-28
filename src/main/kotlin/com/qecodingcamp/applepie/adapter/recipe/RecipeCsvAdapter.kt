package com.qecodingcamp.applepie.adapter.recipe

import com.qecodingcamp.applepie.domain.Recipe
import com.qecodingcamp.applepie.domain.RecipeCreationDto
import java.io.File
import java.util.UUID

/**
 * Adapter pattern based on Design pattern.
 */
class RecipeCsvAdapter(private val file: File) : RecipeProvider {

    override fun createRecipe(recipeCreation: RecipeCreationDto): Recipe {
        val recipe = Recipe(
            id = UUID.randomUUID(),
            name = recipeCreation.recipeName
        )
        val recipeLine = convertToLine(recipe)
        file.appendText(recipeLine)
        return recipe
    }

    override fun readRecipes(): List<Recipe> {
        val lines = file.readLines()

        return lines.filter { line ->
            line.isNotBlank()
        }.map { line ->
            convertToRecipe(line)
        }
    }

    override fun deleteRecipeById(recipeId: UUID) {
        val lines = file.readLines()
        val filteredLines = lines.filter { line ->
            val id = UUID.fromString(line.split(",")[0])
            recipeId != id
        }
        if (filteredLines.isNotEmpty()) {
            file.writeText(
                filteredLines.joinToString(separator = "\n", postfix = "\n")
            )
        } else {
            file.writeText("")
        }
    }

    override fun findRecipesByName(recipeName: String): List<Recipe?> {
        val lines = file.readLines()
        return lines.filter { line ->
            line.contains(recipeName, ignoreCase = true)
        }.map { line ->
            convertToRecipe(line)
        }
    }

    override fun findRecipesById(recipeId: UUID): Recipe? {
        val lines = file.readLines()
        return lines.filter { line ->
            val id = UUID.fromString(line.split(",")[0])
            recipeId == id
        }.map { line ->
            convertToRecipe(line)
        }.firstOrNull()
    }

    private fun convertToRecipe(line: String) : Recipe {
        val (id, name) = line.split(",")
        return Recipe(
            id = UUID.fromString(id),
            name = name
        )
    }

    private fun convertToLine(recipe: Recipe) : String {
        return "${recipe.id},${recipe.name}\n"
    }
}
