package id.co.sherly.mart.utils

import android.content.Context
import android.util.Log
import com.anggastudio.printama.Printama
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.ItemStock
import id.co.sherly.mart.utils.ext.formatPrice
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date


object PrintUtils {
    private val btOutputStream: OutputStream? = null

    fun print(context: Context, name: String, items: List<ItemStock>, total: String, payment: String, change: String, footer: String) {

        val sdf = SimpleDateFormat("dd MMM yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val text = """
            -------------
            This will be printed
            Left aligned
            cool isn't it?
            ------------------
            
            """.trimIndent()

        var textItems = ""

        Printama.with(context).connect { printama: Printama ->
//            printama.printText(Printama.CENTER, text);

            printama.printText(Printama.CENTER, "$name\n$currentDate\n")
            printItems(context, printama, items)
            printama.printLine()
            printama.printText("\n")
            printama.printTextBold(Printama.RIGHT, "${context.getString(R.string.total)} : $total\n")
//            printama.printTextJustifyBold("text1", "text2")
            printama.printLine()
            printama.printText("\n\n")
            printama.printText(footer)
            printama.feedPaper()
            printama.openCashDrawer()
//            printama.printText(Printama.CENTER, "------------------\n")
//            printama.printText(Printama.CENTER, "")
//            printama.printText(Printama.CENTER, "")
//            printama.c

            printama.close()
        }
    }

    private fun printItems(context: Context, printama: Printama, items: List<ItemStock>) {
//        printama.printTextJustifyBold(context.getString(R.string.qty).uppercase(), context.getString(R.string.item_menu).uppercase(), context.getString(R.string.price).uppercase())
        printama.printLine()
        items.forEachIndexed { index, itemStock ->
            printama.printTextBold(Printama.LEFT, "${itemStock.name}\n")
            printama.printTextJustify("${itemStock.quantity} x ${itemStock.sellingPrice?.formatPrice()}", "${itemStock.getSubtotal().toString().formatPrice()}\n")
//            printama.printTextJustify("${itemStock.quantity}x", itemStock.name, itemStock.sellingPrice?.formatPrice())
        }
//        printama.close()
//        printama.printLine()

    }

//    fun openCashDrawer(): Boolean {
//        try {
//            val bytes = intArrayToByteArray(intArrayOf(27, 112, 0, 50, 250))
//            btOutputStream.write(bytes)
//            return true
//        } catch (e: IOException) {
//            Log.e(TAG, "Open drawer error", e)
//            return false
//        }
//    }
//
//    private fun intArrayToByteArray(Iarr: IntArray): ByteArray {
//        val bytes = ByteArray(Iarr.size)
//        for (i in Iarr.indices) {
//            bytes[i] = (Iarr[i] and 0xFF).toByte()
//        }
//        return bytes
//    }
}