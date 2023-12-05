package com.example.razorpaycustomerapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.razorpaycustomerapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//import android.annotation.SuppressLint
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Base64
//import android.widget.Toast
//import com.example.razorpaycustomerapp.databinding.ActivityMainBinding
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
//import retrofit2.converter.gson.GsonConverterFactory



class MainActivity<Item> : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    lateinit var myAdapter: MyAdapter
    lateinit var list: List<Item>
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.button.setOnClickListener {
            var name = binding.editTextText.text.toString()
            var email = binding.editTextTextEmailAddress.text.toString()
            var contact = binding.editTextNumber.text.toString()
            val header = "Basic " + Base64.encodeToString( "rzp_test_9JZiWARukAUmQR:25sPrA3Ppbb4Gn6nQO9OAj1s".toByteArray(), Base64.NO_WRAP)
            val hasMap = HashMap<String,Any>()
            hasMap["Authorization"]=header
            startActivity(Intent(this@MainActivity,CustomerActivity::class.java))

            val myData = PostDataModel("$contact","$email","1","12ABCDE2356F7GH","$name",
                Notes("Hii,nothing","i want to say")
            )

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.razorpay.com/v1/")
                .build()

            val interfaceApi = retrofit.create(InterfaceApi::class.java)
            interfaceApi.postData(myData,hasMap).enqueue(object : Callback<FetchDataModel?> {
                override fun onResponse(
                    call: Call<FetchDataModel?>,
                    response: Response<FetchDataModel?>
                ) {
                    Toast.makeText(this@MainActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<FetchDataModel?>, t: Throwable) {

                }
            })
            binding.editTextText.text.clear()
            binding.editTextNumber.text.clear()
            binding.editTextTextEmailAddress.text.clear()
            binding.editTextText.requestFocus()
        }

        binding.goNextpage.setOnClickListener {
            startActivity(Intent(this@MainActivity,CustomerActivity::class.java))
        }
    }
}