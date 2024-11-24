package id.co.sherly.mart.ui.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.data.model.ItemStock
import id.co.sherly.mart.data.pref.UserPreferences
import id.co.sherly.mart.databinding.FragmentStockBinding
import id.co.sherly.mart.ui.base.view.BaseFragment
import id.co.sherly.mart.ui.item.CategoryAdapter
import javax.inject.Inject

@AndroidEntryPoint
class StockFragment : BaseFragment<FragmentStockBinding>(), StockContract.View {

    private var category: Category? = null
    @Inject
    lateinit var mCategoryAdapter: CategoryAdapter
    @Inject
    lateinit var presenter: StockPresenter

    @Inject
    lateinit var adapter: ItemStockAdapter

    @Inject
    lateinit var preferences: UserPreferences
    private var gridLayout1: GridLayoutManager? = null
    private var gridLayout2: GridLayoutManager? = null

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentStockBinding = FragmentStockBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            gridLayout1 = GridLayoutManager(requireActivity(), preferences.itemStockGrid, GridLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = gridLayout1
            recyclerView.adapter = adapter
        }
        presenter.categories()
        presenter.data(null, null)
    }

    override fun showData(data: List<ItemStock>) {
        hideProgress()
        adapter.addItems(data.toMutableList())
    }

    override fun provideCategories(data: List<Category>) {
        mCategoryAdapter.addItems(data.toMutableList())
        category = data[0]
    }

    override fun showProgress() {
        binding?.progress?.visibility = View.VISIBLE
        binding?.recyclerView?.visibility = View.GONE
    }

    override fun hideProgress() {
        binding?.progress?.visibility = View.GONE
        binding?.recyclerView?.visibility = View.VISIBLE
    }
}