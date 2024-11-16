package id.co.sherly.mart.ui.media

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import id.co.sherly.mart.BuildConfig
import id.co.sherly.mart.data.model.Media
import id.co.sherly.mart.data.model.response.SuccessResponse
import id.co.sherly.mart.network.ProtectedServiceBuilder
import id.co.sherly.mart.network.service.MediaService
import id.co.sherly.mart.ui.account.auth.Auth
import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import net.gotev.uploadservice.data.UploadInfo
import net.gotev.uploadservice.exceptions.UploadError
import net.gotev.uploadservice.exceptions.UserCancelledUploadException
import net.gotev.uploadservice.network.ServerResponse
import net.gotev.uploadservice.observer.request.RequestObserverDelegate
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MediaPresenter @Inject constructor(
    private val view: MediaContract.View
) : MediaContract.Presenter, BasePresenterImpl() {

    init {
        data()
    }

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

    override fun create(context: Context, path: String, lifeCycleOwner: LifecycleOwner) {
        MultipartUploadRequest(context = context, "${BuildConfig.API_URL}media/upload")
            .addFileToUpload(filePath = path, parameterName = "media")
            .addHeader("Authorization", "Bearer ${Auth.getIdentity().token.accessToken}")
            .addHeader("Content-Type", "multipart/form-data")
            .subscribe(
                context = context,
                lifecycleOwner = lifeCycleOwner,
                delegate = object :
                    RequestObserverDelegate {
                    override fun onProgress(context: Context, uploadInfo: UploadInfo) {
                        view.showProgress()
                    }

                    override fun onSuccess(
                        context: Context,
                        uploadInfo: UploadInfo,
                        serverResponse: ServerResponse
                    ) {
                        view.onImageUploaded(serverResponse)
                        data()
                    }

                    override fun onError(
                        context: Context,
                        uploadInfo: UploadInfo,
                        exception: Throwable
                    ) {
                        when (exception) {
                            is UserCancelledUploadException -> {
                                Log.e("RECEIVER", "Error, user cancelled upload: $uploadInfo")
//                                view?.showMessage("Error, user cancelled upload: $uploadInfo")
//                                view?.hideLoading()
                            }

                            is UploadError -> {
                                Log.e(
                                    "RECEIVER",
                                    "Error, upload error: ${exception.serverResponse}"
                                )
                                Log.e(
                                    "RECEIVER",
                                    "Error, upload error: ${exception.message}"
                                )
//                                view?.showMessage("Error, upload error: ${exception.serverResponse}")
//                                view?.showMessage("Error, upload error: ${exception.message}")
//                                view?.hideLoading()
                            }

                            else -> {
                                Log.e("RECEIVER", "Error: $uploadInfo", exception)
//                                view?.showMessage("Error: ${exception}")
//                                view?.hideLoading()
                            }
                        }
                    }

                    override fun onCompleted(context: Context, uploadInfo: UploadInfo) {
                    }

                    override fun onCompletedWhileNotObserving() {
                        // do your thing
                    }
                })
    }



    override fun delete(uid: String) {
        val service = ProtectedServiceBuilder.buildService(MediaService::class.java)
        val call = service.removeMedia(uid)
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