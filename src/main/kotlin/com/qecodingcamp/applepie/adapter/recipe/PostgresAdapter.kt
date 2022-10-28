package com.qecodingcamp.applepie.adapter.recipe

import com.qecodingcamp.applepie.Tables
import com.qecodingcamp.applepie.domain.Recipe
import com.qecodingcamp.applepie.domain.RecipeCreationDto
import com.qecodingcamp.applepie.tables.Recipe.RECIPE
import org.jooq.DSLContext
import java.util.UUID

/**
 * Adapter pattern based on Design pattern.
 */
class PostgresAdapter(private val context: DSLContext) : RecipeProvider {

    override fun createRecipe(recipeCreation: RecipeCreationDto): Recipe {
        val recipe = Recipe(UUID.randomUUID(), recipeCreation.recipeName)
        val record = context.insertInto(RECIPE)
            .set(
                mapOf(
                    RECIPE.ID to recipe.id,
                    RECIPE.NAME to recipe.name
                )
            ).returning().fetchOne()
        val id = record[RECIPE.ID]
        val name = record[RECIPE.NAME]
        return Recipe(id, name)
    }

    override fun readRecipes(): List<Recipe> {
        val records = context.select(Tables.RECIPE.asterisk()) // todo switch to *Tables.RECIPE.fields()
            .from(RECIPE)
            .fetch()
        return records.map { Recipe(it[RECIPE.ID], it[RECIPE.NAME])}
    }

    override fun deleteRecipeById(id: UUID) {
        context.deleteFrom(RECIPE)
            .where(RECIPE.ID.eq(id))
            .execute()
    }

    // todo what to do if no recipe found?

    override fun findRecipesByName(recipeName: String): List<Recipe?> {
        val records = context.select(Tables.RECIPE.asterisk()) // todo switch to *Tables.RECIPE.fields()
            .from(RECIPE)
            .where(RECIPE.NAME.eq(recipeName))
            .fetch()
        return records.map { Recipe(it[RECIPE.ID], it[RECIPE.NAME])}
    }

    override fun findRecipesById(id: UUID): Recipe? {
        val record = context.select(Tables.RECIPE.asterisk())
            .from(RECIPE)
            .where(RECIPE.ID.eq(id))
            .fetchOne()
        val id = record[RECIPE.ID]
        val name = record[RECIPE.NAME]
        return Recipe(id, name)
    }
}
