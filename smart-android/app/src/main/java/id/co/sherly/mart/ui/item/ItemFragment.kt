package id.co.sherly.mart.ui.item

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.data.model.Media
import id.co.sherly.mart.data.pref.UserPreferences
import id.co.sherly.mart.databinding.FragmentItemBinding
import id.co.sherly.mart.ui.base.view.BaseFragment
import id.co.sherly.mart.ui.media.select.SelectMediaActivity
import id.co.sherly.mart.utils.ext.dpToPx
import id.co.sherly.mart.utils.ext.hideKeyboard
import id.co.sherly.mart.utils.ext.loadImage
import id.co.sherly.mart.utils.ext.parcelable
import javax.inject.Inject


@AndroidEntryPoint
class ItemFragment : BaseFragment<FragmentItemBinding>(), ItemContract.View, CategoryAdapter.Callback, ItemAdapter.Callback {

    @Inject
    lateinit var presenter: ItemPresenter

    @Inject
    lateinit var adapter: ItemAdapter
    
    @Inject
    lateinit var mCategoryAdapter: CategoryAdapter

    @Inject
    lateinit var preferences: UserPreferences

    private var isUpdate = false

    private var gridLayout1: GridLayoutManager? = null
    private var gridLayout2: GridLayoutManager? = null

    private var item: Item? = null
    private var category: Category? = null
    private var mSpinnerCategoryAdapter: SpinnerCategoryAdapter? = null

    private var mediaUid: String? = null

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentItemBinding = FragmentItemBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            mSpinnerCategoryAdapter = SpinnerCategoryAdapter(requireActivity(), R.id.txt_name, mutableListOf())

