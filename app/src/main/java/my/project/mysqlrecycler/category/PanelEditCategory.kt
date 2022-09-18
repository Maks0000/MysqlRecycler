package my.project.mysqlrecycler.category

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import my.project.mysqlrecycler.R
import my.project.mysqlrecycler.api.ApiClient
import my.project.mysqlrecycler.databinding.FragmentPanelEditCategoryBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response


class PanelEditCategory : BottomSheetDialogFragment(), View.OnKeyListener {

    private var binding: FragmentPanelEditCategoryBinding? = null
    private var idCategory: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_panel_edit_category,container,false)

        idCategory = arguments?.getString("idCategory")?.toInt()
        binding?.editCategory?.setText(arguments?.getString("nameCategory").toString())
        binding?.editCategory?.setOnKeyListener(this)

        return binding?.root
    }

    override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
        when (view.id)  {

            R.id.editCategory -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    updateCategory(binding?.editCategory?.text?.toString()!!)

                    return true
                }
            }

        }
        return false
    }

    private fun updateCategory(name: String) {
        val callUpdateCategory = ApiClient.instance?.api?.updateCategory(idCategory.toString().toInt(), name)

        callUpdateCategory?.enqueue(object : retrofit2.Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "CATEGORY UPDATED",
                    Toast.LENGTH_SHORT
                ).show()
                loadScreen()

            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(
                    context,
                    "CHECK CONNECTION TO INTERNET",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

    }

    private fun loadScreen() {
        binding?.editCategory?.setText("")

        dismiss()

        (context as FragmentActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.mycontent, CatalogCategories()).commit()
    }
}