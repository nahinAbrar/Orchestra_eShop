package com.first_android_project.orchestraeshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.first_android_project.orchestraeshop.databinding.ActivityAdminCategoryBinding

class AdminCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.categoryTShirt.setOnClickListener{
            startActivity(Intent(this,AdminNewProductActivity::class.java).putExtra("category","tShirts"))
        }

        binding.categoryDress.setOnClickListener{
            startActivity(Intent(this,AdminNewProductActivity::class.java).putExtra("category","Dress"))
        }

        binding.categoryBag.setOnClickListener{
            startActivity(Intent(this,AdminNewProductActivity::class.java).putExtra("category","Bag"))
        }

        binding.categoryShoes.setOnClickListener{
            startActivity(Intent(this,AdminNewProductActivity::class.java).putExtra("category","Shoes"))
        }

        binding.categoryWatch.setOnClickListener{
            startActivity(Intent(this,AdminNewProductActivity::class.java).putExtra("category","Watch"))
        }

        binding.categorySunGlass.setOnClickListener{
            startActivity(Intent(this,AdminNewProductActivity::class.java).putExtra("category","Sunglass"))
        }

        //logout admin
        binding.categoryAdminLogout.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}