            gridLayout1 = GridLayoutManager(requireActivity(), preferences.masterItemGridCount, GridLayoutManager.VERTICAL, false)
            gridLayout2 = GridLayoutManager(requireActivity(), preferences.masterItemGridCount-1, GridLayoutManager.VERTICAL, false)
            binding?.recyclerCategory?.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)

            (binding?.recyclerView?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            binding?.recyclerView?.adapter = adapter
            adapter.callback = this@ItemFragment
            (binding?.recyclerCategory?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            binding?.recyclerCategory?.adapter = mCategoryAdapter
            mCategoryAdapter.callback = this@ItemFragment

//            val snapHelper = PagerSnapHelper()
//            snapHelper.attachToRecyclerView(binding?.recyclerCategory)
//            binding?.recyclerView?.layoutManager = LinearLayoutManager(requireActivity())

            expandConstraintContent()
            binding?.btnAdd?.setOnClickListener {
                isUpdate = false
                binding?.text1?.text = getString(R.string.add_new_master_item)
                showConstraintAdd()
                binding?.textName?.text?.clear()
                adapter.clearSelections(adapter.items())
                binding?.textSku?.text?.clear()
                binding?.textDescription?.text?.clear()
                requireActivity()?.hideKeyboard()
                clearThumbnail()

            }
            binding?.iconClose?.setOnClickListener {
                hideConstraintAdd()
                adapter.clearSelections(adapter.items())
            }

            binding?.btnSave?.setOnClickListener {
                val name = binding?.textName?.text.toString()
                val sku = binding?.textSku?.text.toString()
                val description = binding?.textDescription?.text.toString()
                hideConstraintAdd()
                if (isUpdate) {
                    item?.let {
                        presenter.update(it.id!!, category?.id!!, name, sku, description, mediaUid)
                        requireActivity()?.hideKeyboard()
                    }
                    return@setOnClickListener
                }
                presenter.create(category?.id!!, name, sku, description, mediaUid)
                requireActivity()?.hideKeyboard()
            }
            binding?.btnDelete?.setOnClickListener {
                if (item!=null) {
                    item?.let {
                        presenter.delete(it.id!!)
                    }
                }
                hideConstraintAdd()

            }
            setUpSearchView()
            setUpBarcodeScanner()
            //todo
            binding?.tag?.setOnTouchListener { v, event ->
                if (event != null) {
                    if (event.action == MotionEvent.ACTION_UP && event.x < dpToPx(28F)) {
                        clearCategorySelected()
                    }
                }
                true;
            };

            binding?.spinnerCategory?.adapter = mSpinnerCategoryAdapter
            binding?.spinnerCategory?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?, view: View?, position: Int, l: Long
                ) {
                    category = mSpinnerCategoryAdapter?.getItem(position)!!
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    category = mSpinnerCategoryAdapter?.getItem(0)!!
                }
            }

            binding?.media?.attachmentIv?.setOnClickListener {
                launchSelectMedia()
            }
            binding?.media?.attachmentUploadTv?.setOnClickListener {
                launchSelectMedia()
            }
        }

        presenter.categories()
        presenter.data(null, null)
    }

    private fun clearCategorySelected(){
        mCategoryAdapter.clearSelections(mCategoryAdapter.items())
        presenter.data(null, null)
        binding?.tag?.visibility = View.GONE
    }

    private fun setCategorySelected(category: Category) {
        binding?.tag?.visibility = View.VISIBLE
        binding?.tag?.text = category.name
    }

    override fun onCategorySelected(category: Category, position: Int) {
        this.category = category
        setCategorySelected(category)
        this.category = mSpinnerCategoryAdapter?.getItems()?.find { it.id == item?.categoryId }
        presenter.data(category.id, null)

    }

    override fun onItemSelected(item: Item, position: Int) {
        isUpdate = true
        this.item = item
        binding?.text1?.text = getString(R.string.update_master_item)
        showConstraintAdd()
        binding?.textName?.setText(item.name)
        binding?.textSku?.setText(item.sku)
        binding?.textDescription?.setText(item.description)
        category = mSpinnerCategoryAdapter?.getItems()?.find { it.id == item.categoryId }
        binding?.spinnerCategory?.setSelection(mSpinnerCategoryAdapter?.getPosition(category)!!, true)
        if (item.image!=null) {
            binding?.media?.attachmentIv?.loadImage(item.image)
        } else {
            clearThumbnail()
        }
    }

    override fun provideCategories(data: List<Category>) {
        mSpinnerCategoryAdapter?.addItems(data.toMutableList())
        mCategoryAdapter.addItems(data.toMutableList())
        category = data[0]
    }

    override fun showData(items: List<Item>) {
        hideProgress()
        clearThumbnail()
        adapter.addItems(items.toMutableList())
        binding?.itemCount?.text = getString(R.string.item_count_format, "${items.size}")
        hideConstraintAdd()

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
        lp.matchConstraintPercentWidth = 0.7.toFloat()
        mConstrainLayout.setLayoutParams(lp)
    }

    private fun showConstraintAdd() {
        collapseConstraintContent()
        mCategoryAdapter.clearSelections(mCategoryAdapter.items())
        val mConstrainLayout: ConstraintLayout? = binding?.constraintAdd
        val lp: ConstraintLayout.LayoutParams =
            mConstrainLayout?.layoutParams as ConstraintLayout.LayoutParams
        lp.matchConstraintPercentWidth = 0.3.toFloat()
        mConstrainLayout.setLayoutParams(lp)
        if (item!=null) {
            binding?.btnDelete?.visibility = View.VISIBLE
        } else {
            binding?.btnDelete?.visibility = View.GONE
        }
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

    private fun setUpSearchView() {
        binding?.txtQuery?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty() || !newText.isNullOrBlank()) {
                    newText.let {
                        presenter.data(null, it.toString())
                    }
//                hideLastSearch()
                } else {
                    clearCategorySelected()
//                showLastSearch()
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
        binding?.scanBarcode?.setOnClickListener {
            barcodeLauncher.launch(options)
        }
        binding?.scanSku?.setOnClickListener {
            scanSkuLauncher.launch(options)
        }
    }

    private val barcodeLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(requireActivity(), "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            binding?.txtQuery?.setQuery(result.contents, true)
            binding?.txtQuery?.performClick()
            binding?.txtQuery?.isIconified = false
            requireActivity().hideKeyboard()
//            presenter.data(null, result.contents)
//            Toast.makeText(
//                requireActivity(),
//                "Scanned: " + result.contents,
//                Toast.LENGTH_LONG
//            ).show()
        }
    }

    private val scanSkuLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(requireActivity(), "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            binding?.textSku?.setText(result.contents)
//            Toast.makeText(
//                requireActivity(),
//                "Scanned: " + result.contents,
//                Toast.LENGTH_LONG
//            ).show()
        }
    }

    private val selectMediaLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val media: Media? = it.data?.parcelable("media")
                media?.let {m ->
                    binding?.media?.attachmentIv?.loadImage(m.url)
                    mediaUid = m.uid
                }
//                if (!media.isNull()) {
////                    files(medias)
//                    hideLoading()
//                }
            }
        }

    private fun launchSelectMedia() {
        val enterAnim = R.anim.anim_slide_in_left
        val exitAnim = R.anim.anim_slide_out_left
        val options = ActivityOptionsCompat.makeCustomAnimation(requireActivity(), enterAnim, exitAnim)
        val intent = Intent(requireActivity(), SelectMediaActivity::class.java)
        selectMediaLauncher.launch(intent, options)
    }

    private fun clearThumbnail() {
        binding?.media?.attachmentIv?.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_placeholder_camera))
    }
}