package my.project.mysqlrecycler.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import my.project.mysqlrecycler.R
import my.project.mysqlrecycler.api.models.ProductApiModel
import my.project.mysqlrecycler.databinding.ProductItemBinding


class ProductsAdapter(private val productsList : ArrayList<ProductApiModel>,
                      private val deleteProduct:(Int)->Unit,
                      private val editProduct:(ProductApiModel)->Unit): RecyclerView.Adapter<ProductsAdapter.ProductsHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ProductItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.product_item, parent, false)
        return ProductsHolder(binding)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: ProductsHolder, position: Int) {
        holder.bind(productsList[position], deleteProduct, editProduct)
    }

    class ProductsHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            products: ProductApiModel, deleteProduct: (Int) -> Unit, editProduct: (ProductApiModel) -> Unit
        ) {

            val idProduct = products.id

            binding.idProduct.text = idProduct.toString()

            binding.nameProduct.text = products.name
            binding.categoryProduct.text = products.category
            binding.priceProduct.text = products.price.toString()


            binding.editProduct.setOnClickListener(View.OnClickListener {
                editProduct(products)
            })

            binding.deleteProduct.setOnClickListener(View.OnClickListener {
                deleteProduct(idProduct!!)
            })
        }

    }

}