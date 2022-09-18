package my.project.mysqlrecycler.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductApiModel {

    @SerializedName("id") @Expose
    var id:Int? = null
    @SerializedName("name") @Expose
    var name: String? = null
    @SerializedName("category") @Expose
    var category:String? = null
    @SerializedName("price") @Expose
    var price: String? = null
}