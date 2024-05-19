package com.example.pqstore.activity

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pqstore.MainActivity
import com.example.pqstore.R
import com.example.pqstore.adapter.SizeAdapter
import com.example.pqstore.adapter.SliderAdapter
import com.example.pqstore.api.RetrofitClient
import com.example.pqstore.databinding.ActivityProductDetailsBinding
import com.example.pqstore.helper.Helper
import com.example.pqstore.model.ImageModel
import com.example.pqstore.model.Message
import com.example.pqstore.model.ProductModel
import com.example.pqstore.model.SizeModel
import com.example.pqstore.model.MainViewModel
import io.github.muddz.styleabletoast.StyleableToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailsActivity : AppCompatActivity(), SizeAdapter.OnSizeClickListener {
    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var item: ProductModel
    private val viewModel = MainViewModel()
    private val context = this
    private var size: String = ""

    override fun onSizeClick(size: String) {
        this.size = size
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener() {finish()}

        getBundle()
    }

    private fun getBundle() {
        val id = intent.getIntExtra("id", 0)
        viewModel.product.observe(this, Observer {
            item = it
            binding.txtName.text = item.name
            binding.txtPrice.text = Helper.formatCurrency(item.price)
            binding.txtRate.text = item.rating.toString()
            binding.txtDescriptions.text = item.description
            sliders(item.images)
            initLists(item.sizes)
            favoriting()
            addToCart()
        })
        viewModel.loadProductDetail(id)
    }

    private fun addToCart() {
        binding.btnAddToCart.setOnClickListener() {
            if (this.size == "") {
                StyleableToast.makeText(context, "Vui Lòng Chọn Kích Thước!", R.style.NotifyToast).show()
            } else {
                val uid = MainActivity.auth.currentUser?.uid
                RetrofitClient.getRetrofitClient().addToCart("addToCart", uid!!, item.id, this.size).enqueue(object:
                    Callback<Message> {
                    override fun onResponse(call: Call<Message>, response: Response<Message>) {
                        if (response.isSuccessful) {
                            val mess: Message? = response.body()
                            if (mess!!.success == 1) {
                                StyleableToast.makeText(context, mess.message, R.style.NotifyToast).show()
                            } else {
                                // thêm vào giỏ hàng thất bại
                                StyleableToast.makeText(context, mess.message, R.style.NotifyToast).show()
                            }
                        }
                    }
                    override fun onFailure(call: Call<Message>, t: Throwable) {
                        Log.e("ProductAdapter", t.localizedMessage)
                    }
                })
            }
        }
    }

    private fun favoriting() {
        binding.btnFavorite.setOnClickListener() {
            val uid = MainActivity.auth.currentUser?.uid
            RetrofitClient.getRetrofitClient().addToFavorite("favorite", uid!!, item.id).enqueue(object:
                Callback<Message> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    if (response.isSuccessful) {
                        val mess: Message? = response.body()
                        if (mess!!.success == 1) {
                            binding.btnFavorite.setBackgroundResource(R.drawable.ic_favorite_fill)
                            binding.btnFavorite.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
                            StyleableToast.makeText(context, mess.message, R.style.NotifyToast).show()
                        } else {
                            // xóa khỏi yêu thích
                            StyleableToast.makeText(context, mess.message, R.style.NotifyToast).show()
                        }
                    }
                }
                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Log.e("ProductAdapter", t.localizedMessage)
                }
            })
        }
    }
    private fun sliders(images: ArrayList<ImageModel>) {
        binding.sliderImages.adapter = SliderAdapter(images, binding.sliderImages)
        binding.sliderImages.clipChildren = false
        binding.sliderImages.clipToPadding = false
        binding.sliderImages.offscreenPageLimit = 3
        binding.sliderImages.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        if (item.images.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.sliderImages)
        }
    }
    private fun initLists(sizes: ArrayList<SizeModel>) {
        val adapter = SizeAdapter(sizes)
        adapter.setOnSizeClickListener(this)
        binding.rvSize.adapter = adapter
        binding.rvSize.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}