package id.co.sherly.mart.utils

import android.content.Context
import android.util.Log
import com.anggastudio.printama.Printama
import id.co.sherly.mart.data.model.ItemStock
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date


object PrintUtils {
    private val btOutputStream: OutputStream? = null

    fun print(context: Context, name: String, date: String, items: List<ItemStock>, total: String, change: String) {

        val sdf = SimpleDateFormat("dd MMM yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val text = """
            -------------
            This will be printed
            Left aligned
            cool isn't it?
            ------------------
            
            """.trimIndent()
        Printama.with(context).connect { printama: Printama ->
//            printama.printText(Printama.CENTER, text);

            printama.printText(Printama.CENTER, "$name\n$currentDate")
//            printama.printTextJustifyBold("text1", "text2")
            printama.feedPaper()
//            printama.openCashDrawer()
//            printama.printText(Printama.CENTER, "------------------\n")
//            printama.printText(Printama.CENTER, "")
//            printama.printText(Printama.CENTER, "")
//            printama.c
            printama.close()
        }
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