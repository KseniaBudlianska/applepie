package com.qecodingcamp.applepie.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.qecodingcamp.applepie.adapter.recipe.RecipePostgresAdapter
import com.qecodingcamp.applepie.adapter.recipe.RecipeCsvAdapter
import com.qecodingcamp.applepie.adapter.recipe.RecipeProvider
import com.qecodingcamp.applepie.controller.recipe.RecipeController
import com.qecodingcamp.applepie.service.RecipeService
import org.jooq.DSLContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
open class ApplePieConfig {

    @Bean
    open fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.registerKotlinModule()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return mapper
    }

    @Bean
    open fun getRecipeController(
        recipeService: RecipeService
    ) = RecipeController(recipeService)

    @Bean
    open fun recipeService(
        recipeProvider: RecipeProvider
    ) = RecipeService(recipeProvider)

    //@Bean
    //open fun recipeCsvProvider() = RecipeCsvAdapter(File("recipeRepo.csv"))

    @Bean
    open fun recipePostgresProvider(context: DSLContext) = RecipePostgresAdapter(context)
}
