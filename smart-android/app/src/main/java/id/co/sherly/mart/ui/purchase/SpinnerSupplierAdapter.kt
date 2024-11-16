package id.co.sherly.mart.ui.purchase

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Supplier

class SpinnerSupplierAdapter(
    context: Activity,
    id: Int,
    private val suppliers: MutableList<Supplier>
): ArrayAdapter<Supplier>(context, id, suppliers) {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    fun addItems(list: List<Supplier>) {
        this.suppliers.clear()
        this.suppliers.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView: View = inflater.inflate(R.layout.item_spinner, parent, false)
        val textView: TextView = itemView.findViewById(R.id.txt_name)
        textView.text = suppliers[position].name?.uppercase()
        return itemView
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        return getView(position, convertView, parent)
    }

    fun getItems(): MutableList<Supplier> {
        return suppliers
    }

    override fun getItem(position: Int): Supplier {
        return suppliers[position]
    }
}