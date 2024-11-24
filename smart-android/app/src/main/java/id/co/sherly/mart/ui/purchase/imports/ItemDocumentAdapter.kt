package id.co.sherly.mart.ui.purchase.imports

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
import id.co.sherly.mart.data.model.DocumentImport
import id.co.sherly.mart.databinding.ItemDocumentBinding
import id.co.sherly.mart.utils.ext.loadImage


class ItemDocumentAdapter(
    private var documents: MutableList<DocumentImport>,
//    var divWidth: Int,
//    var divHeight: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemDocumentBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
//        view.root.post {
//            view.root.layoutParams.width = parent.width/divWidth
//            view.root.layoutParams.height = parent.height/divHeight
//            view.root.requestLayout()
//        }
        return ItemDocumentImportViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = documents.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as ItemDocumentImportViewHolder).binding
        val document = documents[position]
        binding.name.text = document.name
        Log.e("log", Gson().toJson(document))
        binding.datetime.text = document.datetime
//        if (document.url!=null) {
//            binding.icon.loadImage(document.url)
//        } else {
//            binding.icon.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.placeholder_image))
//        }
       
//        binding.iconCheck.setBackgroundColor(if (lastPos == position) Color.GREEN else Color.TRANSPARENT)

//        if (lastPos == position) {
//            binding.iconCheck.visibility = View.VISIBLE
//        } else {
//            binding.iconCheck.visibility = View.GONE
//        }
//        // Klik item untuk memilih produk
//        binding.root.setOnClickListener {
//            if (lastPos != -1 && lastPos != position) {
//                // Menyembunyikan iconCheck dari item sebelumnya
//                documents[lastPos].selected = false
////                binding.iconCheck.visibility = View.GONE
//
//                notifyItemChanged(lastPos) // Memperbarui item sebelumnya
//            }
//
//            // Memilih item yang baru di klik
//            document.selected = !document.selected
//            notifyItemChanged(position) // Memperbarui item yang dipilih
//
//            // Menyimpan posisi item yang dipilih
//            lastPos = position
//
//            callback?.onDocumentImportSelected(document, position)
//        }
        binding.btnImport.setOnClickListener {
            callback?.onImport(document, position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelections(list: List<DocumentImport>) {
        lastPos = -1
        for (p in list) {
            p.selected = false
        }
        notifyDataSetChanged()
    }

    fun items(): List<DocumentImport> {
        return documents
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(documents: MutableList<DocumentImport>) {
        this.documents.clear()
        this.documents.addAll(documents)
        this.notifyDataSetChanged()
    }

    var callback: Callback? = null
    interface Callback {
//        fun onDocumentImportSelected(media: DocumentImport, position: Int)
        fun onImport(documentImport: DocumentImport, position: Int)
    }


    inner class ItemDocumentImportViewHolder(val binding: ItemDocumentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }
    }
}