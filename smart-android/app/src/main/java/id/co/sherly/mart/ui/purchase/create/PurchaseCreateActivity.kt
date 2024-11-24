package id.co.sherly.mart.ui.purchase.create

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carteasy.v1.lib.Carteasy
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.data.model.Supplier
import id.co.sherly.mart.databinding.ActivityPurchaseCreateBinding
import id.co.sherly.mart.ui.base.view.BaseActivity
import id.co.sherly.mart.ui.item.CategoryAdapter
import id.co.sherly.mart.ui.purchase.SpinnerSupplierAdapter
import id.co.sherly.mart.utils.CarteasyHelper
import id.co.sherly.mart.utils.ext.cashDrawer
import id.co.sherly.mart.utils.ext.dpToPx
import id.co.sherly.mart.utils.ext.hideKeyboard
import id.co.sherly.mart.utils.view.WrapGripLayoutManager
import id.co.sherly.mart.utils.view.WrapLinearLayoutManager
import javax.inject.Inject


@AndroidEntryPoint
class PurchaseCreateActivity : BaseActivity<ActivityPurchaseCreateBinding>(), PurchaseCreateContract.View, CategoryAdapter.Callback,
    ItemViewPurchaseAdapter.Callback, ItemCartPurchaseAdapter.Callback {

    override fun createBinding(): ActivityPurchaseCreateBinding = ActivityPurchaseCreateBinding.inflate(layoutInflater)

    @Inject
    lateinit var presenter: PurchaseCreatePresenter

    @Inject
    lateinit var adapter: ItemViewPurchaseAdapter

    @Inject
    lateinit var mCategoryAdapter: CategoryAdapter

    @Inject
    lateinit var mCartPurchaseAdapter: ItemCartPurchaseAdapter

    private var mSpinnerSupplierAdapter: SpinnerSupplierAdapter? = null
    private var gridLayout1: WrapGripLayoutManager? = null
    private var gridLayout2: GridLayoutManager? = null
    private var supplier: Supplier? = null
    private var category: Category? = null
    private var cart: Carteasy? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {


            gridLayout1 = WrapGripLayoutManager(this@PurchaseCreateActivity, 3, GridLayoutManager.VERTICAL, false)
            gridLayout2 = GridLayoutManager(this@PurchaseCreateActivity, 2, GridLayoutManager.VERTICAL, false)
//            (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            binding.recyclerView.adapter = adapter
            adapter.callback = this@PurchaseCreateActivity

//            (binding.recyclerCategory.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            binding.recyclerCategory.adapter = mCategoryAdapter
            binding.recyclerCategory.layoutManager = WrapLinearLayoutManager(this@PurchaseCreateActivity, RecyclerView.HORIZONTAL, false)
            mCategoryAdapter.callback = this@PurchaseCreateActivity
//            val snapHelper = PagerSnapHelper()
//            snapHelper.attachToRecyclerView(binding.recyclerCategory)
//            binding?.recyclerView?.layoutManager = LinearLayoutManager(requireActivity())

            showConstraintAdd()
            cart = Carteasy()
            cart?.clearCart(this@PurchaseCreateActivity)
            CarteasyHelper().getCartsToItems(this@PurchaseCreateActivity, cart)
//            if (cart?.ViewAll(this@PurchaseCreateActivity)!=null) {
//                cart?.clearCart(this@PurchaseCreateActivity)
//            }
            binding.cartPurchase.recyclerView.adapter = mCartPurchaseAdapter
            mCartPurchaseAdapter.callback = this@PurchaseCreateActivity
            binding.cartPurchase.recyclerView.layoutManager = LinearLayoutManager(this@PurchaseCreateActivity)
            binding.iconBack.setOnClickListener { finishActivity() }
            mSpinnerSupplierAdapter = SpinnerSupplierAdapter(this@PurchaseCreateActivity, R.id.txt_name, mutableListOf())
            setUpSpinnerSupplier()
            //todo
            binding?.tag?.setOnTouchListener { v, event ->
                if (event != null) {
                    if (event.action == MotionEvent.ACTION_UP && event.x < dpToPx(28F)) {
                        clearCategorySelected()
                    }
                }
                true
            }
        }
        setUpSearchView()
        setUpBarcodeScanner()
        binding.itemCount.text = getString(R.string.item_cart_count_format, "0")

        onBackPressedDispatcher.addCallback(this) {
            finishActivity()
        }
        presenter.suppliers()
        presenter.categories()
        presenter.data(null, null)

        binding.btnSave.setOnClickListener {
            val file = "/sys/class/gpio/gpio14/value"
            cashDrawer(file, "1")
        }
    }


    private fun expandConstraintContent() {
        binding.recyclerView.layoutManager = gridLayout1
        val mConstrainLayout: ConstraintLayout? = binding.constraintContent
        val lp: ConstraintLayout.LayoutParams =
            mConstrainLayout?.layoutParams as ConstraintLayout.LayoutParams
        lp.matchConstraintPercentWidth = 1.toFloat()
        mConstrainLayout.setLayoutParams(lp)
    }

    private fun collapseConstraintContent() {
        binding.recyclerView.layoutManager = gridLayout1
        val mConstrainLayout: ConstraintLayout = binding.constraintContent
        val lp: ConstraintLayout.LayoutParams =
            mConstrainLayout.layoutParams as ConstraintLayout.LayoutParams
        lp.matchConstraintPercentWidth = 0.75.toFloat()
        mConstrainLayout.setLayoutParams(lp)
    }

    private fun showConstraintAdd() {
        collapseConstraintContent()
        mCategoryAdapter.clearSelections(mCategoryAdapter.items())
        val mConstrainLayout: ConstraintLayout = binding.constraintAdd
        val lp: ConstraintLayout.LayoutParams =
            mConstrainLayout.layoutParams as ConstraintLayout.LayoutParams
        lp.matchConstraintPercentWidth = 0.25.toFloat()
        mConstrainLayout.setLayoutParams(lp)

    }

    private fun hideConstraintAdd() {
        expandConstraintContent()
        val mConstrainLayout: ConstraintLayout? = binding.constraintAdd
        val lp: ConstraintLayout.LayoutParams =
            mConstrainLayout?.layoutParams as ConstraintLayout.LayoutParams
        lp.matchConstraintPercentWidth = 0.toFloat()
        mConstrainLayout.setLayoutParams(lp)
        binding.btnDelete.visibility = View.GONE
    }

    private fun setUpSearchView() {
        binding.txtQuery.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty() || !newText.isNullOrBlank()) {
                    newText.let {q ->
//                        presenter.data(null, q.toString())
//                        items.filter { it.sku?.lowercase()?.contains(q)==true }
                        adapter.filterByNameSku(q)
                    }
                } else {
                    clearCategorySelected()
                }
                return false
            }

        })

    }

    private fun setUpBarcodeScanner() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES)
        options.setPrompt("Scan a barcode")
        options.setCameraId(0) // Use a specific camera of the device
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        binding.scanBarcode.setOnClickListener {
            barcodeLauncher.launch(options)
        }

    }

    private val barcodeLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            binding.txtQuery.setQuery(result.contents, true)
            binding.txtQuery.performClick()
            binding.txtQuery.isIconified = false
            hideKeyboard()
        }
    }

    private fun clearCategorySelected(){
        mCategoryAdapter.clearSelections(mCategoryAdapter.items())
//        presenter.data(null, null)
        adapter.resetFilter()
        binding.tag.visibility = View.GONE
    }

    private fun setCategorySelected(category: Category) {
        binding.tag.visibility = View.VISIBLE
        binding.tag.text = category.name
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

    override fun provideSuppliers(suppliers: List<Supplier>) {
        mSpinnerSupplierAdapter?.addItems(suppliers)
        supplier = suppliers[0]
    }

    private var items: List<Item> = listOf()
    override fun showData(items: List<Item>) {
        hideProgress()
        this.items = items
        adapter.addItems(this.items.toMutableList())
//        binding.itemCount.text = getString(R.string.item_cart_count_format, "${items.size}")
//        hideConstraintAdd()
//        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

    }

    private fun setUpSpinnerSupplier() {
        binding.cartPurchase.spinnerSupplier.adapter = mSpinnerSupplierAdapter
        binding.cartPurchase.spinnerSupplier.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?, view: View?, position: Int, l: Long
                ) {
                    supplier = mSpinnerSupplierAdapter?.getItem(position)!!
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    supplier = mSpinnerSupplierAdapter?.getItem(0)!!
                }
            }

    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun finishActivity() {
        finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
    }


    override fun onItemSelected(item: Item, position: Int) {
        showConstraintAdd()
    }
    private var quantity = 0
    override fun onDecreaseQuantity(item: Item, textView: AppCompatTextView, position: Int) {
        var qty = textView.text.toString().toInt()
        qty -= 1
        textView.text = qty.toString()
        cart?.update(item.id, "quantity", qty, this)
        presenter.updateTempQty(item, qty)
        val id = cart?.get(item.id, "id", this)
        val items = CarteasyHelper().getCartsToItems(this, cart)
        val itemCart = items.find { it.id == id }
        item.quantity =qty
        if (qty <= -1 || qty == 0) {
            textView.text = "0"
            item.quantity = 0
//            try {

//            } catch (_: Exception){}

            if (itemCart!=null) {
                val cartPos = itemCart.recyclerViewPosition//cart?.getLong(item.id, "recyclerViewPosition", this) ?: 0
                Log.e("LOG cartpos - = 0", "${cartPos}")
//                cart?.update(item.id, "recyclerViewPosition", cartPos.toInt() - 1, this)
                mCartPurchaseAdapter.removeItem(item, cartPos.toInt())

                if (cartPos != items.size) {
//                    CarteasyHelper().updateOtherCartsPositions(this, cart)
                }
            }
            quantity = qty
            item.quantity = qty

            cart?.RemoveId(
                item.id,
                this
            )

            cart?.update(item.id, "quantity", qty, this)
            presenter.updateTempQty(item, qty)
            item.quantity = (quantity)
            if (cart?.ViewAll(this)?.isEmpty()==true) {
                getCarts()//tes
            } else {

                getCarts()
            }

        } else {
            quantity = textView.text.toString().toInt()
            item.quantity = (quantity)
            item.quantity = qty
//            val cartPos = cart?.getLong(item.id, "recyclerViewPosition", this)?:0
//            Log.e("LOG cartpos - ", "${cartPos}")
            if (itemCart!=null) {
                val cartPos = itemCart.recyclerViewPosition//cart?.getLong(item.id, "recyclerViewPosition", this) ?: 0
                Log.e("LOG cartpos - ", "${cartPos}")
                mCartPurchaseAdapter.updateItem(item, cartPos.toInt())
            }
            getCarts()
        }
    }

    override fun onInCreaseQuantity(item: Item, textView: AppCompatTextView, position: Int) {
        showConstraintAdd()
        val qty = textView.text.toString().toInt()
        val price = item.tmpPrice?:0
        val cartSize = CarteasyHelper().getCartsToItems(this, cart).size?:0

        val id = cart?.get(item.id, "id", this)
        val items = CarteasyHelper().getCartsToItems(this, cart)
        val itemCart = items.find { it.id == id }
        item.quantity = qty+1
        if (itemCart!=null) {
            val cartPos = itemCart.recyclerViewPosition//cart?.getLong(item.id, "recyclerViewPosition", this) ?: 0
            Log.e("LOG cartpos +", "${cartPos}")
            mCartPurchaseAdapter.updateItem(item, cartPos.toInt())
        } else {
            item.quantity = 1
            cart?.add(item.id, "id", item.id)
            cart?.add(item.id, "name", item.name)
            cart?.add(item.id, "price", price)
            cart?.add(item.id, "quantity", item.quantity)
            cart?.add(item.id, "image", item.image)
            cart?.add(item.id, "category", item.categoryId)
            cart?.add(item.id, "recyclerViewPosition", cartSize)
            cart?.add(item.id, "tmp_price", item.tmpPrice)

            cart?.commit(this)
            mCartPurchaseAdapter.addItem(item)
        }

//        if (qty==0) {
//
//        }
//        item.quantity += 1
//
//        item.quantity = qty + 1
        textView.text = item.quantity.toString()

        cart?.update(item.id, "quantity", textView.text.toString(), this)
        presenter.updateTempQty(item, textView.text.toString().toInt())
//        adapter.resetFilter()
        getCarts()
    }

    override fun onUpdatePosition(item: Item, position: Int) {
        Log.e("POS", "on update pos: ${item.name} position = $position")
        cart?.update(item.id, "recyclerViewPosition", position, this)
    }

    private fun getCarts() {
        CarteasyHelper().getCarts(this, cart)
        CarteasyHelper().getCartsToItems(this, cart)
    }



    private fun getTotalPriceEst(list: List<Item>): Double? {
        var priceEst = 0.0
        for (i in list.indices) {
            val itemData = list[i]
            if (itemData.quantity > 0) {
                priceEst += list[i].tmpPrice  * list[i].quantity
            }
        }
        return priceEst
    }

    private fun getQtyListMenu(list: List<Item>): Int {
        var size = 0
        for (i in list.indices) {
            val itemData = list[i]
            size += itemData.quantity
        }
        return size
    }

    override fun onFilteredAddToCart(item: Item) {
        Log.e("LOG", "filteredAddToCart= ${item.name}")
        val price = item.tmpPrice?:0
        val cartItems =  CarteasyHelper().getCartsToItems(this, cart)
        val cartSize = cartItems.size?:0
        val itemInCart = cartItems.find { it.id == item.id }
        if (itemInCart!=null) {
            val cartPos = itemInCart.recyclerViewPosition
            val itemQty = itemInCart.quantity +1
            item.quantity = itemQty
            cart?.update(item.id, "quantity", itemQty, this)
            mCartPurchaseAdapter.updateItem(item, cartPos.toInt())
        } else {
            item.quantity = 1
            cart?.add(item.id, "id", item.id)
            cart?.add(item.id, "name", item.name)
            cart?.add(item.id, "price", price)
            cart?.add(item.id, "quantity", item.quantity)
            cart?.add(item.id, "image", item.image)
            cart?.add(item.id, "category", item.categoryId)
            cart?.add(item.id, "recyclerViewPosition", cartSize)

            cart?.commit(this)
            mCartPurchaseAdapter.addItem(item)
        }
        adapter.resetFilter()
    }

    override fun onPriceCalculated(subtotal: String?, total: String?) {
        binding.total.subtotal.text = subtotal
        binding.total.tax.text = "0"
        binding.total.total.text = total
    }

    private val barcode = StringBuffer()

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {

        if (event.action == KeyEvent.ACTION_DOWN) {
            val pressedKey = event.unicodeChar.toChar()
            barcode.append(pressedKey)
        }
        if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
            adapter.filterBySku(barcode.toString())
            Toast.makeText(baseContext, barcode.toString(), Toast.LENGTH_SHORT).show()
            barcode.delete(0, barcode.length)

            return true
        }

        return super.dispatchKeyEvent(event)
    }

}