package id.co.sherly.mart.ui.purchase.create

import android.annotation.SuppressLint
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.databinding.ItemCartPurchaseBinding
import id.co.sherly.mart.utils.ext.calculateSubtotal
import id.co.sherly.mart.utils.ext.formatPrice
import id.co.sherly.mart.utils.ext.loadImage


class ItemCartPurchaseAdapter(
    private var items: MutableList<Item>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemCartPurchaseBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
//        view.root.post {
//            view.root.layoutParams.width = parent.width/4
//            view.root.layoutParams.height = parent.width/4
//            view.root.requestLayout()
//        }
        return ItemCartPurchaseViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as ItemCartPurchaseViewHolder).binding
        val item = items[position]
        binding.name.text = item.name

        binding.quantity.text = "${item.quantity} x ${item.lastPurchasePrice?.formatPrice()}"
        binding.subtotal.text = (item.lastPurchasePrice?.toBigDecimal()?.calculateSubtotal(item.quantity)).toString().formatPrice()
//        binding.description.text = item.description
        if (item.lastPurchasePrice != null) {
            binding.lastPurchasePrice.text = item.lastPurchasePrice?.formatPrice()
        } else {
            binding.lastPurchasePrice.text = "0".formatPrice()
        }
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

        binding.button.buttonMinus.setOnClickListener {
//            callback?.onDecreaseQuantity(item, binding.button.quantity, position)
        }
        binding.button.buttonPlus.setOnClickListener {
//            callback?.onInCreaseQuantity(item, binding.button.quantity, position)
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

    fun addItem(item: Item) {
        this.items.add(item)
        notifyItemInserted(this.items.size)
    }

    /*
    * 0, 1, 2, 3 jika hapus posisi 0 / first item => bisa update semua posisi -1
    * 0, 1, 2, 3 jika hapus posisi 3 / last item => tidak perlu update posisi
    * 0, 1, 2, 3 jika hapus posisi 2 => update posisi -1 setelah posisi yg dihapus
    */
    fun removeItem(item: Item, position: Int) {
        checkPositions()
        if (position!=0 && position!=items.size) {
            updateNextItemPosition(position)
        }
        this.items.remove(item)
        notifyItemRemoved(position)
        if (position==0){
            updateAllPosition()
        }
        if (position==items.size) {

        }


    }

    private fun checkPositions() {
        for (item in items) {
            Log.e("---", "${item.name} position = ${item.recyclerViewPosition}")
        }
    }

    private fun updateNextItemPosition(position: Int) {
        val data = items.slice(position+1 until items.size)
        for (item in data) {
            val oldPosition = item.recyclerViewPosition
            val newPosition = oldPosition-1
            Log.e("===", "${item.name} old position = $oldPosition  new position = $newPosition")

            item.recyclerViewPosition = newPosition
            updateItem(item, item.recyclerViewPosition)
            callback?.onUpdatePosition(item, newPosition)
        }
    }

    private fun updateItemPosition(item: Item, removedPosition: Int) {
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
    fun updateItem(item: Item, position: Int) {
        item.recyclerViewPosition = position
        notifyItemChanged(position)
    }

    var callback: Callback? = null

    interface Callback {
        //        fun onItemSelected(item: Item, position: Int)
//        fun onDecreaseQuantity(item: Item, textView: AppCompatTextView, position: Int)
//        fun onInCreaseQuantity(item: Item, textView: AppCompatTextView, position: Int)
        fun onUpdatePosition(item: Item, position: Int)
    }


    inner class ItemCartPurchaseViewHolder(val binding: ItemCartPurchaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }
    }
}