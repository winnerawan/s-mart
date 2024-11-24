package id.co.sherly.mart.ui.purchase.imports

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import id.co.sherly.mart.BuildConfig
import id.co.sherly.mart.data.model.DocumentImport
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.data.model.Purchase
import id.co.sherly.mart.data.model.response.SuccessResponse
import id.co.sherly.mart.network.ProtectedServiceBuilder
import id.co.sherly.mart.network.service.ExportService
import id.co.sherly.mart.network.service.ImportService
import id.co.sherly.mart.network.service.ItemService
import id.co.sherly.mart.network.service.PurchaseService
import id.co.sherly.mart.ui.account.auth.Auth
import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import net.gotev.uploadservice.data.UploadInfo
import net.gotev.uploadservice.exceptions.UploadError
import net.gotev.uploadservice.exceptions.UserCancelledUploadException
import net.gotev.uploadservice.network.ServerResponse
import net.gotev.uploadservice.observer.request.RequestObserverDelegate
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class PurchaseImportPresenter @Inject constructor(
    private val view: PurchaseImportContract.View
): PurchaseImportContract.Presenter, BasePresenterImpl() {


    override fun items() {
        val service = ProtectedServiceBuilder.buildService(ItemService::class.java)
        val call = service.data()
        call.enqueue(object : Callback<List<Item>?> {
            override fun onResponse(call: Call<List<Item>?>, response: Response<List<Item>?>) {
                response.body()?.let {
                    view.provideItems(it)
                }
            }

            override fun onFailure(call: Call<List<Item>?>, t: Throwable) {
            }


        })
    }

    override fun create(context: Context, path: String, lifeCycleOwner: LifecycleOwner) {
        MultipartUploadRequest(context = context, "${BuildConfig.API_URL}import/upload")
            .addFileToUpload(filePath = path, parameterName = "file")
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
                                view.hideProgress()
                            }

                            is UploadError -> {
                                Log.e(
                                    "RECEIVER1",
                                    "Error, upload error: ${exception.serverResponse.bodyString}"
                                )
                                Log.e(
                                    "RECEIVER2",
                                    "Error, upload error: ${exception.message}"
                                )
//                                view?.showMessage("Error, upload error: ${exception.serverResponse}")
//                                view?.showMessage("Error, upload error: ${exception.message}")
                                view.hideProgress()
                            }

                            else -> {
                                Log.e("RECEIVER3", "Error: $uploadInfo", exception)
//                                view?.showMessage("Error: ${exception}")
                                view.hideProgress()
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

    override fun exportXls() {
        val service = ProtectedServiceBuilder.buildService(ExportService::class.java)
        val call = service.export()
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                response.body()?.let {
                    it.byteStream().saveToFile(
                        "export-${System.currentTimeMillis()}.xls"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
            }
        })

    }

    override fun data() {
        view.showProgress()
        val service = ProtectedServiceBuilder.buildService(ImportService::class.java)
        val call = service.data()
        call.enqueue(object : Callback<List<DocumentImport>?> {
            override fun onResponse(call: Call<List<DocumentImport>?>, response: Response<List<DocumentImport>?>) {
                response.body()?.let {
                    view.showData(it)
                }
            }

            override fun onFailure(call: Call<List<DocumentImport>?>, t: Throwable) {
            }

        })
    }

    private fun InputStream.saveToFile(name: String) = use { input ->
        File(view.documentPath(name)).outputStream().use { output ->
            input.copyTo(output)
        }
        view.exported()
    }

    override fun import(id: String, supplier: Int) {
        view.showProgress()
        val service = ProtectedServiceBuilder.buildService(ImportService::class.java)
        val call = service.import(id, supplier)
        call.enqueue(object : Callback<SuccessResponse> {
            override fun onResponse(
                call: Call<SuccessResponse>,
                response: Response<SuccessResponse>
            ) {
                response.body()?.let {
                    view.hideProgress()
                    view.successImport()
                }
            }

            override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                view.hideProgress()
                data()
            }

        })
    }
}