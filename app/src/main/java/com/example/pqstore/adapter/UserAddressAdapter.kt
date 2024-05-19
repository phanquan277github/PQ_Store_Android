package com.example.pqstore.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pqstore.R
import com.example.pqstore.activity.AddAddressActivity
import com.example.pqstore.activity.SelectCommuneWardActivity
import com.example.pqstore.activity.UserAddressActivity
import com.example.pqstore.api.RetrofitClient
import com.example.pqstore.databinding.ViewholderSizeBinding
import com.example.pqstore.databinding.ViewholderUserAddressBinding
import com.example.pqstore.model.Message
import com.example.pqstore.model.SizeModel
import com.example.pqstore.model.UserAddressModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class UserAddressAdapter(
    private val items: MutableList<UserAddressModel>,
    private val listener: OnAddressClickListener) : RecyclerView.Adapter<UserAddressAdapter.ViewHolder>() {
    private lateinit var context: Context

    interface OnAddressClickListener {
        fun onAddressClick(id: Int, address: String)
    }
    inner class ViewHolder(val binding: ViewholderUserAddressBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAddressAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderUserAddressBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: UserAddressModel = items[position]
        holder.binding.txtName.text = item.name
        holder.binding.txtAddress.text = item.street + " " + item.address
        holder.binding.txtPhone.text = item.phone
        holder.binding.selectContainer.setOnClickListener() {
            listener.onAddressClick(item.id, item.street + " " + item.address)
        }
        holder.binding.btnEdit.setOnClickListener() {
            val intent = Intent(context, AddAddressActivity::class.java).apply {
                putExtra("type", "updateAddress")
                putExtra("id", item.id.toString())
                putExtra("name", item.name)
                putExtra("address", item.address)
                putExtra("phone", item.phone)
                putExtra("street", item.street)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.btnDelete.setOnClickListener() {
            RetrofitClient.getRetrofitClient().deleteAddress("deleteAddress", item.id).enqueue(object:
                Callback<Message> {
                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    if (response.isSuccessful) {
                        val mess: Message? = response.body()
                        if (mess!!.success == 1) {
                            holder.itemView.context.startActivity(Intent(context, UserAddressActivity::class.java))
                        }
                    }
                }
                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Log.e("CartAdapter", t.localizedMessage)
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}