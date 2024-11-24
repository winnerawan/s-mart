package id.co.sherly.mart.ui.sale

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.ItemStock
import id.co.sherly.mart.databinding.ItemViewSaleBinding
import id.co.sherly.mart.utils.ext.formatPrice
import id.co.sherly.mart.utils.ext.loadImage


class ItemViewSaleAdapter(
    private var items: MutableList<ItemStock>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemViewSaleBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
//        view.root.post {
//            view.root.layoutParams.width = parent.width/4
//            view.root.layoutParams.height = parent.width/4
//            view.root.requestLayout()
//        }
        return ItemSaleViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as ItemSaleViewHolder).binding
        val item = items[position]
        binding.name.text = item.name
        binding.button.quantity.text = item.quantity.toString()

//        binding.description.text = item.description
        if (item.sellingPrice!=null) {
            binding.sellingPrice.text = item.sellingPrice?.formatPrice()
        } else {
            binding.sellingPrice.text = "0".formatPrice()
        }
        if (item.image==null) {
            binding.icon.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.placeholder_image))
        } else {
            binding.icon.loadImage(item.image)
        }

        if (item.selected) {
//            binding.iconCheck.visibility = View.VISIBLE
        }
//        binding.iconCheck.setBackgroundColor(if (lastPos == position) Color.GREEN else Color.TRANSPARENT)

        if (lastPos == position) {
//            binding.iconCheck.visibility = View.VISIBLE
        } else {
//            binding.iconCheck.visibility = View.GONE
        }
//        // Klik item untuk memilih produk
//        binding.root.setOnClickListener {
//            if (lastPos != -1 && lastPos != position) {
//                // Menyembunyikan iconCheck dari item sebelumnya
//                items[lastPos].selected = false
////                binding.iconCheck.visibility = View.GONE
//
//                notifyItemChanged(lastPos) // Memperbarui item sebelumnya
//            }
//
//            // Memilih item yang baru di klik
//            item.selected = !item.selected
//            notifyItemChanged(position) // Memperbarui item yang dipilih
//
//            // Menyimpan posisi item yang dipilih
//            lastPos = position
//
//            callback?.onItemSelected(item, position)
//        }

        binding.button.buttonMinus.setOnClickListener {
            callback?.onDecreaseQuantity(item, binding.button.quantity, position)
        }
        binding.button.buttonPlus.setOnClickListener {
            if (binding.button.quantity.text.toString().toInt()>=item.stock!!) {

            } else {
                callback?.onInCreaseQuantity(item, binding.button.quantity, position)
            }
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
        temp = this.items
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }

    fun selectItemBySku(sku: String) {
        this.items = temp.toMutableList()
        val filtered = this.items.filter {
            it.sku == sku
        }
        this.items = filtered.toMutableList()
        notifyItemRangeChanged(0, filtered.size)
    }

    fun resetFilter() {
        this.items = temp.toMutableList()
        notifyItemRangeChanged(0, this.items.size)
    }

    var temp: List<ItemStock> = listOf()
    fun filterByCategory(category: Category) {
        this.items = temp.toMutableList()
        val filtered = this.items.filter {
            it.categoryId == category.id
        }
        this.items = filtered.toMutableList()
        notifyItemRangeChanged(0, filtered.size)
    }

    fun filterByNameSku(query: String) {
        this.items = temp.toMutableList()
        val filtered = this.items.filter {
            it.name?.lowercase()?.contains(query) == true || it.sku?.lowercase()?.contains(query) == true
        }
        this.items = filtered.toMutableList()
        Log.e("filterByNameSku", "${filtered}")
        Log.e("size", "${filtered.size}")
        notifyItemRangeRemoved(0, this.items.size)
        notifyItemRangeChanged(0, filtered.size)
//        if (filtered.isNotEmpty() && addToCart) {
//            callback?.onFilteredAddToCart(filtered[0])
//        }
    }

    fun filterBySku(sku: String) {
        this.items = temp.toMutableList()
        val filtered = this.items.filter {
            it.sku?.trim()?.lowercase()?.equals(sku.trim()) == true
        }
        this.items = filtered.toMutableList()
        Log.e("filterBySku", "sku= ${sku}")
        Log.e("filterBySku", "${items}")
        Log.e("size", "${filtered.size}")
        notifyItemRangeRemoved(0, this.items.size)
        notifyItemRangeChanged(0, filtered.size)
        if (filtered.size==1) {
            callback?.onFilteredAddToCart(filtered[0])
        }
    }

    var callback: Callback? = null
    interface Callback {
        fun onItemSelected(item: ItemStock, position: Int)
        fun onDecreaseQuantity(item: ItemStock, textView: AppCompatTextView, position: Int)
        fun onInCreaseQuantity(item: ItemStock, textView: AppCompatTextView, position: Int)
        fun onFilteredAddToCart(item: ItemStock)
    }


    inner class ItemSaleViewHolder(val binding: ItemViewSaleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }
    }
}