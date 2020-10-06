package com.kebinr.karmaaplication.di

import com.kebinr.karmaaplication.repository.FirebaseAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideAuthRepository() : FirebaseAuthRepository {
        val firebaseAuthRepository =
            FirebaseAuthRepository()
        return firebaseAuthRepository
    }
}