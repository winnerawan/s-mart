package id.co.sherly.mart.ui.sale

import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.Customer
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.data.model.ItemStock
import id.co.sherly.mart.data.model.PayType

interface SaleContract {

    interface View {
        fun showData(data: List<ItemStock>)
        fun provideCategories(data: List<Category>)
        fun provideCustomers(suppliers: List<Customer>)
        fun showProgress()
        fun hideProgress()
        fun showPayTypes(payTypes: List<PayType>)
    }

    interface Presenter {
        fun data(category: Int?, query: String?)
        fun categories()
        fun customers()
        fun payTypes()
    }
}