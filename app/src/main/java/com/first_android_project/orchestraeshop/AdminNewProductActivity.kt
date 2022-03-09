package com.first_android_project.orchestraeshop


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.first_android_project.orchestraeshop.databinding.ActivityAdminNewProductBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.text.SimpleDateFormat
import java.util.*


class AdminNewProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminNewProductBinding
    private lateinit var categoryName:String
    private lateinit var imageURI: Uri
    private lateinit var productName: String
    private lateinit var productDescription: String
    private lateinit var productPrice: String
    private lateinit var currentDate: Any
    private lateinit var saveCurrentDate: Any
    private lateinit var currentTime: Any
    private lateinit var saveCurrentTime: Any
    private lateinit var productRandomKey: String
    private lateinit var downloadImageUrl: String
    private  val galleryPick: Int = 1
    private lateinit var databaseReference: DatabaseReference
    private lateinit var productImageReference: StorageReference
    private lateinit var filePath: StorageReference





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)


        categoryName = intent.extras?.get("category").toString()
        productImageReference = FirebaseStorage.getInstance("gs://orchestra-eshop-2122an.appspot.com").getReference().child("Product Images")
        databaseReference = FirebaseDatabase.getInstance("https://orchestra-eshop-2122an-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Products")

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


        when {
            TextUtils.isEmpty(productName) -> {
                Toast.makeText(this,"Image is Mandatory",Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(productName) -> {
                Toast.makeText(this,"Enter Product Name",Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(productDescription) -> {
                Toast.makeText(this,"Enter Product Description",Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(productPrice) -> {
                Toast.makeText(this,"Enter Product Price",Toast.LENGTH_SHORT).show()
            }
            else -> {
                storeProductInfo()
            }
        }

    }


    @SuppressLint("SimpleDateFormat")
    private fun storeProductInfo() {

        @Suppress("DEPRECATION")
        val progressDialog = ProgressDialog(this)

        @Suppress("DEPRECATION")
        progressDialog.setTitle("Adding New Product")
        @Suppress("DEPRECATION")
        progressDialog.setMessage("Product Information is being uploaded to database, Please Wait..")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val calendar: Calendar = Calendar.getInstance()
        currentDate = SimpleDateFormat("MMM dd, yyyy")
        saveCurrentDate = (currentDate as SimpleDateFormat).format(calendar.time)

        currentTime = SimpleDateFormat("HH:mm:ss a")
        saveCurrentTime = (currentTime as SimpleDateFormat).format(calendar.time)

        productRandomKey = saveCurrentDate as String + saveCurrentTime

        filePath = productImageReference.child(imageURI.lastPathSegment + productRandomKey + ".jpg")

        val uploadTask : UploadTask = filePath.putFile(imageURI)

        uploadTask.addOnFailureListener {
            val error:String = it.toString()
            Toast.makeText(this, "Error $error",Toast.LENGTH_LONG).show()
            progressDialog.dismiss()
        }.addOnSuccessListener {
            Toast.makeText(this, "Product Images Uploaded Successfully",Toast.LENGTH_LONG).show()

            val urlTask =
                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }

                    downloadImageUrl = filePath.downloadUrl.toString()
                    return@Continuation filePath.downloadUrl
                }).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        downloadImageUrl = task.result.toString()
                        Toast.makeText(this,"got Product Image Url successfully", Toast.LENGTH_SHORT).show()
                        saveProductInfo()

                    }
                }

                }

    }

    private fun saveProductInfo() {
        val productMap: HashMap<String, Any> = HashMap<String,Any>()
        productMap["productId"] = productRandomKey
        productMap["date"] = saveCurrentDate
        productMap["time"] = saveCurrentTime
        productMap["description"] = productDescription
        productMap["image"] = downloadImageUrl
        productMap["price"] = productPrice
        productMap["name"] = productName

        databaseReference.child(productRandomKey).updateChildren(productMap).addOnCompleteListener {
            if(it.isSuccessful)
            {
                startActivity(Intent(this,AdminCategoryActivity::class.java))

                Toast.makeText(this,"Product is Added Successfully", Toast.LENGTH_SHORT).show()

            }
            else
            {

                val error = it.exception.toString()
                Toast.makeText(this,"Problem is $error", Toast.LENGTH_LONG).show()

            }

        }


    }


    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        @Suppress("DEPRECATION")
        startActivityForResult(intent,galleryPick)

    }
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == galleryPick && resultCode == RESULT_OK && data!=null)
        {
            imageURI = data.data!!
            binding.newProductImage.setImageURI(imageURI)
        }
    }

}