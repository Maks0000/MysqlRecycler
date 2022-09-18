package my.project.mysqlrecycler.api

import my.project.mysqlrecycler.api.models.CategoryApiModel
import my.project.mysqlrecycler.api.models.ProductApiModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("insertCategory.php")
    fun insertCategory(
        @Field("name")name: String?
    ): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("insertProduct.php")
    fun insertProduct(
        @Field("name") name: String?,
        @Field("category") category: String?,
        @Field("price") price: String?
    ): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("updateCategory.php")
    fun updateCategory(
        @Field("id") id: Int?,
        @Field("name") name: String?
    ): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("updateProduct.php")
    fun updateProduct(
        @Field("id") id: Int,
        @Field("name") name: String?,
        @Field("category") category: String?,
        @Field("price") price: String?
    ): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("deleteCategory.php")
    fun deleteCategory(
        @Field("id") id :Int?
    ): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("deleteProduct.php")
    fun deleteProduct(
        @Field("id") id: Int
    ): Call<ResponseBody?>?

    @DELETE("clearCategories.php")
    fun clearCategories(): Call<ResponseBody?>?

    @DELETE("clearProducts.php")
    fun clearProducts(): Call<ResponseBody?>?

    @GET("getCategory.php")
    fun getCategory(): Call<ArrayList<CategoryApiModel>>

    @GET("getProduct.php")
    fun getProduct(): Call<ArrayList<ProductApiModel>>

    @GET("getProductFilter.php")
    fun getProductFilter(@Query("category")category: String, @Query("price")price: String):
            Call<ArrayList<ProductApiModel>>
}