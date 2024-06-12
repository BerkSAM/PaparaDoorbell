@file:Suppress("DEPRECATION")

package com.sammy.paparadoorbell.feature.recipe

import com.sammy.paparadoorbell.data.source.network.response.recipes.Recipe
import com.sammy.paparadoorbell.data.source.network.response.recipes.RecipesResponse
import com.sammy.paparadoorbell.data.SpoonacularRepository
import com.sammy.paparadoorbell.data.source.local.RecipesDao
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipes
import com.sammy.paparadoorbell.models.CategoryButtonEnum
import com.sammy.paparadoorbell.utils.SharedPreferencesHelper
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
import kotlin.test.assertEquals

class RecipeViewModelTest {
    private lateinit var viewModel: RecipeViewModel
    private lateinit var repository: SpoonacularRepository
    private lateinit var recipesDao: RecipesDao
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        repository = mockk()
        recipesDao = mockk {
            every { getNotifications() } returns flowOf(listOf())
            every { getRecipeById(any()) } returns flowOf(LocalRecipes(id = 1, title = "Test Recipe", image = "test.jpg"))
        }
        sharedPreferencesHelper = mockk {
            every { getSelectedCategory() } returns CategoryButtonEnum.Dessert
        }
        viewModel = RecipeViewModel(repository, recipesDao, sharedPreferencesHelper)
    }
    @Test
    fun fetchRecipes_updatesStateFlow() = runTest {
        val expectedRecipes = listOf(Recipe(id = 1, title = "Test Recipe", image = "test.jpg"))
        val expectedResponse = RecipesResponse(results = expectedRecipes)
        val expectedFlow = flowOf(ApiResult.Success(data = expectedResponse))
        coEvery { repository.getRecipes(type = "Dessert") } returns expectedFlow

        viewModel.fetchRecipes("Dessert")

        val actualState = viewModel.uiState.first { !it.isLoading }
        assertEquals(false, actualState.isLoading)
        assertEquals(false, actualState.isError)
        assertEquals(expectedRecipes, actualState.recipes)
    }
}
