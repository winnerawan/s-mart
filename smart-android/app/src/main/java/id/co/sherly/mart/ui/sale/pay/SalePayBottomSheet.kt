package id.co.sherly.mart.ui.sale.pay

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.PayType
import id.co.sherly.mart.data.pref.UserPreferences
import id.co.sherly.mart.databinding.BottomSheetSalePayBinding
import id.co.sherly.mart.ui.sale.ItemPayTypeAdapter
import id.co.sherly.mart.utils.PrintUtils
import id.co.sherly.mart.utils.ext.formatPrice
import id.co.sherly.mart.utils.view.WrapGripLayoutManager
import java.math.BigDecimal
import javax.inject.Inject


@AndroidEntryPoint
class SalePayBottomSheet : BottomSheetDialogFragment(), SalePayContract.View {

    private var _binding: BottomSheetSalePayBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferences: UserPreferences

    @Inject
    lateinit var mPayTypeAdapter: ItemPayTypeAdapter

    companion object {
        const val TAG = "SalePayBottomSheet"
        private var ARG_TYPES = "arg-types"
        private var ARG_TOTAL = "arg-total"
        private var payTypes: List<PayType>? = listOf()
        private var total: String? = "0"

        @JvmStatic
        fun newInstance(total: String, payTypes: ArrayList<PayType>) =
            SalePayBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_TYPES, payTypes)
                    putString(ARG_TOTAL, total)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { it ->
            payTypes = it.getParcelableArrayList(ARG_TYPES)
            payTypes?.let { mPayTypeAdapter.addItems(it.toMutableList()) }
            total = it.getString(ARG_TOTAL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetSalePayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerPayType.adapter = mPayTypeAdapter
            recyclerPayType.layoutManager =
                WrapGripLayoutManager(requireActivity(), 3, GridLayoutManager.VERTICAL, false)
            binding.totalView.textTotal.text = total?.formatPrice()

            numPadView.setOnInteractionListener(
                onRightIconClick = {
                    binding.totalView.textPayment.text = "0".formatPrice()
                    binding.totalView.textChange.text = "0".formatPrice()
                    change = BigDecimal(0)
                    currentValue = "0"
                },
                onLeftIconClick = {
                    binding.totalView.textPayment.text = handleLeftValue().formatPrice()
                },
                onNewValue = {
                    binding.totalView.textPayment.text = handleNewValue(it).formatPrice()
                }
            )


            btnPay.setOnClickListener {
                PrintUtils.print(
                    requireActivity(),
                    preferences.storeName.toString(),
                    "",
                    listOf(),
                    "0",
                    "0"
                )
            }
        }
    }

    var change = BigDecimal(0)
    private fun calculateChange() {
        change = (currentValue.toBigDecimal() - total?.toBigDecimal()!!)
        Log.e("LOG", "total?.toBigDecimal() ${total?.toBigDecimal()}")
        Log.e("LOG", "currentValue?.toBigDecimal() ${currentValue?.toBigDecimal()}")
        binding.totalView.textChange.text = change.toString().formatPrice()
        if (currentValue.toBigDecimal()==BigDecimal(0)) {
            binding.totalView.textChange.text = "0".formatPrice()
        }
    }

    private var currentValue: String = ""

    private fun handleNewValue(value: String): String {
        currentValue = "${if (currentValue != "0") currentValue else ""}$value"
        calculateChange()
        return currentValue
    }

    /**
     * Trunk last char of the String if possible, else, return 0
     * @return the value after a back input
     */
    private fun handleLeftValue(): String {
        if (currentValue.length > 1) {
            currentValue = currentValue.shorten()
            Log.e("LOG", "currentValue = $currentValue")
        } else {
            currentValue = "0"
            binding.totalView.textChange.text = "0".formatPrice()
        }
        calculateChange()
        return currentValue
    }

    private fun String.shorten() = substring(0, lastIndex)


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        val width = requireContext().resources.getDimensionPixelSize(R.dimen.bottom_sheet_width)
        layoutParams.width = width
        bottomSheet.layoutParams = layoutParams
    }

    override fun onDismiss(dialog: DialogInterface) {
        callback?.onDismissPayBottomSheet()
        super.onDismiss(dialog)
    }

    private var callback: Callback? = null

    interface Callback {
        fun onPayment(payType: PayType, cashBack: BigDecimal)
        fun onDismissPayBottomSheet()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}