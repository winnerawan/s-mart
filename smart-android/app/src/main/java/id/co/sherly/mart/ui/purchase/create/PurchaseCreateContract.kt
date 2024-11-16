package id.co.sherly.mart.ui.purchase.create

import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.data.model.Supplier

interface PurchaseCreateContract {

    interface View {
        fun showData(data: List<Item>)
        fun provideCategories(data: List<Category>)
        fun provideSuppliers(suppliers: List<Supplier>)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun data(category: Int?, query: String?)
        fun suppliers()
        fun categories()
        fun updateTempQty(item: Item, tempQty: Int)
    }
}