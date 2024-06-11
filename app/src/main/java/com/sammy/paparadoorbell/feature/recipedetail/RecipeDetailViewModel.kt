package com.sammy.paparadoorbell.feature.recipedetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sammy.paparadoorbell.data.SpoonacularRepository
import com.sammy.paparadoorbell.data.source.local.RecipesDao
import com.sammy.paparadoorbell.data.source.network.response.recipesDetail.RecipeDetailResponse
import com.sammy.paparadoorbell.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject


data class RecipeDetailState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val recipe: RecipeDetailResponse? = null,
    val isFav: Boolean = false,

    )

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: SpoonacularRepository,
    private val savedStateHandle: SavedStateHandle,
    private val recipesDao: RecipesDao,
) : ViewModel() {

    val _uiState = MutableStateFlow(RecipeDetailState())
    val uiState: StateFlow<RecipeDetailState> = _uiState

    suspend fun fetchRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            _uiState.value = RecipeDetailState(isLoading = true)
            repository.getRecipesDetail(recipeId).collect { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _uiState.value = RecipeDetailState(isLoading = true)
                    }

                    is ApiResult.Success -> {
                        var isFavorite = false
                        recipesDao.getRecipeDetailById(recipeId).take(1)
                            .collect { localRecipeDetail ->
                                isFavorite = localRecipeDetail.isFav
                            }
                        _uiState.value = RecipeDetailState(
                            isLoading = false,
                            recipe = result.data,
                            isFav = isFavorite,
                        )
                    }

                    is ApiResult.Error -> {
                        _uiState.value = RecipeDetailState(
                            isLoading = false,
                            isError = true
                        )
                        Log.e(
                            "RecipeDetailViewModel",
                            "Error fetching recipe details: ${result.message}"
                        )
                    }
                }
            }
        }
    }

    fun markAsFavorite(recipeId: Int) {
        viewModelScope.launch {
            recipesDao.markAsFavorite(recipeId)
        }
    }

    fun markAsUnFavorite(recipeId: Int) {
        viewModelScope.launch {
            recipesDao.markAsUnFavorite(recipeId)
        }
    }
}