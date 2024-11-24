package id.co.sherly.mart.ui.stock

import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.ItemStock

interface StockContract {

    interface View {
        fun showData(data: List<ItemStock>)
        fun provideCategories(data: List<Category>)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun data(category: Int?, query: String?)
        fun categories()
    }
}