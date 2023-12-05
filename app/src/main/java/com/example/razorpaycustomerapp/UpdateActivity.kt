package com.example.razorpaycustomerapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.razorpaycustomerapp.databinding.ActivityUpdateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//import android.annotation.SuppressLint
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Base64
//import android.widget.Toast
//import com.example.razorpaycustomerapp.databinding.ActivityUpdateBinding
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory


class UpdateActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUpdateBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getId = intent.getStringExtra("id")
        val getName = intent.getStringExtra("name")
        val getEmail = intent.getStringExtra("email")
        val getContact = intent.getStringExtra("contact")

        binding.getNameForUpdate.setText(getName)
        binding.getEmailFroUpdate.setText(getEmail)
        binding.getContactForUpdae.setText(getContact)



        val header = "Basic " + Base64.encodeToString( "rzp_test_9JZiWARukAUmQR:25sPrA3Ppbb4Gn6nQO9OAj1s".toByteArray(), Base64.NO_WRAP)
        val hasMap = HashMap<String,String?>()

        hasMap["Authorization"]=header
        binding.updateBtn.setOnClickListener {
            val name = binding.getNameForUpdate.text.toString()
            val email = binding.getEmailFroUpdate.text.toString()
            val contact = binding.getContactForUpdae.text.toString()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.razorpay.com/v1/")
                .addConverterFactory(GsonConverterFactory.create()) // Use Gson converter for JSON parsing, you can use other converters too
                .build()

            val data = DataModelForMe(name,email,contact)

            val interfaceApi = retrofit.create(InterfaceApi::class.java)
            val myCall =interfaceApi.putData(hasMap,getId!!,data)

            myCall.enqueue(object : Callback<FetchDataModel?> {
                override fun onResponse(
                    call: Call<FetchDataModel?>,
                    response: Response<FetchDataModel?>
                ) {
                    Toast.makeText(this@UpdateActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                    if (response.isSuccessful){
                        startActivity(Intent(this@UpdateActivity,CustomerActivity::class.java))
                    }

                }

                override fun onFailure(call: Call<FetchDataModel?>, t: Throwable) {
                    Toast.makeText(this@UpdateActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })

        }


    }
}