package id.co.sherly.mart.ui.sale.pay

import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import javax.inject.Inject


class SalePayPresenter @Inject constructor(
    private val view: SalePayContract.View
): SalePayContract.Presenter, BasePresenterImpl() {

}