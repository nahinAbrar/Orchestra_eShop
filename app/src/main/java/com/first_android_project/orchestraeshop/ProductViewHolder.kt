package com.first_android_project.orchestraeshop

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
    public var productName: TextView = itemView.findViewById(R.id.product_name)
    public var productPrice: TextView = itemView.findViewById(R.id.product_price)
    public var productDescription: TextView = itemView.findViewById(R.id.product_description)
    public var productImage: ImageView = itemView.findViewById(R.id.product_image)
    public lateinit var listener: ItemClickListener

    public fun setItemClickListener(listener: ItemClickListener) {

    }

    override fun onClick(view: View?) {
        listener.onClick(view,adapterPosition,false)

    }

}