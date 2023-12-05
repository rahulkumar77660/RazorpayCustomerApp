package com.example.razorpaycustomerapp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface InterfaceApi {

    @POST("customers")
    fun postData(@Body postRequest : PostDataModel,@HeaderMap reqHeader : HashMap<String,Any>) : Call<FetchDataModel>

    @GET("customers")
    fun getData(@HeaderMap reqHeader: HashMap<String, Any>) : Call<FetchDataModel>

    @PUT("customers/{cust_id}")
    fun putData(@HeaderMap header: Map<String, String?>, @Path("cust_id") customerId: String, @Body putRequest: DataModelForMe): Call<FetchDataModel>

    @DELETE("customers/{cust_id}")
    fun deleteData(@HeaderMap reqHeader : Map<String,String?>,@Path("cust_id") customerId: String ) : Call<Void>
}