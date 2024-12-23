/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.di

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import id.co.sherly.mart.ui.main.MainContract
import id.co.sherly.mart.ui.main.MainPagerAdapter
import id.co.sherly.mart.ui.media.select.SelectMediaContract
import id.co.sherly.mart.ui.purchase.create.PurchaseCreateContract
import id.co.sherly.mart.ui.purchase.imports.PurchaseImportContract
import id.co.sherly.mart.ui.sign.`in`.SignInContract
import id.co.sherly.mart.ui.splash.SplashContract

@Module
@InstallIn(ActivityComponent::class)
class ActivityContractViewModule {

    @Provides
    fun splashView(activity: Activity): SplashContract.View {
        return activity as SplashContract.View
    }

    @Provides
    fun signInView(activity: Activity): SignInContract.View {
        return activity as SignInContract.View
    }

    @Provides
    fun mainView(activity: Activity): MainContract.View {
        return activity as MainContract.View
    }

    @Provides
    fun selectMediaView(activity: Activity): SelectMediaContract.View {
        return activity as SelectMediaContract.View
    }

    @Provides
    fun purchaseCreateView(activity: Activity): PurchaseCreateContract.View {
        return activity as PurchaseCreateContract.View
    }

    @Provides
    fun purchaseImportView(activity: Activity): PurchaseImportContract.View {
        return activity as PurchaseImportContract.View
    }

    @Provides
    fun provideMainPagerAdapter(fragmentActivity: FragmentActivity): MainPagerAdapter {
        return MainPagerAdapter(fragmentActivity)
    }
}