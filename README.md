## Recipe Service

### Description
The service handles recipes. Recipe has `id`, `name`, list of `ingredients` (out of scope for now). You can add as many recipes as you want (limitation is on storage side). 

### Implemented Functions
 * Create a new recipe (`/recipes`)
 * Get all recipes (`/recipes/find/v1`)
 * Get recipe by name (`/recipes/find/v2?name={name}`)
 * Get recipe by id (`/recipes/find/v3?id={id}`)
 * Delete recipe by id (`/recipes/{id}`)
 
### Storage
 * RecipeCsvAdapter
 * RecipeDbAdapter (todo)
 
### How to Install and Run the Project
To be added.
 
