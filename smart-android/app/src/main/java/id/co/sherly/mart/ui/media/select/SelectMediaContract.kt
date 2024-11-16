package id.co.sherly.mart.ui.media.select

import id.co.sherly.mart.data.model.Media

interface SelectMediaContract {

    interface View {
        fun showData(medias: List<Media>)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun data()
    }
}