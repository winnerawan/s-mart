package id.co.sherly.mart.ui.sale

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.ItemStock
import id.co.sherly.mart.databinding.ItemCartPurchaseBinding
import id.co.sherly.mart.databinding.ItemCartSaleBinding
import id.co.sherly.mart.utils.ext.calculateSubtotal
import id.co.sherly.mart.utils.ext.formatPrice
import id.co.sherly.mart.utils.ext.loadImage
import java.math.BigDecimal


class ItemCartSaleAdapter(
    private var items: MutableList<ItemStock>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemCartSaleBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
//        view.root.post {
//            view.root.layoutParams.width = parent.width/4
//            view.root.layoutParams.height = parent.width/4
//            view.root.requestLayout()
//        }
        return ItemCartSaleViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as ItemCartSaleViewHolder).binding
        val item = items[position]
        binding.name.text = item.name


        binding.quantity.text = "${item.quantity} x ${item.sellingPrice?.formatPrice()}"
        binding.subtotal.text = item.getSubtotal().toString().formatPrice()
//        binding.description.text = item.description

        if (item.image == null) {
            binding.icon.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.placeholder_image
                )
            )
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

//            callback?.onItemSelected(item, position)
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

    fun addItem(item: ItemStock) {
        this.items.add(item)
        notifyItemInserted(this.items.size)
        calculatePrice()
    }

    /*
    * 0, 1, 2, 3 jika hapus posisi 0 / first item => bisa update semua posisi -1
    * 0, 1, 2, 3 jika hapus posisi 3 / last item => tidak perlu update posisi
    * 0, 1, 2, 3 jika hapus posisi 2 => update posisi -1 setelah posisi yg dihapus
    */
    fun removeItem(item: ItemStock, position: Int) {
        checkPositions()
        if (position != 0 && position != items.size) {
            updateNextItemPosition(position)
        }
        this.items.remove(item)
        notifyItemRemoved(position)
        if (position == 0) {
            updateAllPosition()
        }
        if (position == items.size) {

        }
        calculatePrice()

    }

    private fun checkPositions() {
        for (item in items) {
            Log.e("---", "${item.name} position = ${item.recyclerViewPosition}")
        }
    }

    private fun updateNextItemPosition(position: Int) {
        val data = items.slice(position + 1 until items.size)
        for (item in data) {
            val oldPosition = item.recyclerViewPosition
            val newPosition = oldPosition - 1
            Log.e("===", "${item.name} old position = $oldPosition  new position = $newPosition")

            item.recyclerViewPosition = newPosition
            updateItem(item, item.recyclerViewPosition)
            callback?.onUpdatePosition(item, newPosition)
        }
    }

    private fun updateItemPosition(item: ItemStock, removedPosition: Int) {
        if (item.recyclerViewPosition > removedPosition) {
            val newPosition = removedPosition - 1
            item.recyclerViewPosition = newPosition
            updateItem(item, item.recyclerViewPosition)
            callback?.onUpdatePosition(item, newPosition)
        }
    }

    private fun updateAllPosition() {
        for (item in items) {
            val position: Int = item.recyclerViewPosition
            val newPosition = position - 1
            Log.e("LOG", "${item.name} old position = $position   -    new position = $newPosition")
            item.recyclerViewPosition = newPosition
            updateItem(item, newPosition)
            callback?.onUpdatePosition(item, newPosition)
        }
    }

    //    if (item.recyclerViewPosition!=0) {
//        item.recyclerViewPosition -= 1
//        callback?.onUpdatePosition(item, item.recyclerViewPosition)
//        updateItem(item, item.recyclerViewPosition)
//    }
    fun updateItem(item: ItemStock, position: Int) {
        item.recyclerViewPosition = position
        notifyItemChanged(position)
        calculatePrice()
    }

    fun calculatePrice() {
        var total: BigDecimal? = BigDecimal(0)
        if (items.size==0) {
            total = BigDecimal(0)
            callback?.onPriceCalculated(
                total,
                total?.toString()?.formatPrice(),
                total?.toString()?.formatPrice()
            )
            return
        }
        total = items
            .stream()
            .map(ItemStock::getSubtotal)
            .reduce(BigDecimal::add)
            .get()
//        total = this.items
//            .groupBy { it.id }
//            .mapValues { it.value.sumOf { it.tmpPrice * it.quantity } }
//        for (item in items) {
//            total = total?.plus(
//                (item.tmpPrice?.toBigDecimal()?.calculateSubtotal(item.quantity)!!)
//            )
//            Log.e("cal", "${item.name} - $total")
//        }
        callback?.onPriceCalculated(
            total,
            total?.toString()?.formatPrice(),
            total?.toString()?.formatPrice()
        )
    }


    var callback: Callback? = null

    interface Callback {
        //        fun onItemSelected(item: ItemStock, position: Int)
//        fun onDecreaseQuantity(item: ItemStock, textView: AppCompatTextView, position: Int)
//        fun onInCreaseQuantity(item: ItemStock, textView: AppCompatTextView, position: Int)
        fun onUpdatePosition(item: ItemStock, position: Int)
        fun onPriceCalculated(t: BigDecimal, subtotal: String?, total: String?)
    }


    inner class ItemCartSaleViewHolder(val binding: ItemCartSaleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }
    }
}