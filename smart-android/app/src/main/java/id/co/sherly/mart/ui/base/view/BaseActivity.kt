package id.co.sherly.mart.ui.base.view

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import id.co.sherly.mart.utils.ext.showLoadingDialog

abstract class BaseActivity<V : ViewBinding> : AppCompatActivity(), BaseView {

    protected lateinit var binding: V
    private var mProgressDialog: ProgressDialog? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = createBinding()
        this.binding = binding
        setContentView(binding.root)
    }

    abstract fun createBinding(): V

    override fun showLoading() {
        hideLoading()
        mProgressDialog = showLoadingDialog(this)
    }

    override fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog?.isShowing ==true) {
            mProgressDialog?.cancel()
        }
    }

    override fun showMessage(message: String?) {
        message?.let {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showMessage(resId: Int) {
        showMessage(getString(resId))
    }
}