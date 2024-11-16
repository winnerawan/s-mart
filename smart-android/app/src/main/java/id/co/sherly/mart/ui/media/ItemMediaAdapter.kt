package id.co.sherly.mart.ui.media

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Media
import id.co.sherly.mart.databinding.ItemMediaBinding
import id.co.sherly.mart.utils.ext.loadImage


class ItemMediaAdapter(
    private var medias: MutableList<Media>,
//    var divWidth: Int,
//    var divHeight: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemMediaBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
//        view.root.post {
//            view.root.layoutParams.width = parent.width/divWidth
//            view.root.layoutParams.height = parent.height/divHeight
//            view.root.requestLayout()
//        }
        return ItemMediaViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = medias.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as ItemMediaViewHolder).binding
        val media = medias[position]
        binding.name.text = media.name
        Log.e("log", Gson().toJson(media))

        if (media.url!=null) {
            binding.icon.loadImage(media.url)
        } else {
            binding.icon.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.placeholder_image))
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
                medias[lastPos].selected = false
//                binding.iconCheck.visibility = View.GONE

                notifyItemChanged(lastPos) // Memperbarui item sebelumnya
            }

            // Memilih item yang baru di klik
            media.selected = !media.selected
            notifyItemChanged(position) // Memperbarui item yang dipilih

            // Menyimpan posisi item yang dipilih
            lastPos = position

            callback?.onMediaSelected(media, position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelections(list: List<Media>) {
        lastPos = -1
        for (p in list) {
            p.selected = false
        }
        notifyDataSetChanged()
    }

    fun items(): List<Media> {
        return medias
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(medias: MutableList<Media>) {
        this.medias.clear()
        this.medias.addAll(medias)
        this.notifyDataSetChanged()
    }

    var callback: Callback? = null
    interface Callback {
        fun onMediaSelected(media: Media, position: Int)
    }


    inner class ItemMediaViewHolder(val binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }
    }
}