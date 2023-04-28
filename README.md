## Recipe Service

### Description
The service handles recipes. Recipe has `id`, `name`, list of `ingredients` (out of scope for now). You can add as many recipes as you want (limitation is on storage side). 

### Implemented Functions
 * Create a new recipe (`/recipes`)
 * Get all recipes (`/recipes/find/recipes`)
 * Get recipe by name (`/recipes/find/recipesByName?name={name}`)
 * Get recipe by id (`/recipes/find/recipeById?id={id}`)
 * Delete recipe by id (`/recipes/{id}`)
 
### Storage
 * [RecipeCsvAdapter](https://github.com/KseniaBudlianska/applepie/blob/main/src/main/kotlin/com/qecodingcamp/applepie/adapter/recipe/RecipeCsvAdapter.kt)
 * RecipeDbAdapter (todo)
 
### How to Install and Run the Project
To be added.
 
