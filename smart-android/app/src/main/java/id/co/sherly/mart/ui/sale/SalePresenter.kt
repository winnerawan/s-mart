package id.co.sherly.mart.ui.sale

import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.Customer
import id.co.sherly.mart.data.model.ItemStock
import id.co.sherly.mart.data.model.PayType
import id.co.sherly.mart.network.ProtectedServiceBuilder
import id.co.sherly.mart.network.service.CategoryService
import id.co.sherly.mart.network.service.CustomerService
import id.co.sherly.mart.network.service.PayTypeService
import id.co.sherly.mart.network.service.StockService
import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SalePresenter @Inject constructor(
    private val view: SaleContract.View
): SaleContract.Presenter, BasePresenterImpl() {

    override fun customers() {
        val service = ProtectedServiceBuilder.buildService(CustomerService::class.java)
        val call = service.data()
        call.enqueue(object : Callback<List<Customer>?> {
            override fun onResponse(
                call: Call<List<Customer>?>,
                response: Response<List<Customer>?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { view.provideCustomers(it) }
                }
            }

            override fun onFailure(call: Call<List<Customer>?>, t: Throwable) {
            }
        })
    }

    override fun categories() {
        val service = ProtectedServiceBuilder.buildService(CategoryService::class.java)
        val call = service.data("items")
        call.enqueue(object : Callback<List<Category>?> {
            override fun onResponse(
                call: Call<List<Category>?>,
                response: Response<List<Category>?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { view.provideCategories(it) }
                }
            }

            override fun onFailure(call: Call<List<Category>?>, t: Throwable) {
            }

        })
    }
    override fun data(category: Int?, query: String?) {
        view.showProgress()

        val service = ProtectedServiceBuilder.buildService(StockService::class.java)
        val call = service.data(category, query)
        call.enqueue(object : Callback<List<ItemStock>?> {
            override fun onResponse(call: Call<List<ItemStock>?>, response: Response<List<ItemStock>?>) {
                response.body()?.let {
                    view.showData(it)
                }
            }

            override fun onFailure(call: Call<List<ItemStock>?>, t: Throwable) {
            }

        })
    }

    override fun payTypes() {
        val service = ProtectedServiceBuilder.buildService(PayTypeService::class.java)
        val call = service.data()
        call.enqueue(object : Callback<List<PayType>?> {
            override fun onResponse(
                call: Call<List<PayType>?>,
                response: Response<List<PayType>?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { view.showPayTypes(it) }
                }
            }

            override fun onFailure(call: Call<List<PayType>?>, t: Throwable) {
            }

        })
    }
}