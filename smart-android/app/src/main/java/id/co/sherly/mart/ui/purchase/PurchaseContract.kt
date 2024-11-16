package id.co.sherly.mart.ui.purchase

import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.Customer
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.data.model.Purchase
import id.co.sherly.mart.data.model.Supplier

interface PurchaseContract {

    interface View {
        fun showData(data: List<Purchase>)
        fun provideSuppliers(data: List<Supplier>)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun data(purchase: String?, query: String?)
        fun suppliers()
        fun create(category: Int, name: String, sku: String, description: String?, image: String?)
        fun update(id: String, category: Int, name: String, sku: String, description: String?, image: String?)
        fun delete(id: String)
    }
}