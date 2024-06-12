package com.sammy.paparadoorbell.feature.recipe

import com.sammy.paparadoorbell.utils.SharedPreferencesHelper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sammy.paparadoorbell.data.SpoonacularRepository
import com.sammy.paparadoorbell.data.source.local.RecipesDao
import com.sammy.paparadoorbell.data.source.local.entity.Notification
import com.sammy.paparadoorbell.data.source.network.response.recipes.Recipe
import com.sammy.paparadoorbell.models.CategoryButtonEnum
import com.sammy.paparadoorbell.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecipeState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val recipes: List<Recipe> = emptyList()
)

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: SpoonacularRepository,
    private val recipesDao: RecipesDao,
    private val sharedPreferencesHelper: SharedPreferencesHelper

) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeState())
    val uiState: StateFlow<RecipeState> = _uiState

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes


    private val _selectedCategory = MutableStateFlow(sharedPreferencesHelper.getSelectedCategory())
    val selectedCategory = _selectedCategory.asStateFlow()

    fun setSelectedCategory(category: CategoryButtonEnum) {
        _selectedCategory.value = category
        sharedPreferencesHelper.setSelectedCategory(category)
    }

    suspend fun fetchRecipes(type: String = "Dessert") {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getRecipes(type).collect { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is ApiResult.Success -> {
                        recipesDao.deleteAllRecipes()
                        result.data?.results?.let { recipesFromApi ->
                            _recipes.value = recipesFromApi
                            recipesFromApi.forEach { recipeFromApi ->
                                val recipeInDb =
                                    recipesDao.getRecipeById(recipeFromApi.id).firstOrNull()
                                if (recipeInDb == null) {
                                    val notification =
                                        Notification(title = "New Recipe : " + recipeFromApi.title)
                                    recipesDao.insertNotification(notification)
                                }
                            }
                        } ?: run {
                            _recipes.value = emptyList()
                        }
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                recipes = _recipes.value
                            )
                        }
                    }

                    is ApiResult.Error -> {
                        _uiState.update { it.copy(isLoading = false, isError = true) }
                    }
                }
            }
        }
    }

    val notifications: Flow<List<Notification>> = recipesDao.getNotifications()
}