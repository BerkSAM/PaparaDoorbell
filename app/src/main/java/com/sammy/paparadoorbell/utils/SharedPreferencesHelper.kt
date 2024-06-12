package com.sammy.paparadoorbell.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.sammy.paparadoorbell.models.CategoryButtonEnum
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }
}

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    fun provideSharedPreferencesHelper(context: Context): SharedPreferencesHelper {
        return SharedPreferencesHelper(context)
    }
}

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("RecipeApp", Context.MODE_PRIVATE)

    fun setSelectedCategory(category: CategoryButtonEnum) {
        sharedPreferences.edit { putString("selectedCategory", category.name) }
    }

    fun getSelectedCategory(): CategoryButtonEnum {
        val categoryName = sharedPreferences.getString("selectedCategory", CategoryButtonEnum.Dessert.name)
        return CategoryButtonEnum.valueOf(categoryName ?: CategoryButtonEnum.Dessert.name)
    }
}