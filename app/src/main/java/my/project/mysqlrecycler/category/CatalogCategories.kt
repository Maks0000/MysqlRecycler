package my.project.mysqlrecycler.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import my.project.mysqlrecycler.R
import my.project.mysqlrecycler.api.ApiClient
import my.project.mysqlrecycler.api.models.CategoryApiModel
import my.project.mysqlrecycler.databinding.FragmentCatalogCategoriesBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CatalogCategories : Fragment() {

    private var binding: FragmentCatalogCategoriesBinding? = null
    private var categoriesAdapter: CategoriesAdapter? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_catalog_categories, container, false)

        loadCategories()

        binding?.deleteAllCategories?.setOnClickListener(View.OnClickListener {

            clearAllCategories()

        })


        return binding?.root
    }


    private fun loadCategories () {

        val callCategories = ApiClient.instance?.api?.getCategory()
        callCategories?.enqueue(object: Callback<ArrayList<CategoryApiModel>> {
            override fun onResponse(
                call: Call<ArrayList<CategoryApiModel>>,
                response: Response<ArrayList<CategoryApiModel>>
            ) {

                val loadCategories = response.body()

                binding?.recyclerCategories?.layoutManager = LinearLayoutManager(context)

                categoriesAdapter = loadCategories?.let {
                    CategoriesAdapter(
                        it, { idCategory:Int->deleteCategory(idCategory)},
                        {categoriesApiModel:CategoryApiModel->editCategory(categoriesApiModel)})
                }
                binding?.recyclerCategories?.adapter = categoriesAdapter

                Toast.makeText(context, "LOADING", Toast.LENGTH_SHORT).show()


            }

            override fun onFailure(call: Call<ArrayList<CategoryApiModel>>, t: Throwable) {
                Toast.makeText(context, "ERROR! TURN ON THE INTERNET!", Toast.LENGTH_SHORT).show()

            }
        })

    }

    private fun deleteCategory(id:Int) {

        val callDeleteCategory: Call<ResponseBody?>? = ApiClient.instance?.api?.deleteCategory(id)

        callDeleteCategory?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "CATEGORY REMOVED",
                    Toast.LENGTH_SHORT
                ).show()

                loadCategories()
            }



            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(
                    context,
                    "ERROR! TURN ON THE INTERNET!",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })

    }


    private fun clearAllCategories() {

        val callClearAllCat: Call<ResponseBody?>? = ApiClient.instance?.api?.clearCategories()

        callClearAllCat?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "CATEGORIES REMOVED",
                    Toast.LENGTH_SHORT
                ).show()

                loadCategories()
            }



            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(
                    context,
                    "ERROR! TURN ON THE INTERNET!",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })

    }

    private fun editCategory(categoriesApiModel:CategoryApiModel) {
        val panelCategory = PanelEditCategory()
        val parameters = Bundle()
        parameters.putString("idCategory", categoriesApiModel.id.toString())
        parameters.putString("nameCategory", categoriesApiModel.name)
        panelCategory.arguments = parameters

        panelCategory.show((context as FragmentActivity).supportFragmentManager, "editCategory")
    }

}