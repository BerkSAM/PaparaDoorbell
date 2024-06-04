package com.sammy.paparadoorbell.feature.recipe

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sammy.paparadoorbell.data.SpoonacularRepository
import com.sammy.paparadoorbell.data.source.network.response.recipes.Recipe
import com.sammy.paparadoorbell.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class RecipeState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val recipes: List<Recipe> = emptyList()
)

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: SpoonacularRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeState())
    val uiState: StateFlow<RecipeState> = _uiState

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    suspend fun fetchRecipes() {
        _uiState.update { it.copy(isLoading = true) }
        repository.getRecipes().collect { result ->
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
                    Log.e("HomeViewModel", "Error fetching recipes: vallahi error")
                }
            }
        }
    }
}