package id.co.sherly.mart.ui.supplier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Supplier
import id.co.sherly.mart.databinding.FragmentSupplierBinding
import id.co.sherly.mart.ui.base.view.BaseFragment
import id.co.sherly.mart.utils.GridSpacingItemDecoration
import id.co.sherly.mart.utils.ext.hideKeyboard
import javax.inject.Inject


@AndroidEntryPoint
class SupplierFragment : BaseFragment<FragmentSupplierBinding>(), SupplierContract.View, ItemSupplierAdapter.Callback {

    @Inject
    lateinit var presenter: SupplierPresenter

    private var gridLayout1: GridLayoutManager? = null
    private var gridLayout2: GridLayoutManager? = null

    @Inject
    lateinit var adapter: ItemSupplierAdapter

    private var isUpdate = false
    private var category: Supplier? = null

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSupplierBinding = FragmentSupplierBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            gridLayout1 = GridLayoutManager(requireActivity(), 3, GridLayoutManager.VERTICAL, false)
            gridLayout2 = GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
            (binding?.recyclerView?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            binding?.recyclerView?.adapter = adapter
            adapter.callback = this@SupplierFragment
            expandConstraintContent()
            binding?.btnAdd?.setOnClickListener {
                isUpdate = false
                binding?.text1?.text = getString(R.string.add_new_supplier)
                showConstraintAdd()
                binding?.textName?.text?.clear()
                adapter.clearSelections(adapter.items())
                requireActivity()?.hideKeyboard()
            }
            binding?.iconClose?.setOnClickListener {
                hideConstraintAdd()
                adapter.clearSelections(adapter.items())
                requireActivity()?.hideKeyboard()
            }

            binding?.btnSave?.setOnClickListener {
                val name = binding?.textName?.text.toString()
                val address = binding?.textAddress?.text.toString()
                val phone = binding?.textPhone?.text.toString()
                if (isUpdate) {
                    category?.let {
                        presenter.update(it.id!!, name, address, phone)
                        requireActivity()?.hideKeyboard()
                    }
                    return@setOnClickListener
                }
                presenter.create(name, address, phone)
                requireActivity()?.hideKeyboard()
            }
            binding?.btnDelete?.setOnClickListener {
                if (category!=null) {
                    category?.let {
                        presenter.delete(it.id!!)
                    }
                }
            }
        }
    }

    override fun onSupplierSelected(category: Supplier, position: Int) {
        isUpdate = true
        this.category = category
        binding?.text1?.text = getString(R.string.update_supplier)
        showConstraintAdd()
        binding?.textName?.setText(category.name)
    }

    override fun showData(customers: List<Supplier>) {
        hideProgress()
        adapter.addItems(customers.toMutableList())
        binding?.categoryCount?.text = getString(R.string.customer_count_format, "${customers.size}")
        hideConstraintAdd()
//        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

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
        val mConstrainLayout: ConstraintLayout? = binding?.constraintAdd
        val lp: ConstraintLayout.LayoutParams =
            mConstrainLayout?.layoutParams as ConstraintLayout.LayoutParams
        lp.matchConstraintPercentWidth = 0.3.toFloat()
        mConstrainLayout.setLayoutParams(lp)
        if (category!=null) {
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
}