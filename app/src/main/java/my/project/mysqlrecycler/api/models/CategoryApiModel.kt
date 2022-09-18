package my.project.mysqlrecycler.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CategoryApiModel {

    @SerializedName("id") @Expose
    var id:Int? = null
    @SerializedName("name") @Expose
    var name: String? = null 
}