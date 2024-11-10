package id.co.sherly.mart.ui.history

import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import javax.inject.Inject

class HistoryPresenter @Inject constructor(
    private val view: HistoryContract.View,
): HistoryContract.Presenter, BasePresenterImpl() {


}