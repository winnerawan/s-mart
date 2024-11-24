package id.co.sherly.mart.ui.stock

import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.data.model.ItemStock
import id.co.sherly.mart.network.ProtectedServiceBuilder
import id.co.sherly.mart.network.service.CategoryService
import id.co.sherly.mart.network.service.ItemService
import id.co.sherly.mart.network.service.StockService
import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class StockPresenter @Inject constructor(
    private val view: StockContract.View
): StockContract.Presenter, BasePresenterImpl() {

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
}