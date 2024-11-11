package id.co.sherly.mart.ui.supplier

import id.co.sherly.mart.data.model.Customer
import id.co.sherly.mart.data.model.Supplier

interface SupplierContract {

    interface View {
        fun showData(data: List<Supplier>)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun data()
        fun create(name: String, address: String?, phone: String?)
        fun update(id: Int, name: String, address: String?, phone: String?)
        fun delete(id: Int)
    }
}