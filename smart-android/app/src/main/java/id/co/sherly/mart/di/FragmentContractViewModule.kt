/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import id.co.sherly.mart.ui.account.AccountContract
import id.co.sherly.mart.ui.category.CategoryContract
import id.co.sherly.mart.ui.customer.CustomerContract
import id.co.sherly.mart.ui.history.HistoryContract
import id.co.sherly.mart.ui.home.HomeContract
import id.co.sherly.mart.ui.item.ItemContract
import id.co.sherly.mart.ui.supplier.SupplierContract

@Module
@InstallIn(FragmentComponent::class)
class FragmentContractViewModule {

    @Provides
    fun provideHomeFragmentView(fragment: Fragment): HomeContract.View = fragment as HomeContract.View

    @Provides
    fun provideHistoryFragmentView(fragment: Fragment): HistoryContract.View = fragment as HistoryContract.View

    @Provides
    fun provideAccountFragmentView(fragment: Fragment): AccountContract.View = fragment as AccountContract.View

    @Provides
    fun provideCategoryFragmentView(fragment: Fragment): CategoryContract.View = fragment as CategoryContract.View

    @Provides
    fun provideCustomerFragmentView(fragment: Fragment): CustomerContract.View = fragment as CustomerContract.View

    @Provides
    fun provideSupplierFragmentView(fragment: Fragment): SupplierContract.View = fragment as SupplierContract.View

    @Provides
    fun provideItemFragmentView(fragment: Fragment): ItemContract.View = fragment as ItemContract.View

}