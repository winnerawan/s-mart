package id.co.sherly.mart.ui.purchase

import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.Customer
import id.co.sherly.mart.data.model.Purchase
import id.co.sherly.mart.data.model.Supplier
import id.co.sherly.mart.data.model.response.SuccessResponse
import id.co.sherly.mart.network.ProtectedServiceBuilder
import id.co.sherly.mart.network.service.CategoryService
import id.co.sherly.mart.network.service.CustomerService
import id.co.sherly.mart.network.service.PurchaseService
import id.co.sherly.mart.network.service.SupplierService
import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PurchasePresenter @Inject constructor(
    private val view: PurchaseContract.View
): PurchaseContract.Presenter, BasePresenterImpl() {

    init {
//        suppliers()
//        data(null, null)
    }

    override fun suppliers() {
        val service = ProtectedServiceBuilder.buildService(SupplierService::class.java)
        val call = service.data()
        call.enqueue(object : Callback<List<Supplier>?> {
            override fun onResponse(
                call: Call<List<Supplier>?>,
                response: Response<List<Supplier>?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { view.provideSuppliers(it) }
                }
            }

            override fun onFailure(call: Call<List<Supplier>?>, t: Throwable) {
            }
        })
    }
    override fun data(purchase: String?, query: String?) {
        view.showProgress()

        val service = ProtectedServiceBuilder.buildService(PurchaseService::class.java)
        val call = service.data()
        call.enqueue(object : Callback<List<Purchase>?> {
            override fun onResponse(call: Call<List<Purchase>?>, response: Response<List<Purchase>?>) {
                response.body()?.let {
                    view.showData(it)
                }
            }

            override fun onFailure(call: Call<List<Purchase>?>, t: Throwable) {
            }

        })
    }

    override fun create(
        category: Int,
        name: String,
        sku: String,
        description: String?,
        image: String?
    ) {
        val service = ProtectedServiceBuilder.buildService(PurchaseService::class.java)
//        val call = service.create(category, name, sku, description, image)
//        call.enqueue(object : Callback<SuccessResponse?> {
//            override fun onResponse(
//                call: Call<SuccessResponse?>,
//                response: Response<SuccessResponse?>
//            ) {
//                if (response.isSuccessful) {
//                    data(null, null)
//                }
//            }
//
//            override fun onFailure(call: Call<SuccessResponse?>, t: Throwable) {
//            }
//
//        })
    }

    override fun update(
        id: String,
        category: Int,
        name: String,
        sku: String,
        description: String?,
        image: String?
    ) {
        val service = ProtectedServiceBuilder.buildService(PurchaseService::class.java)
//        val call = service.update(id, category, name, sku, description, image)
//        call.enqueue(object : Callback<SuccessResponse?> {
//            override fun onResponse(
//                call: Call<SuccessResponse?>,
//                response: Response<SuccessResponse?>
//            ) {
//                if (response.isSuccessful) {
//                    data(null, null)
//                }
//            }
//
//            override fun onFailure(call: Call<SuccessResponse?>, t: Throwable) {
//            }
//
//        })
    }

    override fun delete(id: String) {
        val service = ProtectedServiceBuilder.buildService(PurchaseService::class.java)
        val call = service.delete(id)
        call.enqueue(object : Callback<SuccessResponse?> {
            override fun onResponse(
                call: Call<SuccessResponse?>,
                response: Response<SuccessResponse?>
            ) {
                if (response.isSuccessful) {
                    data(null, null)
                }
            }

            override fun onFailure(call: Call<SuccessResponse?>, t: Throwable) {
            }

        })
    }
}