package id.co.sherly.mart.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.co.sherly.mart.ui.category.ItemCategoryAdapter
import id.co.sherly.mart.ui.customer.ItemCustomerAdapter
import id.co.sherly.mart.ui.supplier.ItemSupplierAdapter

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Provides
    fun provideCategoryAdapter(): ItemCategoryAdapter {
        return ItemCategoryAdapter(mutableListOf())
    }

    @Provides
    fun provideCustomerAdapter(): ItemCustomerAdapter {
        return ItemCustomerAdapter(mutableListOf())
    }

    @Provides
    fun provideSupplierAdapter(): ItemSupplierAdapter {
        return ItemSupplierAdapter(mutableListOf())
    }
}