package id.co.sherly.mart.ui.purchase.imports

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.gson.Gson
import com.nareshchocha.filepickerlibrary.models.PopUpConfig
import com.nareshchocha.filepickerlibrary.models.PopUpType
import com.nareshchocha.filepickerlibrary.ui.FilePicker
import com.nareshchocha.filepickerlibrary.utilities.appConst.Const
import dagger.hilt.android.AndroidEntryPoint
import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.DocumentImport
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.data.model.ItemImportPurchase
import id.co.sherly.mart.databinding.ActivityPurchaseImportBinding
import id.co.sherly.mart.ui.base.view.BaseActivity
import id.co.sherly.mart.utils.FileUtils
import id.co.sherly.mart.utils.PathUtil
import net.gotev.uploadservice.network.ServerResponse
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class PurchaseImportActivity : BaseActivity<ActivityPurchaseImportBinding>(), PurchaseImportContract.View, ItemDocumentAdapter.Callback {

    @Inject
    lateinit var presenter: PurchaseImportPresenter

    private var gridLayout1: GridLayoutManager? = null
    private var gridLayout2: GridLayoutManager? = null
    private var items: List<Item>? = null
    private var documentUri: String? = null

    @Inject
    lateinit var adapter: ItemDocumentAdapter

    override fun createBinding(): ActivityPurchaseImportBinding = ActivityPurchaseImportBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {

            gridLayout1 = GridLayoutManager(this@PurchaseImportActivity, 4, GridLayoutManager.VERTICAL, false)
            gridLayout2 = GridLayoutManager(this@PurchaseImportActivity, 3, GridLayoutManager.VERTICAL, false)
            (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            binding.recyclerView.adapter = adapter
            adapter.callback = this@PurchaseImportActivity

            expandConstraintContent()
            binding.btnAdd.setOnClickListener {
                showConstraintAdd()
            }
            binding.iconClose.setOnClickListener {
                hideConstraintAdd()
            }
            iconBack.setOnClickListener {
                finishActivity()
            }
            btnExport.setOnClickListener {
                if (!items.isNullOrEmpty()) {
                    presenter.exportXls()
                }
            }
            btnSave.setOnClickListener {
                documentUri?.let {
                    presenter.create(
                        this@PurchaseImportActivity, it, lifeCycleOwner = this@PurchaseImportActivity
                    )
                }
            }
            upload.attachmentUploadTv.setOnClickListener {
                launchDocumentPicker()
            }

        }
        presenter.items()
        presenter.data()

    }

    override fun onImport(documentImport: DocumentImport, position: Int) {
        presenter.import(documentImport.id!!, 1)
    }

    override fun successImport() {
        finishResult(null)
    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun expandConstraintContent() {
        binding.recyclerView.layoutManager = gridLayout1
        val mConstrainLayout: ConstraintLayout = binding.constraintContent
        val lp: ConstraintLayout.LayoutParams =
            mConstrainLayout.layoutParams as ConstraintLayout.LayoutParams
        lp.matchConstraintPercentWidth = 1.toFloat()
        mConstrainLayout.setLayoutParams(lp)
    }

    private fun collapseConstraintContent() {
        binding.recyclerView.layoutManager = gridLayout2
        val mConstrainLayout: ConstraintLayout = binding.constraintContent
        val lp: ConstraintLayout.LayoutParams =
            mConstrainLayout.layoutParams as ConstraintLayout.LayoutParams
        lp.matchConstraintPercentWidth = 0.75.toFloat()
        mConstrainLayout.setLayoutParams(lp)
    }

    private fun showConstraintAdd() {
        collapseConstraintContent()
        val mConstrainLayout: ConstraintLayout = binding.constraintAdd
        val lp: ConstraintLayout.LayoutParams =
            mConstrainLayout.layoutParams as ConstraintLayout.LayoutParams
        lp.matchConstraintPercentWidth = 0.25.toFloat()
        mConstrainLayout.setLayoutParams(lp)

    }

    private fun hideConstraintAdd() {
        expandConstraintContent()
        val mConstrainLayout: ConstraintLayout = binding.constraintAdd
        val lp: ConstraintLayout.LayoutParams =
            mConstrainLayout.layoutParams as ConstraintLayout.LayoutParams
        lp.matchConstraintPercentWidth = 0.toFloat()
        mConstrainLayout.setLayoutParams(lp)
        binding.btnDelete.visibility = View.GONE
    }

    override fun provideItems(items: List<Item>) {
        this.items = items
    }

    private fun finishResultOk(data: Intent?) {
        setResult(RESULT_OK, data)
        finishActivity()
    }
    private fun finishResult(data: Bundle?) {
        val intent = Intent()
        if (data != null) {
            intent.putExtras(data)
        }
        finishResultOk(intent)
    }
    private fun finishActivity() {
        finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
    }

    private val documentPickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                // Use the uri to load the image
                val uri = it.data?.data
                // Use the file path to set image or upload
                val filePath= it.data?.getStringExtra(Const.BundleExtras.FILE_PATH)
                documentUri = filePath//FileUtils(this).getPath(uri)
                Log.e("DOC", "fp: $filePath  uri: $uri.  docUri: $documentUri")
                binding.upload.name.text = filePath?.substringAfterLast("/")
                //...
//
//                // for Multiple picks
//                // first item
//                val first = it.data?.data!!
//                // other items
//                val  clipData = it.data?.clipData
//                // Multiple file paths list
//                val filePaths = result.data?.getStringArrayListExtra(Const.BundleExtras.FILE_PATH_LIST)
                //...
            }
        }

    private fun optionsLauncher(): ActivityOptionsCompat {
        val enterAnim = R.anim.anim_slide_in_left
        val exitAnim = R.anim.anim_slide_out_left
        val options = ActivityOptionsCompat.makeCustomAnimation(this, enterAnim, exitAnim)
        return options
    }
    private fun launchDocumentPicker() {
        val popUpConfig = PopUpConfig(
            chooserTitle = getString(R.string.select_document),
            // layoutId = 0, custom layout
            mPopUpType = PopUpType.BOTTOM_SHEET,// PopUpType.BOTTOM_SHEET Or PopUpType.DIALOG
            mOrientation = RecyclerView.VERTICAL // RecyclerView.VERTICAL or RecyclerView.HORIZONTAL
        )
        val intent = FilePicker.Builder(this)
            .setPopUpConfig(popUpConfig)
            .addPickDocumentFile()
            .addImageCapture()
//            .addVideoCapture()
//            .addPickMedia()
            .build()
        documentPickerLauncher.launch(intent, optionsLauncher())
    }


    override fun documentPath(name: String): String {
        val values = ContentValues()
        values.put(MediaStore.Downloads.DISPLAY_NAME, "$name")
        values.put(MediaStore.Downloads.TITLE, name)
//        values.put(MediaStore.Downloads.MIME_TYPE, "video/$ext")

        var uri: Uri? = null
        uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            contentResolver
                ?.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
        } else {
            val path =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separatorChar + "$name"
            Uri.fromFile(File(path))
        }

        val p = PathUtil.getPath(this, uri)
        return p
    }

    override fun exported() {
        Toast.makeText(this, R.string._exported, Toast.LENGTH_SHORT).show()
    }

    override fun showData(data: List<DocumentImport>?) {
        adapter.addItems(data?.toMutableList()!!)
        hideProgress()
    }

    override fun onImageUploaded(serverResponse: ServerResponse) {
        hideProgress()
        hideConstraintAdd()
    }
}