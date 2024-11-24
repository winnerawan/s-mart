package id.co.sherly.mart.ui.sale

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.PayType
import id.co.sherly.mart.databinding.ItemCategoryBinding
import id.co.sherly.mart.databinding.ItemPayTypeBinding


class ItemPayTypeAdapter(
    private var payTypes: MutableList<PayType>,
//    var divWidth: Int,
//    var divHeight: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemPayTypeBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
//        view.root.post {
//            view.root.layoutParams.width = parent.width/divWidth
//            view.root.layoutParams.height = parent.height/divHeight
//            view.root.requestLayout()
//        }
        return ItemPayTypeViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = payTypes.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as ItemPayTypeViewHolder).binding
        val payType = payTypes[position]
        binding.name.text = payType.name

        when(payType.id) {
            1 -> {
                binding.icon.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_money))
            }
            2 -> {
                binding.icon.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_qr_code))
            }
            3 -> {
                binding.icon.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_debit_card))
            }
        }

        if (payType.selected) {
            binding.relative.setBackgroundDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.shape_rect_paytype_active))
        } else {
            binding.relative.setBackgroundDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.shape_rect_paytype_inactive))

        }
//        binding.iconCheck.setBackgroundColor(if (lastPos == position) Color.GREEN else Color.TRANSPARENT)

        if (lastPos == position) {
//            binding.iconCheck.visibility = View.VISIBLE
//            binding.relative.setBackgroundDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.shape_rect_paytype_active))
        } else {
//            binding.iconCheck.visibility = View.GONE
//            binding.relative.setBackgroundDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.shape_rect_paytype_inactive))

        }
        // Klik item untuk memilih produk
        binding.root.setOnClickListener {
            clearSelections(payTypes)
            if (lastPos != -1 && lastPos != position) {
                // Menyembunyikan iconCheck dari item sebelumnya
                payTypes[lastPos].selected = false
//                binding.iconCheck.visibility = View.GONE

                notifyItemChanged(lastPos) // Memperbarui item sebelumnya
            }

            // Memilih item yang baru di klik
            payType.selected = !payType.selected
            notifyItemChanged(position) // Memperbarui item yang dipilih

            // Menyimpan posisi item yang dipilih
            lastPos = position

            callback?.onCategorySelected(payType, position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelections(list: List<PayType>) {
        lastPos = -1
        for (p in list) {
            p.selected = false
        }
        notifyDataSetChanged()
    }

    fun items(): List<PayType> {
        return payTypes
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(payTypes: MutableList<PayType>) {
        this.payTypes.clear()
        payTypes[0].selected=true
        this.payTypes.addAll(payTypes)
        this.payTypes[0].selected = true
        this.notifyDataSetChanged()
    }

    var callback: Callback? = null
    interface Callback {
        fun onCategorySelected(payType: PayType, position: Int)
    }


    inner class ItemPayTypeViewHolder(val binding: ItemPayTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }
    }
}