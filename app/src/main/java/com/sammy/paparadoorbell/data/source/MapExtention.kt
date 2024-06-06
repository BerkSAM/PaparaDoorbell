package com.sammy.paparadoorbell.data.source

import com.sammy.paparadoorbell.data.source.local.entity.AnalyzedInstruction
import com.sammy.paparadoorbell.data.source.local.entity.Equipment
import com.sammy.paparadoorbell.data.source.local.entity.ExtendedIngredient
import com.sammy.paparadoorbell.data.source.local.entity.Ingredient
import com.sammy.paparadoorbell.data.source.local.entity.Length
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipes
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipesDetail
import com.sammy.paparadoorbell.data.source.local.entity.Measures
import com.sammy.paparadoorbell.data.source.local.entity.Metric
import com.sammy.paparadoorbell.data.source.local.entity.Step
import com.sammy.paparadoorbell.data.source.local.entity.Us
import com.sammy.paparadoorbell.data.source.network.response.recipes.RecipesResponse
import com.sammy.paparadoorbell.data.source.network.response.recipes.RecipesResult
import com.sammy.paparadoorbell.data.source.network.response.recipesDetail.RecipeDetailResponse
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

fun RecipeDetailResponse.toLocalRecipeDetail(): LocalRecipesDetail {
    return LocalRecipesDetail(
        aggregateLikes = this.aggregateLikes,
//        analyzedInstructions = this.analyzedInstructions?.map { instruction ->
//            instruction?.let {
//                AnalyzedInstruction(
//                    name = it.name,
//                    steps = it.steps?.map { step ->
//                        Step(
//                            number = step?.number,
//                            step = step?.step,
//                            ingredients = step?.ingredients?.map { ingredient ->
//                                Ingredient(
//                                    id = ingredient?.id,
//                                    name = ingredient?.name,
//                                    localizedName = ingredient?.localizedName,
//                                    image = ingredient?.image
//                                )
//                            },
//                            equipment = step?.equipment?.map { equipment ->
//                                Equipment(
//                                    id = equipment?.id,
//                                    name = equipment?.name,
//                                    localizedName = equipment?.localizedName,
//                                    image = equipment?.image
//                                )
//                            },
//                            length = step?.length?.let { length ->
//                                Length(
//                                    number = length.number,
//                                    unit = length.unit
//                                )
//                            }
//                        )
//                    }
//                )
//            }
//        },
        cheap = this.cheap,
//        cookingMinutes = this.cookingMinutes,
        creditsText = this.creditsText,
//        cuisines = this.cuisines,
        dairyFree = this.dairyFree,
//        diets = this.diets,
//        dishTypes = this.dishTypes,
//        extendedIngredients = this.extendedIngredients?.map { ingredient ->
//            ingredient?.let {
//                ExtendedIngredient(
//                    id = it.id,
//                    aisle = it.aisle,
//                    image = it.image,
//                    consistency = it.consistency,
//                    name = it.name,
//                    nameClean = it.nameClean,
//                    original = it.original,
//                    originalName = it.originalName,
//                    amount = it.amount,
//                    unit = it.unit,
//                    meta = it.meta,
//                    measures = it.measures?.let { measures ->
//                        Measures(
//                            us = measures.us?.let { us ->
//                                Us(
//                                    amount = us.amount,
//                                    unitShort = us.unitShort,
//                                    unitLong = us.unitLong
//                                )
//                            },
//                            metric = measures.metric?.let { metric ->
//                                Metric(
//                                    amount = metric.amount,
//                                    unitShort = metric.unitShort,
//                                    unitLong = metric.unitLong
//                                )
//                            }
//                        )
//                    }
//                )
//            }
//        },
        gaps = this.gaps,
        glutenFree = this.glutenFree,
        healthScore = this.healthScore,
        id = this.id,
        image = this.image,
        imageType = this.imageType,
        instructions = this.instructions,
        lowFodmap = this.lowFodmap,
//        occasions = this.occasions,
//        originalId = this.originalId,
//        preparationMinutes = this.preparationMinutes,
        pricePerServing = this.pricePerServing,
        readyInMinutes = this.readyInMinutes,
        servings = this.servings,
        sourceName = this.sourceName,
        sourceUrl = this.sourceUrl,
        spoonacularScore = this.spoonacularScore,
        spoonacularSourceUrl = this.spoonacularSourceUrl,
        summary = this.summary,
        sustainable = this.sustainable,
        title = this.title,
        vegan = this.vegan,
        vegetarian = this.vegetarian,
        veryHealthy = this.veryHealthy,
        veryPopular = this.veryPopular,
        weightWatcherSmartPoints = this.weightWatcherSmartPoints
    )
}
