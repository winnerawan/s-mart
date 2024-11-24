package id.co.sherly.mart.ui.sale

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Customer

class SpinnerCustomerAdapter(
    context: Activity,
    id: Int,
    private val customers: MutableList<Customer>
): ArrayAdapter<Customer>(context, id, customers) {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    fun addItems(list: List<Customer>) {
        this.customers.clear()
        this.customers.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView: View = inflater.inflate(R.layout.item_spinner, parent, false)
        val textView: TextView = itemView.findViewById(R.id.txt_name)
        textView.text = customers[position].name?.uppercase()
        return itemView
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        return getView(position, convertView, parent)
    }

    fun getItems(): MutableList<Customer> {
        return customers
    }

    override fun getItem(position: Int): Customer {
        return customers[position]
    }
}