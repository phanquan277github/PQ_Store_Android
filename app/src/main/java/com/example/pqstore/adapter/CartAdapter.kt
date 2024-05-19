package com.example.pqstore.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pqstore.R
import com.example.pqstore.activity.HomeActivity
import com.example.pqstore.activity.ProductDetailsActivity
import com.example.pqstore.api.RetrofitClient
import com.example.pqstore.databinding.ViewholderCartBinding
import com.example.pqstore.helper.ChangeNumberItemsListener
import com.example.pqstore.helper.Helper
import com.example.pqstore.helper.ManagementCart
import com.example.pqstore.model.CartModel
import com.example.pqstore.model.Message
import io.github.muddz.styleabletoast.StyleableToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartAdapter(
    private val listItemSelected: MutableList<CartModel>,
    context: Context,
    var changeNumberItemsListener: ChangeNumberItemsListener?=null
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var context: Context? = null
    private lateinit var managementCart: ManagementCart

    inner class ViewHolder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        managementCart = ManagementCart(context)
        val binding = ViewholderCartBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItemSelected.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItemSelected[position]
        holder.itemView.apply {
            holder.binding.txtName.text = item.name
            holder.binding.txtPrice.text = Helper.formatCurrency(item.price)
            holder.binding.txtSize.text = item.size
            holder.binding.txtQuantity.text = item.quantity.toString()
            holder.binding.txtTotalEachItem.text = Helper.formatCurrency(item.quantity * item.price)
            Glide
                .with(holder.binding.root)
                .load(item.imagePath)
                .centerCrop()
                .placeholder(R.drawable.ic_loading_spinner)
                .into(holder.binding.image)

            // click vào sản phẩm chuyển tới trang chi tiết của nó
            holder.itemView.setOnClickListener() {
                val intent = Intent(holder.itemView.context, ProductDetailsActivity::class.java)
                intent.putExtra("id", item.productId)
                holder.itemView.context.startActivity(intent)
            }

            holder.binding.btnDecrease.setOnClickListener() {
                managementCart.minusItem(listItemSelected, position, object: ChangeNumberItemsListener {
                    override fun onChanged() {
                        RetrofitClient.getRetrofitClient().cartItemQuantity("cartItemQuantity", item.id, item.quantity).enqueue(object:
                            Callback<Message> {
                            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                                if (response.isSuccessful) {
                                    val mess: Message? = response.body()
                                    if (mess!!.success == 1) {
                                        notifyDataSetChanged()
                                        changeNumberItemsListener?.onChanged() // cập nhật tổng giỏ hàng
                                    }
                                }
                            }
                            override fun onFailure(call: Call<Message>, t: Throwable) {
                                Log.e("CartAdapter", t.localizedMessage)
                            }
                        })
                    }
                })
            }
            holder.binding.btnIncrease.setOnClickListener() {
                managementCart.plusItem(listItemSelected, position, object: ChangeNumberItemsListener {
                    override fun onChanged() {
                        RetrofitClient.getRetrofitClient().cartItemQuantity("cartItemQuantity", item.id, item.quantity).enqueue(object:
                            Callback<Message> {
                            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                                if (response.isSuccessful) {
                                    val mess: Message? = response.body()
                                    if (mess!!.success == 1) {
                                        notifyDataSetChanged()
                                        changeNumberItemsListener?.onChanged() // cập nhật tổng giỏ hàng
                                    }
                                }
                            }
                            override fun onFailure(call: Call<Message>, t: Throwable) {
                                Log.e("CartAdapter", t.localizedMessage)
                            }
                        })
                    }
                })
            }
        }
    }


}