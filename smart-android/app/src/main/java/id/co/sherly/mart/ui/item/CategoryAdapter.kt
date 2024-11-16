package id.co.sherly.mart.ui.item

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.databinding.ItemCategoryBinding
import id.co.sherly.mart.databinding.ItemCategoryBlueBinding


class CategoryAdapter(
    private var categories: MutableList<Category>,
//    var divWidth: Int,
//    var divHeight: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemCategoryBlueBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        view.root.post {
            view.root.layoutParams.width = parent.width/4
            view.root.layoutParams.height = parent.height/1
            view.root.requestLayout()
        }
        return ItemCategoryBlueViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as ItemCategoryBlueViewHolder).binding
        val category = categories[position]
        binding.name.text = category.name
        binding.stockCount.text = holder.itemView.context.getString(R.string.item_format, "${category.item}")

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

            callback?.onCategorySelected(category, position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelections(list: List<Category>) {
        lastPos = -1
        for (p in list) {
            p.selected = false
        }
        notifyDataSetChanged()
    }

    fun items(): List<Category> {
        return categories
    }

    fun getItem(position: Int): Category {
        return categories[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(categories: MutableList<Category>) {
        this.categories.clear()
        this.categories.addAll(categories)
        this.notifyDataSetChanged()
    }

    var callback: Callback? = null
    interface Callback {
        fun onCategorySelected(category: Category, position: Int)
    }


    inner class ItemCategoryBlueViewHolder(val binding: ItemCategoryBlueBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }
    }
}