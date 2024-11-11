package id.co.sherly.mart.ui.category

import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.response.SuccessResponse
import id.co.sherly.mart.network.ProtectedServiceBuilder
import id.co.sherly.mart.network.service.CategoryService
import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CategoryPresenter @Inject constructor(
    private val view: CategoryContract.View
) : CategoryContract.Presenter, BasePresenterImpl() {

    init {
        fetchCategories()
    }

    override fun fetchCategories() {
        view.showProgress()
        val service = ProtectedServiceBuilder.buildService(CategoryService::class.java)
        val call = service.data("items")
        call.enqueue(object : Callback<List<Category>?> {
            override fun onResponse(
                call: Call<List<Category>?>,
                response: Response<List<Category>?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { view.showCategories(it) }
                }
            }

            override fun onFailure(call: Call<List<Category>?>, t: Throwable) {
            }

        })
    }

    override fun createCategory(name: String) {
        val service = ProtectedServiceBuilder.buildService(CategoryService::class.java)
        val call = service.create(name)
        call.enqueue(object : Callback<SuccessResponse?> {
            override fun onResponse(
                call: Call<SuccessResponse?>,
                response: Response<SuccessResponse?>
            ) {
                if (response.isSuccessful) {
                    fetchCategories()
                }
            }

            override fun onFailure(call: Call<SuccessResponse?>, t: Throwable) {
            }

        })
    }

    override fun updateCategory(id: Int, name: String) {
        val service = ProtectedServiceBuilder.buildService(CategoryService::class.java)
        val call = service.update(id, name)
        call.enqueue(object : Callback<SuccessResponse?> {
            override fun onResponse(
                call: Call<SuccessResponse?>,
                response: Response<SuccessResponse?>
            ) {
                if (response.isSuccessful) {
                    fetchCategories()
                }
            }

            override fun onFailure(call: Call<SuccessResponse?>, t: Throwable) {
            }

        })
    }

    override fun deleteCategory(id: Int) {
        val service = ProtectedServiceBuilder.buildService(CategoryService::class.java)
        val call = service.delete(id)
        call.enqueue(object : Callback<SuccessResponse?> {
            override fun onResponse(
                call: Call<SuccessResponse?>,
                response: Response<SuccessResponse?>
            ) {
                if (response.isSuccessful) {
                    fetchCategories()
                }
            }

            override fun onFailure(call: Call<SuccessResponse?>, t: Throwable) {
            }

        })
    }
}