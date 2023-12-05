package com.example.razorpaycustomerapp

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.razorpaycustomerapp.databinding.ActivityCustomerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Base64
//import android.widget.Toast
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.razorpaycustomerapp.databinding.ActivityCustomerBinding
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory


class CustomerActivity : AppCompatActivity(),InterfaceForPostion {
    lateinit var myAdapter: MyAdapter
    lateinit var listItem : ArrayList<Item>
    lateinit var binding : ActivityCustomerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager= LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        listItem = arrayListOf()
        val retrofit1= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.razorpay.com/v1/")
            .build()
        val interfaceApi1 = retrofit1.create(InterfaceApi::class.java)
        val header = "Basic " + Base64.encodeToString( "rzp_test_9JZiWARukAUmQR:25sPrA3Ppbb4Gn6nQO9OAj1s".toByteArray(), Base64.NO_WRAP)
        val hasMap = HashMap<String,Any>()
        hasMap["Authorization"]=header
        interfaceApi1.getData(hasMap).enqueue(object : Callback<FetchDataModel?> {
            override fun onResponse(
                call: Call<FetchDataModel?>,
                response: Response<FetchDataModel?>
            ) {
                myAdapter =MyAdapter(baseContext,response.body()!!.items,this@CustomerActivity)
                myAdapter.notifyDataSetChanged()
                binding.recyclerView.adapter=myAdapter
            }

            override fun onFailure(call: Call<FetchDataModel?>, t: Throwable) {

            }
        })

    }

    override fun update(id: String, position: Int, name: String, email: String, contact: String) {
        val intent = Intent(this@CustomerActivity,UpdateActivity::class.java)
        intent.putExtra("id",id)
        intent.putExtra("name",name)
        intent.putExtra("email",email)
        intent.putExtra("contact",contact)
        startActivity(intent)
    }

    override fun deleteData(id: String, position: Int) {
        val header = "Basic " + Base64.encodeToString( "rzp_test_2XAQEnCY9O13rx:MpEmnhqgceL3PGIb6QZMvYgY".toByteArray(), Base64.NO_WRAP)
        val hasMap = HashMap<String,String?>()
        hasMap["Authorization"]=header

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.razorpay.com/v1/")
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson converter for JSON parsing, you can use other converters too
            .build()

        val interfaceApi = retrofit.create(InterfaceApi::class.java)
        val myCall = interfaceApi.deleteData(hasMap,id)
        myCall.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.isSuccessful){
                    Toast.makeText(this@CustomerActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                    myAdapter.notifyDataSetChanged()
                    myAdapter.notifyItemChanged(position)
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {

            }
        })
    }


}