package com.first_android_project.orchestraeshop

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.first_android_project.orchestraeshop.databinding.ActivityAdminNewProductBinding
import com.google.android.material.snackbar.Snackbar
import java.net.URI

class AdminNewProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminNewProductBinding
    private lateinit var categoryName:String
    private lateinit var imageURI: Uri
    private lateinit var productName: String
    private lateinit var productDescription: String
    private lateinit var productPrice: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryName = intent.extras?.get("category").toString()

        /*Snackbar.make(binding.root,categoryName,Snackbar.LENGTH_LONG).show()*/

        // for selecting image
        binding.newProductImage.setOnClickListener {
            selectImage()
        }

        //upload
        binding.newAddProductButton.setOnClickListener {
            verifyProductData()
        }

    }

    private fun verifyProductData() {
        productName = binding.newProductName.text.toString()
        productDescription = binding.newProductDescription.text.toString()
        productPrice = binding.newProductPrice.text.toString()
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        @Suppress("DEPRECATION")
        startActivityForResult(intent,100)

    }
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK && data!=null)
        {
            imageURI = data?.data!!
            binding.newProductImage.setImageURI(imageURI)
        }
    }
}