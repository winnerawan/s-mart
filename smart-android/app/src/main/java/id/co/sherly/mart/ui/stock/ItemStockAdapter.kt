package id.co.sherly.mart.ui.stock

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.ItemStock
import id.co.sherly.mart.databinding.ItemItemBinding
import id.co.sherly.mart.databinding.ItemStockBinding
import id.co.sherly.mart.utils.ext.formatPrice
import id.co.sherly.mart.utils.ext.loadImage


class ItemStockAdapter(
    private var items: MutableList<ItemStock>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemStockBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
//        view.root.post {
//            view.root.layoutParams.width = parent.width/4
//            view.root.layoutParams.height = parent.width/4
//            view.root.requestLayout()
//        }
        return ItemStockViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as ItemStockViewHolder).binding
        val item = items[position]
        binding.name.text = item.name
        binding.stock.text = "${item.stock}"
        if (item.sellingPrice!=null) {
            binding.lastPurchasePrice.text = item.sellingPrice?.formatPrice()
        } else {
            binding.lastPurchasePrice.text = "0".formatPrice()
        }
        if (item.image==null) {
            binding.icon.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.placeholder_image))
        } else {
            binding.icon.loadImage(item.image)
        }
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.encodeBitmap(item.sku, BarcodeFormat.CODE_128, 72, 24)
            binding.barcode.setImageBitmap(bitmap)
        } catch (e: Exception) {
        }

        if (item.selected) {
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
                items[lastPos].selected = false
//                binding.iconCheck.visibility = View.GONE

                notifyItemChanged(lastPos) // Memperbarui item sebelumnya
            }

            // Memilih item yang baru di klik
            item.selected = !item.selected
            notifyItemChanged(position) // Memperbarui item yang dipilih

            // Menyimpan posisi item yang dipilih
            lastPos = position

            callback?.onItemSelected(item, position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelections(list: List<ItemStock>) {
        lastPos = -1
        for (p in list) {
            p.selected = false
        }
        notifyDataSetChanged()
    }

    fun items(): List<ItemStock> {
        return items
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: MutableList<ItemStock>) {
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }

    var callback: Callback? = null
    interface Callback {
        fun onItemSelected(item: ItemStock, position: Int)
    }

    inner class ItemStockViewHolder(val binding: ItemStockBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }
    }
}