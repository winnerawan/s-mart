package id.co.sherly.mart.ui.item

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.databinding.ItemItemBinding
import id.co.sherly.mart.utils.ext.formatPrice
import id.co.sherly.mart.utils.ext.loadImage


class ItemAdapter(
    private var items: MutableList<Item>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
//        view.root.post {
//            view.root.layoutParams.width = parent.width/4
//            view.root.layoutParams.height = parent.width/4
//            view.root.requestLayout()
//        }
        return ItemItemViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as ItemItemViewHolder).binding
        val item = items[position]
        binding.name.text = item.name
        binding.description.text = item.description
        if (item.lastPurchasePrice!=null) {
            binding.lastPurchasePrice.text = item.lastPurchasePrice?.formatPrice()
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
            binding.barcodeView.setImageBitmap(bitmap)
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
    fun clearSelections(list: List<Item>) {
        lastPos = -1
        for (p in list) {
            p.selected = false
        }
        notifyDataSetChanged()
    }

    fun items(): List<Item> {
        return items
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: MutableList<Item>) {
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }

    var callback: Callback? = null
    interface Callback {
        fun onItemSelected(item: Item, position: Int)
    }


    inner class ItemItemViewHolder(val binding: ItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }
    }
}