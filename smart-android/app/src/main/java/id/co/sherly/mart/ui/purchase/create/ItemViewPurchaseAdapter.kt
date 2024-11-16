package id.co.sherly.mart.ui.purchase.create

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.databinding.ItemViewPurchaseBinding
import id.co.sherly.mart.utils.ext.formatPrice
import id.co.sherly.mart.utils.ext.loadImage


class ItemViewPurchaseAdapter(
    private var items: MutableList<Item>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemViewPurchaseBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
//        view.root.post {
//            view.root.layoutParams.width = parent.width/4
//            view.root.layoutParams.height = parent.width/4
//            view.root.requestLayout()
//        }
        return ItemViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as ItemViewHolder).binding
        val item = items[position]
        binding.name.text = item.name
        binding.button.quantity.text = item.quantity.toString()
        binding.description.text = item.sku
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
            callback?.onInCreaseQuantity(item, binding.button.quantity, position)
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

    var temp: List<Item> = listOf()
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
        fun onItemSelected(item: Item, position: Int)
        fun onDecreaseQuantity(item: Item, textView: AppCompatTextView, position: Int)
        fun onInCreaseQuantity(item: Item, textView: AppCompatTextView, position: Int)
        fun onFilteredAddToCart(item: Item)
    }


    inner class ItemViewHolder(val binding: ItemViewPurchaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }
    }
}