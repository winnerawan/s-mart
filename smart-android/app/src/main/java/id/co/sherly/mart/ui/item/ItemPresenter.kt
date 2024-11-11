package id.co.sherly.mart.ui.item

import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import javax.inject.Inject

class ItemPresenter @Inject constructor(
    private val view: ItemContract.View
): ItemContract.Presenter, BasePresenterImpl() {


}