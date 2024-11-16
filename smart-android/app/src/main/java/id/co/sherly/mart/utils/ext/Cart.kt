package id.co.sherly.mart.utils.ext

import androidx.appcompat.widget.AppCompatTextView

object Cart {

    fun increase(quantity: Int=0, txtQuantity: AppCompatTextView, callback: () -> Unit) {
        if (quantity>=0) {
            txtQuantity.text = "${quantity + quantity}"
            callback()
        }
    }
}