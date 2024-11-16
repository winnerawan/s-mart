package id.co.sherly.mart.ui.media

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import id.co.sherly.mart.data.model.Media
import net.gotev.uploadservice.network.ServerResponse

interface MediaContract {

    interface View {
        fun showData(medias: List<Media>)
        fun showProgress()
        fun hideProgress()
        fun onImageUploaded(serverResponse: ServerResponse)
    }

    interface Presenter {
        fun data()
        fun create(context: Context, path: String, lifeCycleOwner: LifecycleOwner)
        fun delete(uid: String)
    }
}