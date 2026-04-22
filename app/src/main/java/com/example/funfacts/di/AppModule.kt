package com.example.funfacts.di

import android.content.Context
import com.example.funfacts.data.DatabaseProvider
import com.example.funfacts.data.dao.CustomFactDao
import com.example.funfacts.data.database.AppDatabase
import com.example.funfacts.data.repository.CustomFactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides // used when u have provided the implementation already (use @binds on interface)
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return DatabaseProvider.getDatabase(context)
    }

    @Provides
    fun provideCustomFactDao(database: AppDatabase): CustomFactDao {
        return database.dao()
    }

    @Provides
    @Singleton
    fun provideCustomFactRepository(dao: CustomFactDao): CustomFactRepository {
        return CustomFactRepository(dao)
    }
}
