package com.first_android_project.orchestraeshop


import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.first_android_project.orchestraeshop.databinding.ActivityRegisterBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {


    private lateinit var binding: ActivityRegisterBinding
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding =  ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerLogInButton.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        binding.registerButton.setOnClickListener{
            val firstName = binding.registerFirstName.text.toString()
            val lastName = binding.registerLastName.text.toString()
            val userName = binding.registerUserName.text.toString()
            val email = binding.registerEmail.text.toString()
            val phoneNum = binding.registerPhone.text.toString()
            val passWord = binding.registerPassWord.text.toString()
            val repassWord = binding.registerRePassWord.text.toString()

            @Suppress("DEPRECATION")
            val progressDialog = ProgressDialog(this)
            @Suppress("DEPRECATION")
            progressDialog.setMessage("Verifying Data.. Be Patient")
            progressDialog.setTitle("Register")
            progressDialog.setCancelable(false)
            progressDialog.show()

            reference = FirebaseDatabase.getInstance("https://orchestra-eshop-2122an-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
            val user = User(firstName,lastName,userName,email,phoneNum,passWord)



            if(firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || email.isEmpty() ||
                phoneNum.isEmpty() || passWord.isEmpty() || repassWord.isEmpty())
                {
                Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_LONG).show()
                    progressDialog.dismiss()
            }
            else
            {
/*                reference.child(userName).get().addOnSuccessListener {
                    if (it.exists()) {
                        Toast.makeText(this, "Username Must be Unique", Toast.LENGTH_LONG).show()
                    }}.addOnFailureListener {
                        Toast.makeText(this,"Network Problem", Toast.LENGTH_SHORT).show()
                    }*/
                    if (passWord == repassWord) {
                        reference.child(userName).setValue(user).addOnSuccessListener {

                            Toast.makeText(this, "Account Created", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, MainActivity::class.java))
                        }.addOnFailureListener {
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Password doesn't match", Toast.LENGTH_LONG).show()
                        progressDialog.dismiss()
                    }
            }

        }
    }
}