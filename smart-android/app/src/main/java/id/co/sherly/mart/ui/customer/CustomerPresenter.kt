package id.co.sherly.mart.ui.customer

import id.co.sherly.mart.data.model.Customer
import id.co.sherly.mart.data.model.response.SuccessResponse
import id.co.sherly.mart.network.ProtectedServiceBuilder
import id.co.sherly.mart.network.service.CustomerService
import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CustomerPresenter @Inject constructor(
    private val view: CustomerContract.View
) : CustomerContract.Presenter, BasePresenterImpl() {

    init {
        data()
    }

    override fun data() {
        view.showProgress()
        val service = ProtectedServiceBuilder.buildService(CustomerService::class.java)
        val call = service.data("items")
        call.enqueue(object : Callback<List<Customer>?> {
            override fun onResponse(
                call: Call<List<Customer>?>,
                response: Response<List<Customer>?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { view.showData(it) }
                }
            }

            override fun onFailure(call: Call<List<Customer>?>, t: Throwable) {
            }

        })
    }

    override fun create(name: String) {
        val service = ProtectedServiceBuilder.buildService(CustomerService::class.java)
        val call = service.create(name)
        call.enqueue(object : Callback<SuccessResponse?> {
            override fun onResponse(
                call: Call<SuccessResponse?>,
                response: Response<SuccessResponse?>
            ) {
                if (response.isSuccessful) {
                    data()
                }
            }

            override fun onFailure(call: Call<SuccessResponse?>, t: Throwable) {
            }

        })
    }

    override fun update(id: Int, name: String) {
        val service = ProtectedServiceBuilder.buildService(CustomerService::class.java)
        val call = service.update(id, name)
        call.enqueue(object : Callback<SuccessResponse?> {
            override fun onResponse(
                call: Call<SuccessResponse?>,
                response: Response<SuccessResponse?>
            ) {
                if (response.isSuccessful) {
                    data()
                }
            }

            override fun onFailure(call: Call<SuccessResponse?>, t: Throwable) {
            }

        })
    }

    override fun delete(id: Int) {
        val service = ProtectedServiceBuilder.buildService(CustomerService::class.java)
        val call = service.delete(id)
        call.enqueue(object : Callback<SuccessResponse?> {
            override fun onResponse(
                call: Call<SuccessResponse?>,
                response: Response<SuccessResponse?>
            ) {
                if (response.isSuccessful) {
                    data()
                }
            }

            override fun onFailure(call: Call<SuccessResponse?>, t: Throwable) {
            }

        })
    }
}