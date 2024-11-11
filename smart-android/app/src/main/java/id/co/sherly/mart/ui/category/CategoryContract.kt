package id.co.sherly.mart.ui.category

import id.co.sherly.mart.data.model.Category

interface CategoryContract {

    interface View {
        fun showCategories(categories: List<Category>)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun fetchCategories()
        fun createCategory(name: String)
        fun updateCategory(id: Int, name: String)
        fun deleteCategory(id: Int)
    }
}