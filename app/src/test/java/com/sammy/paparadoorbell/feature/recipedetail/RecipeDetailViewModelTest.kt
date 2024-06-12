@file:Suppress("DEPRECATION")

package com.sammy.paparadoorbell.feature.recipedetail

import com.sammy.paparadoorbell.data.source.network.response.recipesDetail.RecipeDetailResponse
import com.sammy.paparadoorbell.data.SpoonacularRepository
import com.sammy.paparadoorbell.data.source.local.RecipesDao
import com.sammy.paparadoorbell.utils.ApiResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import androidx.lifecycle.SavedStateHandle
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipesDetail
import kotlin.test.assertEquals

class RecipeDetailViewModelTest {
    private lateinit var viewModel: RecipeDetailViewModel
    private lateinit var repository: SpoonacularRepository
    private lateinit var recipesDao: RecipesDao
    private lateinit var savedStateHandle: SavedStateHandle

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        repository = mockk()
        recipesDao = mockk {
            every { getRecipeDetailById(any()) } returns flowOf(
                LocalRecipesDetail(
                    id = 1,
                    aggregateLikes = null,
                    cheap = null,
                    creditsText = null,
                    dairyFree = null,
                    gaps = null,
                    glutenFree = null,
                    healthScore = null,
                    image = null,
                    imageType = null,
                    instructions = null,
                    lowFodmap = null,
                    pricePerServing = null,
                    readyInMinutes = null,
                    servings = null,
                    sourceName = null,
                    sourceUrl = null,
                    spoonacularScore = null,
                    spoonacularSourceUrl = null,
                    summary = null,
                    sustainable = null,
                    title = null,
                    vegan = null,
                    vegetarian = null,
                    veryHealthy = null,
                    veryPopular = null,
                    weightWatcherSmartPoints = null,
                    isFav = false
                )
            )
        }
        savedStateHandle = mockk()
        viewModel = RecipeDetailViewModel(repository, savedStateHandle, recipesDao)
    }

    @Test
    fun fetchRecipeDetails_updatesStateFlow() = runTest {
        val expectedRecipe = RecipeDetailResponse(
            aggregateLikes = null,
            analyzedInstructions = null,
            cheap = null,
            cookingMinutes = null,
            creditsText = null,
            cuisines = null,
            dairyFree = null,
            diets = null,
            dishTypes = null,
            extendedIngredients = null,
            gaps = null,
            glutenFree = null,
            healthScore = null,
            id = 1,
            image = null,
            imageType = null,
            instructions = null,
            lowFodmap = null,
            occasions = null,
            originalId = null,
            preparationMinutes = null,
            pricePerServing = null,
            readyInMinutes = null,
            servings = null,
            sourceName = null,
            sourceUrl = null,
            spoonacularScore = null,
            spoonacularSourceUrl = null,
            summary = null,
            sustainable = null,
            title = null,
            vegan = null,
            vegetarian = null,
            veryHealthy = null,
            veryPopular = null,
            weightWatcherSmartPoints = null,
            isFav = null
        )
        val expectedFlow = flowOf(ApiResult.Success(data = expectedRecipe))
        coEvery { repository.getRecipesDetail(any()) } returns expectedFlow

        viewModel.fetchRecipeDetails(1)

        val actualState = viewModel.uiState.first { !it.isLoading }
        assertEquals(false, actualState.isLoading)
        assertEquals(false, actualState.isError)
        assertEquals(expectedRecipe, actualState.recipe)
        assertEquals(false, actualState.isFav)
    }
}