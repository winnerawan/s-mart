package id.co.sherly.mart.utils.view

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import id.co.sherly.mart.R
import id.co.sherly.mart.databinding.SideMenuBinding

class SideMenuView : LinearLayout {
    companion object {
        const val HOME_MENU = 11
        const val SEARCH_MENU = 12
        const val SETTING_MENU = 13


        const val TAG_SEARCH = "SEARCH"
        const val TAG_HOME = "HOME"
        const val TAG_SETTINGS = "SETTINGS"


        fun animateView(view: View, valueAnimator: ValueAnimator) {
            valueAnimator.addUpdateListener { animation ->
                view.layoutParams.width = animation.animatedValue as Int
                view.requestLayout()
            }

            valueAnimator.interpolator = AccelerateInterpolator()
            valueAnimator.duration = 100
            valueAnimator.start()
        }
    }

    private var _binding: SideMenuBinding? = null
    private val binding get() = _binding!!

    private var menuItemClick: MenuItemClickListener? = null
    private var menuTitles = emptyArray<String>()
    private var currentSelected: Int = HOME_MENU
    private var leftMenusShown: Boolean = false

    private var mContext: Context? = null

    interface MenuItemClickListener {
        fun menuItemClick(menuId: Int)
    }

    constructor(context: Context) : super(context) {
        setupMenuUI(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupMenuUI(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setupMenuUI(context)
    }

    private fun setupMenuUI(context: Context) {
        this.mContext = context
        this.menuItemClick = context as MenuItemClickListener
        menuTitles = resources.getStringArray(R.array.leftMenu)
        orientation = VERTICAL
        gravity = Gravity.CENTER
        setBackgroundColor(ContextCompat.getColor(this.context, R.color.blue_accent))
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        inflater.inflate(R.layout.side_menu, this, true)
        _binding = SideMenuBinding.inflate(inflater, this)
        binding.cllSearchView.setOnClickListener {
            menuItemClick!!.menuItemClick(SEARCH_MENU)
        }

        binding.cllHomeView.setOnClickListener {
            menuItemClick!!.menuItemClick(HOME_MENU)
        }

        binding.cllSettingsView.setOnClickListener {
            menuItemClick!!.menuItemClick(SETTING_MENU)
        }
    }

    private fun focusCurrentSelectedMenu() {
        when (currentSelected) {
            HOME_MENU -> {
                binding.cllHomeView.apply {
                    requestFocus()
                }
            }

            SEARCH_MENU -> {
                binding.cllSearchView.apply {
                    requestFocus()
                }
            }

            SETTING_MENU -> {
                binding.cllSettingsView.apply {
                    requestFocus()
                }
            }
        }
    }

    fun setCurrentSelected(currentSelected: Int) {
        this.currentSelected = currentSelected
    }

    fun getCurrentSelected(): Int {
        return currentSelected
    }

    private fun highlightCurrentSelectedMenu() {
        when (currentSelected) {
            HOME_MENU -> {
                binding.cllHomeView.setBackgroundColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.blue_accent
                    )
                )
                binding.civHome.imageTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this.context,
                            R.color.blue_accent_dark
                        )
                    )
            }

            SEARCH_MENU -> {
                binding.cllSearchView.setBackgroundColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.blue_accent
                    )
                )
                binding.civSearch.imageTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this.context,
                            R.color.blue_accent_dark
                        )
                    )
            }

            SETTING_MENU -> {
                binding.cllSettingsView.setBackgroundColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.blue_accent
                    )
                )
                binding.civSettings.imageTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this.context,
                            R.color.blue_accent_dark
                        )
                    )
            }
        }
    }

    private fun resetMenusText() {
        binding.txtHomeMenuLabel.apply {
            text = ""
        }

        binding.txtSearchMenuLabel.apply {
            text = ""
        }

        binding.txtSettingsMenuLabel.apply {
            text = ""
        }
    }

    private fun setMenusText() {

        binding.txtHomeMenuLabel.apply {
            text = menuTitles[0]
        }

        binding.txtSearchMenuLabel.apply {
            text = menuTitles[1]
        }

        binding.txtSettingsMenuLabel.apply {
            text = menuTitles[2]
        }
    }

    fun setupMenuExpandedUI() {
        Handler(Looper.getMainLooper()).postDelayed({
            setMenusText()
            changeMenuFocusStatus(true)
            focusCurrentSelectedMenu()
        }, 100)
    }

    fun setupMenuClosedUI() {
        resetMenusText()
        changeMenuFocusStatus(false)
    }

    private fun changeMenuFocusStatus(status: Boolean) {
        val count = childCount
        for (i in 0 until count) {
            val childView = getChildAt(i)
            childView.apply {
                isFocusable = status
                isFocusableInTouchMode = status
                if (!status) {
                    clearFocus()
                    highlightCurrentSelectedMenu()
                } else {
                    setBackgroundColor(0)
                    background =
                        ContextCompat.getDrawable(this.context, R.drawable.drawable_menu_hover)
                    changeMenuColor()
                }
            }
        }
    }

    private fun changeMenuColor() {
        when (currentSelected) {
            HOME_MENU -> {
                binding.civHome?.imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this.context,
                        R.color.white
                    )
                )
            }

            SEARCH_MENU -> {
                binding.civSearch?.imageTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this.context,
                            R.color.white
                        )
                    )
            }

            SETTING_MENU -> {
                binding.civSettings?.imageTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this.context,
                            R.color.white
                        )
                    )
            }
        }
    }

    fun setupDefaultMenu(menuId: Int) {
        currentSelected = menuId
        leftMenusShown = false
        changeMenuFocusStatus(false)
    }

    fun changeFocusTo(menuId: Int) {
        resetMenus()
        currentSelected = menuId
        highlightCurrentSelectedMenu()
    }

    private fun resetMenus() {
        val color = ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.white
            )
        )
        binding.civSearch?.imageTintList = color
        binding.cllSearchView.setBackgroundColor(0)

        binding.civHome?.imageTintList = color
        binding.cllHomeView.setBackgroundColor(0)

        binding.civSettings?.imageTintList = color
        binding.cllSettingsView.setBackgroundColor(0)
    }
}