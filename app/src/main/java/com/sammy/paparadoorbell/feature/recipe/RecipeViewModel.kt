package com.sammy.paparadoorbell.feature.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sammy.paparadoorbell.data.SpoonacularRepository
import com.sammy.paparadoorbell.data.source.local.RecipesDao
import com.sammy.paparadoorbell.data.source.network.response.recipes.Recipe
import com.sammy.paparadoorbell.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val savedStateHandle: SavedStateHandle,
    private val recipesDao: RecipesDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeState())
    val uiState: StateFlow<RecipeState> = _uiState

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes


    suspend fun fetchRecipes(type: String = "Dessert") {
        _uiState.update { it.copy(isLoading = true) }
        repository.getRecipes(type).collect { result ->
            when (result) {
                is ApiResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }

                is ApiResult.Success -> {
                    _recipes.value = result.data?.results ?: emptyList()
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

    suspend fun fetchFavoriteRecipes() {
        val response = recipesDao.getRecipeFav()
    }

    fun markAsFavoriteRecipe(recipeId: Int) {
        viewModelScope.launch {
            recipesDao.markAsFavoriteRecipe(recipeId)
        }
    }
}