package id.co.sherly.mart.ui.supplier

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Supplier
import id.co.sherly.mart.databinding.ItemSupplierBinding


class ItemSupplierAdapter(
    private var categories: MutableList<Supplier>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemSupplierBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
//        view.root.post {
//            view.root.layoutParams.width = parent.width/4
//            view.root.layoutParams.height = parent.width/4
//            view.root.requestLayout()
//        }
        return ItemSupplierViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as ItemSupplierViewHolder).binding
        val supplier = categories[position]
        binding.name.text = supplier.name
        binding.phone.text = supplier.phone?:"-"

        if (supplier.selected) {
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
                categories[lastPos].selected = false
//                binding.iconCheck.visibility = View.GONE

                notifyItemChanged(lastPos) // Memperbarui item sebelumnya
            }

            // Memilih item yang baru di klik
            supplier.selected = !supplier.selected
            notifyItemChanged(position) // Memperbarui item yang dipilih

            // Menyimpan posisi item yang dipilih
            lastPos = position

            callback?.onSupplierSelected(supplier, position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelections(list: List<Supplier>) {
        lastPos = -1
        for (p in list) {
            p.selected = false
        }
        notifyDataSetChanged()
    }

    fun items(): List<Supplier> {
        return categories
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(categories: MutableList<Supplier>) {
        this.categories.clear()
        this.categories.addAll(categories)
        this.notifyDataSetChanged()
    }

    var callback: Callback? = null
    interface Callback {
        fun onSupplierSelected(supplier: Supplier, position: Int)
    }


    inner class ItemSupplierViewHolder(val binding: ItemSupplierBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }
    }
}