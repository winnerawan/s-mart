/*
 * Copyright 2024 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.di

import android.app.Application
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @UserPrefs
    fun provideUserPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("settings", 0)


}
@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class UserPrefs