package com.sammy.paparadoorbell.feature.favoriterecipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sammy.paparadoorbell.data.source.local.RecipesDao
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipesDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipeViewModel @Inject constructor(
    private val recipesDao: RecipesDao,
) : ViewModel() {

    private val _favoriteRecipes = MutableLiveData<List<LocalRecipesDetail>>()
    val favoriteRecipes: LiveData<List<LocalRecipesDetail>> = _favoriteRecipes

    init {
        viewModelScope.launch {
            fetchFavoriteRecipes()
        }
    }

    private suspend fun fetchFavoriteRecipes() {
        recipesDao.getRecipeFav().collect { localRecipesList ->
            _favoriteRecipes.postValue(localRecipesList)
        }
    }


    fun markAsUnFavorite(recipeId: Int) {
        viewModelScope.launch {
            recipesDao.markAsUnFavorite(recipeId)
        }
    }
}