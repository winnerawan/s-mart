package id.co.sherly.mart.ui.base.view

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import id.co.sherly.mart.utils.ext.showLoadingDialog

abstract class BaseFragment<V : ViewBinding> : Fragment(), BaseView {
    protected var binding: V? = null
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = onCreateBinding(inflater, container, savedInstanceState)
        this.binding = binding
        return binding.root
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    abstract fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): V

    override fun showLoading() {
        hideLoading()
        mProgressDialog = showLoadingDialog(activity)
    }

    override fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog?.isShowing ==true) {
            mProgressDialog?.cancel()
        }
    }

    override fun showMessage(message: String?) {
        message?.let {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showMessage(resId: Int) {
        showMessage(getString(resId))
    }
}