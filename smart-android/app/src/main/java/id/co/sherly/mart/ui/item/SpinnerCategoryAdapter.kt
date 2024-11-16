package id.co.sherly.mart.ui.item

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Category

class SpinnerCategoryAdapter(
    context: Activity,
    id: Int,
    private val categories: MutableList<Category>
): ArrayAdapter<Category>(context, id, categories) {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    fun addItems(list: List<Category>) {
        this.categories.clear()
        this.categories.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView: View = inflater.inflate(R.layout.item_spinner, parent, false)
        val textView: TextView = itemView.findViewById(R.id.txt_name)
        textView.text = categories[position].name?.uppercase()
        return itemView
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        return getView(position, convertView, parent)
    }

    fun getItems(): MutableList<Category> {
        return categories
    }

    override fun getItem(position: Int): Category {
        return categories[position]
    }
}