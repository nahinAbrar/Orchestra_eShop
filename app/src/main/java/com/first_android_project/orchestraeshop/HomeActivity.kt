package com.first_android_project.orchestraeshop

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.first_android_project.orchestraeshop.databinding.ActivityHomeBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private lateinit var productReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        binding.appBarHome.fabCart.setOnClickListener { view ->
            Snackbar.make(view, "No Product Found!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
/*        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )*/
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.menu.findItem(R.id.nav_logout).setOnMenuItemClickListener {
            logoutUser()
            true
        }
        val headerView: View = navView.getHeaderView(0)
        val userTextView: TextView = headerView.findViewById(R.id.nav_header_userName)

        recyclerView = findViewById(R.id.recycler_menU)
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager


    }

    private fun logoutUser() {
        startActivity(Intent(this,LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()

        productReference = FirebaseDatabase.getInstance("https://orchestra-eshop-2122an-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Products")

        val options = FirebaseRecyclerOptions.Builder<Product>()
            .setQuery(productReference,Product::class.java).build()

        val adapter = object : FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
                return ProductViewHolder(
                    LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_home, parent, false))
            }

            override fun onBindViewHolder(holder: ProductViewHolder, position: Int, model: Product) {
                holder.productName.text = model.productName
                holder.productPrice.setText("Price: " + model.price)
                holder.productDescription.text = model.description
                Picasso.get().load(model.image).into(holder.productImage)

            }
        }

        recyclerView.adapter = adapter
        adapter.startListening()

    }
}