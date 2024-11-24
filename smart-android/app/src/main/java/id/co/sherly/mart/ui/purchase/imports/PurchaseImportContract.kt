package id.co.sherly.mart.ui.purchase.imports

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import id.co.sherly.mart.data.model.DocumentImport
import id.co.sherly.mart.data.model.Item
import net.gotev.uploadservice.network.ServerResponse

interface PurchaseImportContract {

    interface View {
        fun provideItems(items: List<Item>)
        fun documentPath(name: String): String
        fun onImageUploaded(serverResponse: ServerResponse)
        fun showData(data: List<DocumentImport>?)
        fun showProgress()
        fun hideProgress()
        fun exported()
        fun successImport()
    }

    interface Presenter {
        fun create(context: Context, path: String, lifeCycleOwner: LifecycleOwner)
        fun import(id: String, supplier: Int)
        fun data()
        fun items()
        fun exportXls()
    }
}