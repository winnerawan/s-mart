package id.co.sherly.mart.ui.sale

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carteasy.v1.lib.Carteasy
import dagger.hilt.android.AndroidEntryPoint
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.Customer
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.data.model.ItemStock
import id.co.sherly.mart.data.model.PayType
import id.co.sherly.mart.databinding.FragmentSaleBinding
import id.co.sherly.mart.ui.base.view.BaseFragment
import id.co.sherly.mart.ui.item.CategoryAdapter
import id.co.sherly.mart.ui.main.MainActivity
import id.co.sherly.mart.ui.purchase.SpinnerSupplierAdapter
import id.co.sherly.mart.ui.purchase.create.ItemCartPurchaseAdapter
import id.co.sherly.mart.ui.sale.pay.SalePayBottomSheet
import id.co.sherly.mart.utils.CarteasyHelper
import id.co.sherly.mart.utils.ext.dpToPx
import id.co.sherly.mart.utils.view.WrapGripLayoutManager
import id.co.sherly.mart.utils.view.WrapLinearLayoutManager
import java.math.BigDecimal
import javax.inject.Inject

@AndroidEntryPoint
class SaleFragment : BaseFragment<FragmentSaleBinding>(), SaleContract.View, CategoryAdapter.Callback,
    ItemViewSaleAdapter.Callback, ItemCartSaleAdapter.Callback, SalePayBottomSheet.Callback, MainActivity.Callback {

    @Inject
    lateinit var presenter: SalePresenter

    @Inject
    lateinit var adapter: ItemViewSaleAdapter

    @Inject
    lateinit var mCategoryAdapter: CategoryAdapter

    @Inject
    lateinit var mCartSaleAdapter: ItemCartSaleAdapter

    @Inject
    lateinit var mPayTypeAdapter: ItemPayTypeAdapter

    private var mPayBottomSheet: SalePayBottomSheet? = null
    
    private var gridLayout1: WrapGripLayoutManager? = null
    private var gridLayout2: WrapGripLayoutManager? = null
    private var customer: Customer? = null
    private var category: Category? = null
    private var items: List<ItemStock> = listOf()
    private var cart: Carteasy? = null
    private var mSpinnerCustomerAdapter: SpinnerCustomerAdapter? = null
    private var payTypes: List<PayType> = listOf()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSaleBinding = FragmentSaleBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            gridLayout1 = WrapGripLayoutManager(requireActivity(), 4, GridLayoutManager.VERTICAL, false)
            gridLayout2 = WrapGripLayoutManager(requireActivity(), 4, GridLayoutManager.VERTICAL, false)
            binding?.recyclerView?.adapter = adapter
            adapter.callback = this@SaleFragment
            recyclerView.layoutManager = gridLayout1
            binding?.recyclerCategory?.adapter = mCategoryAdapter
            mCategoryAdapter.callback = this@SaleFragment
            binding?.recyclerCategory?.layoutManager = WrapLinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)

            cartSale.recyclerView.adapter = mCartSaleAdapter
            mCartSaleAdapter?.callback = this@SaleFragment
            mSpinnerCustomerAdapter = SpinnerCustomerAdapter(requireActivity(), R.id.txt_name, mutableListOf())
            cartSale.spinnerCustomer.adapter = mSpinnerCustomerAdapter

            totalView.recyclerPayType.adapter = mPayTypeAdapter
            totalView.recyclerPayType.layoutManager =  WrapGripLayoutManager(requireActivity(), 3, GridLayoutManager.VERTICAL, false)

            showConstraintAdd()
            cart = Carteasy()
            cart?.clearCart(requireActivity())
            CarteasyHelper().getCartsToItems(requireActivity(), cart)

            //todo
            tag?.setOnTouchListener { v, event ->
                if (event != null) {
                    if (event.action == MotionEvent.ACTION_UP && event.x < dpToPx(28F)) {
                        clearCategorySelected()
                    }
                }
                true
            }

            btnSave.setOnClickListener {
                payTypes?.let { showPayBottomSheet(total, it) }
            }
            (requireActivity() as MainActivity).callback = this@SaleFragment
        }
        presenter.payTypes()
        presenter.customers()
        presenter.categories()
        presenter.data(null, null)
    }

    override fun onPhysicalBarcodeScanned(barcode: String) {
        adapter.filterBySku(barcode)
    }

    override fun onPayment(payType: PayType, change: BigDecimal) {

    }

    override fun onDismissPayBottomSheet() {
        mPayBottomSheet?.dismiss()
        mPayBottomSheet = null
    }

    private fun showPayBottomSheet(total: BigDecimal, list: List<PayType>) {
        mPayBottomSheet = SalePayBottomSheet.newInstance(total.toString(), list as ArrayList<PayType>)
        mPayBottomSheet?.show(childFragmentManager, SalePayBottomSheet.TAG)
    }

    override fun showData(data: List<ItemStock>) {
        hideProgress()
        this.items = data
        adapter.addItems(this.items.toMutableList())
    }

    private fun clearCategorySelected(){
        mCategoryAdapter.clearSelections(mCategoryAdapter.items())
//        presenter.data(null, null)
        adapter.resetFilter()
        binding?.tag?.visibility = View.GONE
    }

    private fun setCategorySelected(category: Category) {
        binding?.tag?.visibility = View.VISIBLE
        binding?.tag?.text = category.name
    }

    override fun onCategorySelected(category: Category, position: Int) {
        this.category = category
        setCategorySelected(category)
//        items.filter { it.categoryId == category.id }
//        presenter.data(category.id, null)
        adapter.filterByCategory(category)
    }

    override fun provideCategories(data: List<Category>) {
        mCategoryAdapter.addItems(data.toMutableList())
        category = data[0]
    }

    override fun provideCustomers(customers: List<Customer>) {
        mSpinnerCustomerAdapter?.addItems(customers)
        customer = customers[0]
    }

    override fun showProgress() {
        binding?.progress?.visibility = View.VISIBLE
        binding?.recyclerView?.visibility = View.GONE
    }

    override fun hideProgress() {
        binding?.progress?.visibility = View.GONE
        binding?.recyclerView?.visibility = View.VISIBLE
    }

    private fun expandConstraintContent() {
        binding?.recyclerView?.layoutManager = gridLayout1
        val mConstrainLayout: ConstraintLayout? = binding?.constraintContent
        val lp: ConstraintLayout.LayoutParams =
            mConstrainLayout?.layoutParams as ConstraintLayout.LayoutParams
        lp.matchConstraintPercentWidth = 1.toFloat()
        mConstrainLayout.setLayoutParams(lp)
    }

    private fun collapseConstraintContent() {
        binding?.recyclerView?.layoutManager = gridLayout2
        val mConstrainLayout: ConstraintLayout? = binding?.constraintContent
        val lp: ConstraintLayout.LayoutParams =
            mConstrainLayout?.layoutParams as ConstraintLayout.LayoutParams
        lp.matchConstraintPercentWidth = 0.75.toFloat()
        mConstrainLayout.setLayoutParams(lp)
    }

    private fun showConstraintAdd() {
        collapseConstraintContent()
        val mConstrainLayout: ConstraintLayout? = binding?.constraintAdd
        val lp: ConstraintLayout.LayoutParams =
            mConstrainLayout?.layoutParams as ConstraintLayout.LayoutParams
        lp.matchConstraintPercentWidth = 0.25.toFloat()
        mConstrainLayout.setLayoutParams(lp)

    }

    private fun hideConstraintAdd() {
        expandConstraintContent()
        adapter.clearSelections(adapter.items())
        val mConstrainLayout: ConstraintLayout? = binding?.constraintAdd
        val lp: ConstraintLayout.LayoutParams =
            mConstrainLayout?.layoutParams as ConstraintLayout.LayoutParams
        lp.matchConstraintPercentWidth = 0.toFloat()
        mConstrainLayout.setLayoutParams(lp)
        binding?.btnDelete?.visibility = View.GONE
        adapter.clearSelections(adapter.items())
    }

    override fun onItemSelected(item: ItemStock, position: Int) {
    }

    private var quantity = 0
    override fun onDecreaseQuantity(item: ItemStock, textView: AppCompatTextView, position: Int) {
        var qty = textView.text.toString().toInt()
        qty -= 1
        textView.text = qty.toString()
        cart?.update(item.itemId, "quantity", qty, requireActivity())
        val id = cart?.get(item.itemId, "id", requireActivity())
        val items = CarteasyHelper().getCartsToItems(requireActivity(), cart)
        val itemCart = items.find { it.id == id }
        item.quantity =qty
        if (qty <= -1 || qty == 0) {
            textView.text = "0"
            item.quantity = 0
//            try {

//            } catch (_: Exception){}

            if (itemCart!=null) {
                val cartPos = itemCart.recyclerViewPosition//cart?.getLong(item.itemId, "recyclerViewPosition", this) ?: 0
                Log.e("LOG cartpos - = 0", "${cartPos}")
//                cart?.update(item.itemId, "recyclerViewPosition", cartPos.toInt() - 1, this)
                mCartSaleAdapter.removeItem(item, cartPos.toInt())

                if (cartPos != items.size) {
//                    CarteasyHelper().updateOtherCartsPositions(this, cart)
                }
            }
            quantity = qty
            item.quantity = qty

            cart?.RemoveId(
                item.itemId,
                requireActivity()
            )

            cart?.update(item.itemId, "quantity", qty, requireActivity())
            item.quantity = (quantity)
            if (cart?.ViewAll(requireActivity())?.isEmpty()==true) {
                getCarts()//tes
            } else {

                getCarts()
            }

        } else {
            quantity = textView.text.toString().toInt()
            item.quantity = (quantity)
            item.quantity = qty
//            val cartPos = cart?.getLong(item.itemId, "recyclerViewPosition", this)?:0
//            Log.e("LOG cartpos - ", "${cartPos}")
            if (itemCart!=null) {
                val cartPos = itemCart.recyclerViewPosition//cart?.getLong(item.itemId, "recyclerViewPosition", this) ?: 0
                Log.e("LOG cartpos - ", "${cartPos}")
                mCartSaleAdapter.updateItem(item, cartPos.toInt())
            }
            getCarts()
        }
    }
    
    

    override fun onInCreaseQuantity(item: ItemStock, textView: AppCompatTextView, position: Int) {
        showConstraintAdd()
        val qty = textView.text.toString().toInt()
        val price = item.sellingPrice?:0
        val cartSize = CarteasyHelper().getCartsToItems(requireActivity(), cart).size?:0

        val id = cart?.get(item.itemId, "id", requireActivity())
        val items = CarteasyHelper().getCartsToItems(requireActivity(), cart)
        val itemCart = items.find { it.id == id }
        item.quantity = qty+1
        if (itemCart!=null) {
            val cartPos = itemCart.recyclerViewPosition//cart?.getLong(item.itemId, "recyclerViewPosition", this) ?: 0
            Log.e("LOG cartpos +", "${cartPos}")
            mCartSaleAdapter.updateItem(item, cartPos.toInt())
        } else {
            item.quantity = 1
            cart?.add(item.itemId, "id", item.itemId)
            cart?.add(item.itemId, "name", item.name)
            cart?.add(item.itemId, "price", price)
            cart?.add(item.itemId, "quantity", item.quantity)
            cart?.add(item.itemId, "image", item.image)
            cart?.add(item.itemId, "category", item.categoryId)
            cart?.add(item.itemId, "recyclerViewPosition", cartSize)
            cart?.add(item.itemId, "selling_price", item.sellingPrice)

            cart?.commit(requireActivity())
            mCartSaleAdapter.addItem(item)
        }

//        if (qty==0) {
//
//        }
//        item.quantity += 1
//
//        item.quantity = qty + 1
        textView.text = item.quantity.toString()

        cart?.update(item.itemId, "quantity", textView.text.toString(), requireActivity())
//        adapter.resetFilter()
        getCarts()
    }

    override fun onUpdatePosition(item: ItemStock, position: Int) {
        Log.e("POS", "on update pos: ${item.name} position = $position")
        cart?.update(item.itemId, "recyclerViewPosition", position, requireActivity())
    }

    private fun getCarts() {
        CarteasyHelper().getCarts(requireActivity(), cart)
        CarteasyHelper().getCartsToItems(requireActivity(), cart)
    }

    override fun onFilteredAddToCart(item: ItemStock) {
        Log.e("LOG", "filteredAddToCart= ${item.name}")
        val price = item.sellingPrice?:0
        val cartItems =  CarteasyHelper().getCartsToItems(requireActivity(), cart)
        val cartSize = cartItems.size?:0
        val itemInCart = cartItems.find { it.id == item.itemId }
        if (itemInCart!=null) {
            val cartPos = itemInCart.recyclerViewPosition
            val itemQty = itemInCart.quantity +1
            item.quantity = itemQty
            cart?.update(item.itemId, "quantity", itemQty, requireActivity())
            mCartSaleAdapter.updateItem(item, cartPos.toInt())
        } else {
            item.quantity = 1
            cart?.add(item.itemId, "id", item.itemId)
            cart?.add(item.itemId, "name", item.name)
            cart?.add(item.itemId, "price", price)
            cart?.add(item.itemId, "quantity", item.quantity)
            cart?.add(item.itemId, "image", item.image)
            cart?.add(item.itemId, "category", item.categoryId)
            cart?.add(item.itemId, "recyclerViewPosition", cartSize)

            cart?.commit(requireActivity())
            mCartSaleAdapter.addItem(item)
        }
        adapter.resetFilter()
    }

    private var total: BigDecimal = BigDecimal(0)
    override fun onPriceCalculated(t: BigDecimal, subtotal: String?, total: String?) {
        this.total = t
        binding?.totalView?.subtotal?.text = subtotal
        binding?.totalView?.tax?.text = "0"
        binding?.totalView?.total?.text = total
    }

    override fun showPayTypes(payTypes: List<PayType>) {
        this.payTypes = payTypes
        this.payTypes[0].selected = true
        mPayTypeAdapter.addItems(this.payTypes.toMutableList())
    }

    private val barcode = StringBuffer()

//    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
//
//        if (event.action == KeyEvent.ACTION_DOWN) {
//            val pressedKey = event.unicodeChar.toChar()
//            barcode.append(pressedKey)
//        }
//        if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
//            adapter.filterBySku(barcode.toString())
//            Toast.makeText(requireActivity(), barcode.toString(), Toast.LENGTH_SHORT).show()
//            barcode.delete(0, barcode.length)
//
//            return true
//        }
//
//        return super.dispatchKeyEvent(event)
//    }
}