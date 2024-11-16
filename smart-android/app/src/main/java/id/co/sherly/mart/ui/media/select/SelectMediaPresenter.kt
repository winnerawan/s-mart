package id.co.sherly.mart.ui.media.select

import id.co.sherly.mart.data.model.Media
import id.co.sherly.mart.network.ProtectedServiceBuilder
import id.co.sherly.mart.network.service.MediaService
import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SelectMediaPresenter @Inject constructor(
    private val view: SelectMediaContract.View
): SelectMediaContract.Presenter, BasePresenterImpl() {


    override fun data() {
        view.showProgress()
        val service = ProtectedServiceBuilder.buildService(MediaService::class.java)
        val call = service.fetchMedia()
        call.enqueue(object : Callback<List<Media>?> {
            override fun onResponse(
                call: Call<List<Media>?>,
                response: Response<List<Media>?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { view.showData(it) }
                }
            }

            override fun onFailure(call: Call<List<Media>?>, t: Throwable) {
            }

        })
    }
}