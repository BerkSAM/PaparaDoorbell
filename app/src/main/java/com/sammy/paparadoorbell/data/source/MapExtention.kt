package com.sammy.paparadoorbell.data.source

import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipes
import com.sammy.paparadoorbell.data.source.network.response.recipes.RecipesResponse
import com.sammy.paparadoorbell.data.source.network.response.recipes.RecipesResult
import com.sammy.paparadoorbell.models.RecipesModel

fun RecipesResponse.toLocal(): List<LocalRecipes> {
    return this.results.map {
        LocalRecipes(
            id = it.id,
            title = it.title,
            image = it.image
        )
    }
}

fun RecipesResult.toLocal(): LocalRecipes {
    return LocalRecipes(
        id = this.id,
        title = this.title,
        image = this.image
    )
}

fun LocalRecipes.toModel(): RecipesModel {
    return RecipesModel(
        id = this.id,
        title = this.title,
        image = this.image
    )
}