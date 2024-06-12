package com.sammy.paparadoorbell.di

import android.content.Context
import androidx.room.Room
import com.sammy.paparadoorbell.data.SpoonacularRepository
import com.sammy.paparadoorbell.data.SpoonacularRepositoryImpl
import com.sammy.paparadoorbell.data.source.local.RecipesDao
import com.sammy.paparadoorbell.data.source.local.RecipesDatabase
import com.sammy.paparadoorbell.data.source.network.NetworkDataSource
import com.sammy.paparadoorbell.data.source.network.SpoonacularApiService
import com.sammy.paparadoorbell.data.source.network.SpoonacularNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindSpoonacularRepository(
        spoonacularRepositoryImpl: SpoonacularRepositoryImpl
    ): SpoonacularRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabasesModule {

    @Provides
    @Singleton
    fun provideRecipesDao(database: RecipesDatabase): RecipesDao {
        return database.recipesDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): RecipesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RecipesDatabase::class.java,
            "recipe.db"
        ).fallbackToDestructiveMigration().build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
class DataProviderModule {

    // provide retrofit
    @Provides
    @Singleton
    fun provideNetworkDataSource(spoonacularApiService: SpoonacularApiService): NetworkDataSource {
        return SpoonacularNetworkDataSource(spoonacularApiService)
    }

    @Provides
    @Singleton
    fun provideSpoonacularApiService(retrofit: Retrofit): SpoonacularApiService {
        return retrofit.create(SpoonacularApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
