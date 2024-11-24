package id.co.sherly.mart.ui.media

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.Glide
import com.github.drjacky.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.Media
import id.co.sherly.mart.databinding.FragmentMediaBinding
import id.co.sherly.mart.ui.base.view.BaseFragment
import id.co.sherly.mart.utils.FileUtils
import id.co.sherly.mart.utils.GridSpacingItemDecoration
import id.co.sherly.mart.utils.ext.hideKeyboard
import id.co.sherly.mart.utils.ext.loadImage
import net.gotev.uploadservice.network.ServerResponse
import javax.inject.Inject


@AndroidEntryPoint
class MediaFragment : BaseFragment<FragmentMediaBinding>(), MediaContract.View, ItemMediaAdapter.Callback {

    @Inject
    lateinit var presenter: MediaPresenter

    private var gridLayout1: GridLayoutManager? = null
    private var gridLayout2: GridLayoutManager? = null

    @Inject
    lateinit var adapter: ItemMediaAdapter

    private var media: Media? = null
    private var mediaUri: Uri? = null
    private var imageUri: String? = null

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMediaBinding = FragmentMediaBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            gridLayout1 = GridLayoutManager(requireActivity(), 4, GridLayoutManager.VERTICAL, false)
            gridLayout2 = GridLayoutManager(requireActivity(), 3, GridLayoutManager.VERTICAL, false)
            (binding?.recyclerView?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            binding?.recyclerView?.adapter = adapter
            adapter.callback = this@MediaFragment
            expandConstraintContent()
            binding?.btnAdd?.setOnClickListener {
                showConstraintAdd()
                clearThumbnail()
//                binding?.textName?.text?.clear()
            }
            binding?.iconClose?.setOnClickListener {
                hideConstraintAdd()
                adapter.clearSelections(adapter.items())
            }

            binding?.btnSave?.setOnClickListener {
                imageUri?.let {
                    presenter.create(
                        requireActivity(),
                        it,
                        this@MediaFragment
                    )
                }
//                presenter.create(requireActivity(), )
            }
            binding?.btnDelete?.setOnClickListener {
                if (media!=null) {
                    media?.let {
                        presenter.delete(it.uid!!)
                    }
                }
            }
            binding?.upload?.attachmentIv?.setOnClickListener {
                launchGallery()
            }
            binding?.upload?.attachmentUploadTv?.setOnClickListener {
                launchGallery()
            }
        }
    }

    private fun launchGallery() {
        galleryLauncher.launch(
            ImagePicker.with(requireActivity())
                .galleryOnly()
                .createIntent()
        )
    }

    override fun onMediaSelected(category: Media, position: Int) {
    }

    override fun showData(medias: List<Media>) {
        adapter.addItems(medias.toMutableList())
        hideConstraintAdd()
        hideProgress()
        binding?.imageCount?.text = getString(R.string.image_format, "${medias.size}")
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
        if (media!=null) {
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

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                // Use the uri to load the image
                // Only if you are not using crop feature:
                uri.let { galleryUri ->
                    mediaUri = galleryUri
                    binding?.upload?.attachmentIv?.let { view ->
                        Glide.with(this).load(mediaUri)
                            .into(view)
                    }
                    imageUri = FileUtils(requireActivity()).getPath(mediaUri)
//                    contentResolver.takePersistableUriPermission(
//                        uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    )
                }
                Log.e("DOC", "uri: $uri  mediaUri: $mediaUri   imageUri: $imageUri")

            }
        }

    override fun onImageUploaded(serverResponse: ServerResponse) {

    }

    private fun clearThumbnail() {
        binding?.upload?.attachmentIv?.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_placeholder_camera))
    }
}