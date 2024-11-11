package id.co.sherly.mart.ui.customer

import id.co.sherly.mart.data.model.Customer

interface CustomerContract {

    interface View {
        fun showData(data: List<Customer>)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun data()
        fun create(name: String)
        fun update(id: Int, name: String)
        fun delete(id: Int)
    }
}