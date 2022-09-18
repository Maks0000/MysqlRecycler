package my.project.mysqlrecycler.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import my.project.mysqlrecycler.R
import my.project.mysqlrecycler.api.models.CategoryApiModel
import my.project.mysqlrecycler.databinding.CategoryItemBinding

class CategoriesAdapter( private val categoriesList: ArrayList<CategoryApiModel>,
                         private val deleteCategory:(Int)->Unit,
                         private val editCategory:(CategoryApiModel)->Unit): RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CategoryItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.category_item, parent, false)
        return CategoriesHolder(binding)

    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {

        holder.bind(categoriesList[position],deleteCategory,editCategory)
    }

    class CategoriesHolder(val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(
            categories: CategoryApiModel, deleteCategory: (Int) -> Unit,
            editCategory: (CategoryApiModel) -> Unit
        ) {
            val idCategory  = categories.id

            binding.idCategory.text = idCategory.toString()

            binding.nameCategory.text = categories.name

            binding.editCategory.setOnClickListener(View.OnClickListener {
                editCategory(categories)
            })

            binding.deleteCategory.setOnClickListener(View.OnClickListener {
                deleteCategory(idCategory!!)
            })
        }
    }

}