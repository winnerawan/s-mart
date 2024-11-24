package id.co.sherly.mart.ui.media.select

import android.content.Intent
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import id.co.sherly.mart.databinding.ActivitySelectMediaBinding
import id.co.sherly.mart.ui.base.view.BaseActivity
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Media
import id.co.sherly.mart.ui.media.ItemMediaAdapter
import id.co.sherly.mart.utils.ext.loadImage
import id.co.sherly.mart.utils.ext.parcelable
import javax.inject.Inject

@AndroidEntryPoint
class SelectMediaActivity : BaseActivity<ActivitySelectMediaBinding>(), SelectMediaContract.View, ItemMediaAdapter.Callback {


    private var gridLayout1: GridLayoutManager? = null
    private var gridLayout2: GridLayoutManager? = null

    @Inject
    lateinit var presenter: SelectMediaPresenter

    @Inject
    lateinit var adapter: ItemMediaAdapter

    private var media: Media? = null

    override fun createBinding(): ActivitySelectMediaBinding = ActivitySelectMediaBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            gridLayout1 = GridLayoutManager(this@SelectMediaActivity, 4, GridLayoutManager.VERTICAL, false)
            gridLayout2 = GridLayoutManager(this@SelectMediaActivity, 3, GridLayoutManager.VERTICAL, false)
            (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            binding.recyclerView.adapter = adapter
            adapter.callback = this@SelectMediaActivity
            expandConstraintContent()
            binding.btnAdd.setOnClickListener {
                showConstraintAdd()
//                binding.textName.text.clear()
            }
            binding.iconClose.setOnClickListener {
                hideConstraintAdd()
                adapter.clearSelections(adapter.items())
            }

            binding.btnSave.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable("media", media)
                finishResult(bundle)
            }

            binding.iconBack.setOnClickListener { finishActivity() }
        }

        onBackPressedDispatcher.addCallback(this) {
            finishActivity()
        }
        presenter.data()

    }


    private fun expandConstraintContent() {
        binding.recyclerView.layoutManager = gridLayout1
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
        if (media!=null) {
            binding.btnDelete.visibility = View.VISIBLE
        } else {
            binding.btnDelete.visibility = View.GONE
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
        binding.btnDelete.visibility = View.GONE
        adapter.clearSelections(adapter.items())
    }

    override fun onMediaSelected(media: Media, position: Int) {
        this.media = media
        binding.imageView.loadImage(media.url)
        binding.name.text = media.name
        showConstraintAdd()
    }

    override fun showData(medias: List<Media>) {
        hideProgress()
        adapter.addItems(medias.toMutableList())
        hideConstraintAdd()
    }
    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun finishResult(data: Intent?) {
        setResult(RESULT_OK, data)
        finish()
    }
    private fun finishResult(data: Bundle?) {
        val intent = Intent()
        if (data != null) {
            intent.putExtras(data)
        }
        finishResult(intent)
    }
    private fun finishActivity() {
        finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
    }
}