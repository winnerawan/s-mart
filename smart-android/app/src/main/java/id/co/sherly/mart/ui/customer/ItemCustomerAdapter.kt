package id.co.sherly.mart.ui.customer

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Customer
import id.co.sherly.mart.databinding.ItemCustomerBinding


class ItemCustomerAdapter(
    private var categories: MutableList<Customer>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemCustomerBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
//        view.root.post {
//            view.root.layoutParams.width = parent.width/4
//            view.root.layoutParams.height = parent.width/4
//            view.root.requestLayout()
//        }
        return ItemCustomerViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as ItemCustomerViewHolder).binding
        val category = categories[position]
        binding.name.text = category.name
        binding.saleCount.text = holder.itemView.context.getString(R.string.sale_format, "0")

        if (category.selected) {
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
            category.selected = !category.selected
            notifyItemChanged(position) // Memperbarui item yang dipilih

            // Menyimpan posisi item yang dipilih
            lastPos = position

            callback?.onCustomerSelected(category, position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelections(list: List<Customer>) {
        lastPos = -1
        for (p in list) {
            p.selected = false
        }
        notifyDataSetChanged()
    }

    fun items(): List<Customer> {
        return categories
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(categories: MutableList<Customer>) {
        this.categories.clear()
        this.categories.addAll(categories)
        this.notifyDataSetChanged()
    }

    var callback: Callback? = null
    interface Callback {
        fun onCustomerSelected(category: Customer, position: Int)
    }


    inner class ItemCustomerViewHolder(val binding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }
    }
}