package id.co.sherly.mart.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.co.sherly.mart.ui.category.ItemCategoryAdapter
import id.co.sherly.mart.ui.customer.ItemCustomerAdapter
import id.co.sherly.mart.ui.item.CategoryAdapter
import id.co.sherly.mart.ui.item.ItemAdapter
import id.co.sherly.mart.ui.item.SpinnerCategoryAdapter
import id.co.sherly.mart.ui.media.ItemMediaAdapter
import id.co.sherly.mart.ui.purchase.ItemPurchaseAdapter
import id.co.sherly.mart.ui.purchase.SupplierAdapter
import id.co.sherly.mart.ui.purchase.create.ItemCartPurchaseAdapter
import id.co.sherly.mart.ui.purchase.create.ItemViewPurchaseAdapter
import id.co.sherly.mart.ui.supplier.ItemSupplierAdapter

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Provides
    fun provideItemCategoryAdapter(): ItemCategoryAdapter {
        return ItemCategoryAdapter(mutableListOf())
    }

    @Provides
    fun provideCustomerAdapter(): ItemCustomerAdapter {
        return ItemCustomerAdapter(mutableListOf())
    }

    @Provides
    fun provideItemSupplierAdapter(): ItemSupplierAdapter {
        return ItemSupplierAdapter(mutableListOf())
    }

    @Provides
    fun provideItemAdapter(): ItemAdapter {
        return ItemAdapter(mutableListOf())
    }

    @Provides
    fun provideCategoryAdapter(): CategoryAdapter {
        return CategoryAdapter(mutableListOf())
    }

    @Provides
    fun provideSupplierAdapter(): SupplierAdapter {
        return SupplierAdapter(mutableListOf())
    }

    @Provides
    fun provideItemMediaAdapter(): ItemMediaAdapter {
        return ItemMediaAdapter(mutableListOf())
    }

    @Provides
    fun providePurchaseAdapter(): ItemPurchaseAdapter {
        return ItemPurchaseAdapter(mutableListOf())
    }

    @Provides
    fun provideItemViewPurchaseAdapter(): ItemViewPurchaseAdapter {
        return ItemViewPurchaseAdapter(mutableListOf())
    }

    @Provides
    fun provideItemCartPurchaseAdapter(): ItemCartPurchaseAdapter {
        return ItemCartPurchaseAdapter(mutableListOf())
    }

}