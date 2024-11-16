/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.co.sherly.mart.BuildConfig
import id.co.sherly.mart.data.db.AppDatabase
import id.co.sherly.mart.data.pref.UserPreferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, BuildConfig.DATABASE
    ).fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    fun providePurchaseDao(db: AppDatabase) = db.purchaseDao()

}