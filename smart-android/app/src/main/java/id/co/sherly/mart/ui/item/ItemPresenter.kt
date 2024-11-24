package id.co.sherly.mart.ui.item

import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.Customer
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.data.model.response.SuccessResponse
import id.co.sherly.mart.network.ProtectedServiceBuilder
import id.co.sherly.mart.network.service.CategoryService
import id.co.sherly.mart.network.service.CustomerService
import id.co.sherly.mart.network.service.ItemService
import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ItemPresenter @Inject constructor(
    private val view: ItemContract.View
): ItemContract.Presenter, BasePresenterImpl() {

    init {
//        categories()
//        data(null, null)
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

    override fun create(
        category: Int,
        name: String,
        sku: String,
        description: String?,
        image: String?
    ) {
        val service = ProtectedServiceBuilder.buildService(ItemService::class.java)
        val call = service.create(category, name, sku, description, image)
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

    override fun update(
        id: String,
        category: Int,
        name: String,
        sku: String,
        description: String?,
        image: String?
    ) {
        val service = ProtectedServiceBuilder.buildService(ItemService::class.java)
        val call = service.update(id, category, name, sku, description, image)
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

    override fun delete(id: String) {
        val service = ProtectedServiceBuilder.buildService(ItemService::class.java)
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