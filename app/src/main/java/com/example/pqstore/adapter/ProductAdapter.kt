package com.example.pqstore.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pqstore.MainActivity
import com.example.pqstore.activity.ProductDetailsActivity
import com.example.pqstore.R
import com.example.pqstore.activity.HomeActivity
import com.example.pqstore.api.RetrofitClient
import com.example.pqstore.databinding.ViewholderProductBinding
import com.example.pqstore.helper.Helper
import com.example.pqstore.model.Message
import com.example.pqstore.model.ProductModel
import io.github.muddz.styleabletoast.StyleableToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductAdapter (val items: MutableList<ProductModel>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private var context: Context? = null
    inner class ViewHolder(val binding: ViewholderProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderProductBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.apply {
            holder.binding.txtName.text = item.name.toString()
            holder.binding.txtPrice.text = Helper.formatCurrency(item.price)
            if (item.favorite) {
                holder.binding.btnFavorite.setBackgroundResource(R.drawable.ic_favorite_fill)
                holder.binding.btnFavorite.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
            }
                Glide
                    .with(holder.binding.root)
                    .load(item.imagePath)
                    .centerCrop()
                    .placeholder(R.drawable.ic_loading_spinner)
                    .into(holder.binding.image)

            // click vào sản phẩm chuyển tới trang chi tiết của nó
            holder.itemView.setOnClickListener() {
                val intent = Intent(holder.itemView.context, ProductDetailsActivity::class.java)
                intent.putExtra("id", item.id)
                holder.itemView.context.startActivity(intent)
            }

            // thêm vào yêu thích
            holder.binding.btnFavorite.setOnClickListener() {
                val uid = MainActivity.auth.currentUser?.uid
                RetrofitClient.getRetrofitClient().addToFavorite("favorite", uid!!, item.id).enqueue(object:
                    Callback<Message> {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onResponse(call: Call<Message>, response: Response<Message>) {
                        if (response.isSuccessful) {
                            val mess: Message? = response.body()
                            if (mess!!.success == 1) {
                                holder.binding.btnFavorite.setBackgroundResource(R.drawable.ic_favorite_fill)
                                holder.binding.btnFavorite.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
                                StyleableToast.makeText(context, mess.message, R.style.NotifyToast).show()
                            } else {
                                // xóa khỏi yêu thích
                                StyleableToast.makeText(context, mess.message, R.style.NotifyToast).show()
                                items.removeAt(position)
                                notifyDataSetChanged()
                            }
z                        }
                    }
                    override fun onFailure(call: Call<Message>, t: Throwable) {
                        Log.e("ProductAdapter", t.localizedMessage)
                    }
                })
            }
        }
    }


}