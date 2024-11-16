package id.co.sherly.mart.ui.purchase.create

import id.co.sherly.mart.data.db.dao.PurchaseDao
import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.data.model.Supplier
import id.co.sherly.mart.data.model.response.SuccessResponse
import id.co.sherly.mart.network.ProtectedServiceBuilder
import id.co.sherly.mart.network.service.CategoryService
import id.co.sherly.mart.network.service.ItemService
import id.co.sherly.mart.network.service.SupplierService
import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PurchaseCreatePresenter @Inject constructor(
    private val view: PurchaseCreateContract.View,
    private val dao: PurchaseDao
) : PurchaseCreateContract.Presenter, BasePresenterImpl() {


    init {
//        categories()
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

        val service = ProtectedServiceBuilder.buildService(ItemService::class.java)
        val call = service.data(category, query)
        call.enqueue(object : Callback<List<Item>?> {
            override fun onResponse(call: Call<List<Item>?>, response: Response<List<Item>?>) {
                response.body()?.let {
                    view.showData(it)
                }
            }

            override fun onFailure(call: Call<List<Item>?>, t: Throwable) {
            }

        })
    }

    override fun updateTempQty(item: Item, tempQty: Int) {
//        val service = ProtectedServiceBuilder.buildService(ItemService::class.java)
//        val call = service.updateTempQty(item.id!!, tempQty)
//        call.enqueue(object : Callback<SuccessResponse?> {
//            override fun onResponse(
//                call: Call<SuccessResponse?>,
//                response: Response<SuccessResponse?>
//            ) {
//                if (response.isSuccessful) {
//
//                }
//            }
//
//            override fun onFailure(call: Call<SuccessResponse?>, t: Throwable) {
//            }
//
//        })
    }

}