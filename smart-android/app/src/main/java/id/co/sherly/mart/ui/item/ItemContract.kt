package id.co.sherly.mart.ui.item

import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.Customer
import id.co.sherly.mart.data.model.Item

interface ItemContract {

    interface View {
        fun showData(data: List<Item>)
        fun provideCategories(data: List<Category>)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun data(category: Int?, query: String?)
        fun categories()
        fun create(category: Int, name: String, sku: String, description: String?, image: String?)
        fun update(id: String, category: Int, name: String, sku: String, description: String?, image: String?)
        fun delete(id: String)
    }
}