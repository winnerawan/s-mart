package id.co.sherly.mart.utils.ext

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.orhanobut.logger.Logger
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Image
import java.io.Serializable
import java.text.NumberFormat
import java.util.Currency
import java.util.Date
import java.util.Locale


/**
 * A url, which can also have headers
 * **/
data class FileUrl(
    val url: String,
    val headers: Map<String, String> = mapOf()
) : Serializable {
    companion object {
        operator fun get(url: String?, headers: Map<String, String> = mapOf()): FileUrl? {
            return FileUrl(url ?: return null, headers)
        }
    }
}

fun <T> tryWith(call: () -> T): T? {
    return try {
        call.invoke()
    } catch (e: Exception) {
        logError(e)
        null
    }
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableExtra(
        key,
        T::class.java
    )

    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(
        key,
        T::class.java
    )

    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}

fun Context?.getParentActivity(): AppCompatActivity? = when (this) {
    is ContextWrapper -> if (this is AppCompatActivity) this else this.baseContext.getParentActivity()
    else -> null
}

fun logMessage(message: String) {
    Logger.w(message)
}

fun logError(t: Throwable?) {
    Logger.e("t: $t")
}
fun logError(e: Exception?) {
    Logger.e("e: $e")
}

fun showLoadingDialog(context: Context?): ProgressDialog? {
    val progressDialog = ProgressDialog(context)
    progressDialog.show()
    if (progressDialog.window != null) {
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    progressDialog.setContentView(R.layout._progress_bar)
    progressDialog.setIndeterminate(true)
    progressDialog.setCancelable(false)
    progressDialog.setCanceledOnTouchOutside(false)
    return progressDialog
}

@SuppressLint("SimpleDateFormat")
fun String.formatDate(): String {
    val outputFormat: DateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.US)
    val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale("id", "ID"))
    val date: Date = inputFormat.parse(this)
    return try {
        outputFormat.format(date)
    } catch (e: Exception) {
        Log.e("LOG", "error format: $e")
        this
    }
}

@SuppressLint("SimpleDateFormat")
fun String.formatDatetime(): String {
    val outputFormat: DateFormat = SimpleDateFormat("dd MMM, yyyy hh:mm a", Locale.US)
    val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale("id", "ID"))
    val date: Date = inputFormat.parse(this)
    return try {
        outputFormat.format(date)
    } catch (e: Exception) {
        Log.e("LOG", "error format: $e")
        this
    }
}

fun Double.priceFormat(currency: String): String {
    return (currency + this).replace("(\\d)(?=(\\d{3})+(?!\\d))".toRegex(), "$1.")
}

fun String.formatPrice(): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 2
    format.currency = Currency.getInstance("IDR")
//    val symbol = format.currency?.symbol

    return format.format(this.toDouble()).replace("IDR", "Rp ")
}

fun ImageView.loadBlogImage(image: Image?) {
//    val rw = image?.width?.div(_w)
//    val w = rw?.let { image.width?.times(it) }
//
//    val rh = image?.height?.div(_h)
//    val h = rh?.let { image.height?.times(it) }

    if (image!=null) {
        val url = "${image.url}&w=${image.width}&h=${image.height}"
        loadImage(url)
    }
}
fun ImageView.loadImage(image: Image?) {
    if (image!=null) {
        val url = "${image.url}&w=${image.width}&h=${image.height}"
        loadImage(url)
    }
}

fun ImageView.loadImage(url: String?, size: Int = 0) {
    if (!url.isNullOrEmpty()) {
        loadImage(FileUrl(url), size)
    }
}

fun ImageView.loadImage(file: FileUrl?, size: Int = 0) {
    if (file?.url?.isNotEmpty() == true) {
        tryWith {
            val glideUrl = GlideUrl(file.url) { file.headers }
//            logMessage(glideUrl.toStringUrl())
            Glide.with(this.context).load(glideUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .transition(DrawableTransitionOptions.withCrossFade()).override(size).into(this)
        }
    }
}


fun setStatusBarTransparent(window: Window, bool: Boolean) {
    if (Build.VERSION.SDK_INT>=30) {
        setDecorFitSystemWindows(window, bool)
    } else {
        setDecorFitSystemWindowsOldApi(window, bool)
    }
}

fun setDecorFitSystemWindowsOldApi(window: Window, bool: Boolean) {
    val decorView = window.decorView
    decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}

@RequiresApi(Build.VERSION_CODES.R)
fun setDecorFitSystemWindows(window: Window, bool: Boolean) {
    window.setDecorFitsSystemWindows(bool)
}

//fun ImageView.loadImageCircle(file: FileUrl?, size: Int = 0) {
//    if (file?.url?.isNotEmpty() == true) {
//        tryWith {
//            val glideUrl = GlideUrl(file.url) { file.headers }
//            Glide.with(this.context).load(glideUrl)
//                .placeholder(R.drawable.placeholder_avatar)
//                .error(R.drawable.placeholder_avatar)
//                .apply(RequestOptions.bitmapTransform(CircleCrop()))
//                .transition(DrawableTransitionOptions.withCrossFade()).override(size).into(this)
//        }
//    }
//}