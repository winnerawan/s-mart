/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.base.presenter

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.SerialDisposable

open class BasePresenterImpl : BasePresenter, ViewModel() {

    //@NonNull
    //var disposable: Disposable? = null
    //var disposable by lazy { CompositeDisposable() }
    val disposable by lazy { SerialDisposable() }
    @SuppressLint("StaticFieldLeak")
    private var viewLifecycle: Lifecycle? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onAttachView(view: View, viewLifecycle: Lifecycle?) {
//        this.view = view
        this.viewLifecycle = viewLifecycle
        viewLifecycle?.addObserver(this)
    }

    override fun onStop() {
        disposable.dispose()
    }
}