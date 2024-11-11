package id.co.sherly.mart.ui.supplier

import id.co.sherly.mart.data.model.Supplier
import id.co.sherly.mart.data.model.response.SuccessResponse
import id.co.sherly.mart.network.ProtectedServiceBuilder
import id.co.sherly.mart.network.service.SupplierService
import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SupplierPresenter @Inject constructor(
    private val view: SupplierContract.View
) : SupplierContract.Presenter, BasePresenterImpl() {

    init {
        data()
    }

    override fun data() {
        view.showProgress()
        val service = ProtectedServiceBuilder.buildService(SupplierService::class.java)
        val call = service.data()
        call.enqueue(object : Callback<List<Supplier>?> {
            override fun onResponse(
                call: Call<List<Supplier>?>,
                response: Response<List<Supplier>?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { view.showData(it) }
                }
            }

            override fun onFailure(call: Call<List<Supplier>?>, t: Throwable) {
            }

        })
    }

    override fun create(name: String, address: String?, phone: String?) {
        val service = ProtectedServiceBuilder.buildService(SupplierService::class.java)
        val call = service.create(name, address, phone)
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

    override fun update(id: Int, name: String, address: String?, phone: String?) {
        val service = ProtectedServiceBuilder.buildService(SupplierService::class.java)
        val call = service.update(id, name, address, phone)
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
        val service = ProtectedServiceBuilder.buildService(SupplierService::class.java)
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