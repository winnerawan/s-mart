package id.co.sherly.mart.ui.purchase

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Purchase
import id.co.sherly.mart.databinding.ItemPurchaseBinding


class ItemPurchaseAdapter(
    private var purchases: MutableList<Purchase>,
//    var divWidth: Int,
//    var divHeight: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemPurchaseBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
//        view.root.post {
//            view.root.layoutParams.width = parent.width/divWidth
//            view.root.layoutParams.height = parent.height/divHeight
//            view.root.requestLayout()
//        }
        return ItemPurchaseViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = purchases.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as ItemPurchaseViewHolder).binding
        val purchase = purchases[position]
        binding.name.text = purchase.description
        binding.stockCount.text = holder.itemView.context.getString(R.string.item_format, "${purchase.item}")

        if (purchase.selected) {
            binding.iconCheck.visibility = View.VISIBLE
        }
//        binding.iconCheck.setBackgroundColor(if (lastPos == position) Color.GREEN else Color.TRANSPARENT)

        if (lastPos == position) {
            binding.iconCheck.visibility = View.VISIBLE
        } else {
            binding.iconCheck.visibility = View.GONE
        }
        // Klik item untuk memilih produk
        binding.root.setOnClickListener {
            if (lastPos != -1 && lastPos != position) {
                // Menyembunyikan iconCheck dari item sebelumnya
                purchases[lastPos].selected = false
//                binding.iconCheck.visibility = View.GONE

                notifyItemChanged(lastPos) // Memperbarui item sebelumnya
            }

            // Memilih item yang baru di klik
            purchase.selected = !purchase.selected
            notifyItemChanged(position) // Memperbarui item yang dipilih

            // Menyimpan posisi item yang dipilih
            lastPos = position

            callback?.onPurchaseSelected(purchase, position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelections(list: List<Purchase>) {
        lastPos = -1
        for (p in list) {
            p.selected = false
        }
        notifyDataSetChanged()
    }

    fun items(): List<Purchase> {
        return purchases
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(purchases: MutableList<Purchase>) {
        this.purchases.clear()
        this.purchases.addAll(purchases)
        this.notifyDataSetChanged()
    }

    var callback: Callback? = null
    interface Callback {
        fun onPurchaseSelected(purchase: Purchase, position: Int)
    }


    inner class ItemPurchaseViewHolder(val binding: ItemPurchaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }
    }
}