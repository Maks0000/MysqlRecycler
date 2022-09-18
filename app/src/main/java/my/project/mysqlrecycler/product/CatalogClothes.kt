package my.project.mysqlrecycler.product

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
import my.project.mysqlrecycler.api.models.ProductApiModel
import my.project.mysqlrecycler.databinding.FragmentCatalogClothesBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CatalogClothes : Fragment() {
    private var binding: FragmentCatalogClothesBinding? = null
    private var productsAdapter: ProductsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_catalog_clothes, container, false)

        loadClothes()

        return binding?.root
    }


    private fun loadClothes () {

        val callGetClothes = ApiClient.instance?.api?.getProductFilter(getString(R.string.catalogClothes),
            getString(R.string.priceFilter))
        callGetClothes?.enqueue(object: Callback<ArrayList<ProductApiModel>> {
            override fun onResponse(
                call: Call<ArrayList<ProductApiModel>>,
                response: Response<ArrayList<ProductApiModel>>
            ) {

                val loadProducts = response.body()

                binding?.recyclerClothes?.layoutManager = LinearLayoutManager(context)
                productsAdapter = loadProducts?.let {
                    ProductsAdapter(
                        it, { idProduct:Int->deleteProduct(idProduct)},
                        {productsApiModel:ProductApiModel->editProduct(productsApiModel)})
                }
                binding?.recyclerClothes?.adapter = productsAdapter

                Toast.makeText(context, "LOADING", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "It will show products from the category 'clothes' equal to '5000' price", Toast.LENGTH_LONG).show()



            }

            override fun onFailure(call: Call<ArrayList<ProductApiModel>>, t: Throwable) {
                Toast.makeText(context, "ERROR! TURN ON THE INTERNET!", Toast.LENGTH_SHORT).show()

            }
        })

    }


    private fun deleteProduct(id:Int) {

        val callDeleteProduct: Call<ResponseBody?>? = ApiClient.instance?.api?.deleteProduct(id)

        callDeleteProduct?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "ITEM REMOVED",
                    Toast.LENGTH_SHORT
                ).show()

                loadClothes()
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

    private fun editProduct(productsApiModel: ProductApiModel) {
        val panelEditProduct = PanelEditProduct()
        val parameters = Bundle()
        parameters.putString("idProduct", productsApiModel.id.toString())
        parameters.putString("nameProduct", productsApiModel.name)
        parameters.putString("categoryProduct", productsApiModel.category)
        parameters.putString("priceProduct", productsApiModel.price)
        panelEditProduct.arguments = parameters

        panelEditProduct.show((context as FragmentActivity).supportFragmentManager, "editProduct")
    }

}