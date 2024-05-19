package com.example.pqstore.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pqstore.MainActivity
import com.example.pqstore.api.RetrofitClient
import com.example.pqstore.model.CartModel
import com.example.pqstore.model.CategoryModel
import com.example.pqstore.model.FavoriteModel
import com.example.pqstore.model.ProductModel
import com.example.pqstore.model.SliderModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {
    private val _favorites = MutableLiveData<MutableList<ProductModel>>()
    val favorites: LiveData<MutableList<ProductModel>> = _favorites

    private val _categories = MutableLiveData<MutableList<CategoryModel>>()
    val categories: LiveData<MutableList<CategoryModel>> = _categories

    private val _cartItems = MutableLiveData<MutableList<CartModel>>()
    val cartItems: LiveData<MutableList<CartModel>> = _cartItems
    fun loadCartItems() {
        val uid = MainActivity.auth.currentUser?.uid
        RetrofitClient.getRetrofitClient().getCarts(uid!!).enqueue(object :
            Callback<List<CartModel>> {
            override fun onResponse(call: Call<List<CartModel>>,response: Response<List<CartModel>>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    _cartItems.value = (dataResponse as MutableList<CartModel>?)!!
                }
            }
            override fun onFailure(call: Call<List<CartModel>>, t: Throwable) {
                Log.e("MainViewModel", "Error: ${t.message}")
            }
        })
    }

    private val _product = MutableLiveData<ProductModel>()
    val product: LiveData<ProductModel> = _product
    fun loadProductDetail(id: Int) {
        RetrofitClient.getRetrofitClient().getProductDetail(id).enqueue(object: Callback<ProductModel> {
            override fun onResponse(call: Call<ProductModel>, response: Response<ProductModel>) {
                if (response.isSuccessful) {
                    _product.value = response.body()
                }
            }
            override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                Log.e("ProductDetailsActivity", t.localizedMessage)
            }
        })
    }

    private val _sliders = MutableLiveData<MutableList<ImageModel>>()
    val sliders: LiveData<MutableList<ImageModel>> = _sliders
    fun loadSliders() {
        RetrofitClient.getRetrofitClient().getSliders().enqueue(object: Callback<List<ImageModel>> {
            override fun onResponse(call: Call<List<ImageModel>>, response: Response<List<ImageModel>>) {
                if (response.isSuccessful) {
                    _sliders.postValue(response.body() as ArrayList<ImageModel>?)
                }
            }
            override fun onFailure(call: Call<List<ImageModel>>, t: Throwable) {
                Log.e("MainViewModel", t.localizedMessage)
            }
        })
    }

    private val _populars = MutableLiveData<MutableList<ProductModel>>()
    val populars: LiveData<MutableList<ProductModel>> = _populars
    fun loadPopulars() {
        val uid = MainActivity.auth.currentUser?.uid
        RetrofitClient.getRetrofitClient().getPopulars(uid!!).enqueue(object: Callback<List<ProductModel>> {
            override fun onResponse(call: Call<List<ProductModel>>, response: Response<List<ProductModel>>) {
                if (response.isSuccessful) {
                    _populars.postValue(response.body() as ArrayList<ProductModel>?)
                }
            }
            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                Log.e("MainViewModel", t.localizedMessage)
            }
        })
    }

    fun loadFavorite() {
        val uid = MainActivity.auth.currentUser?.uid
        RetrofitClient.getRetrofitClient().getFavorites(uid!!).enqueue(object: Callback<List<ProductModel>> {
            override fun onResponse(call: Call<List<ProductModel>>, response: Response<List<ProductModel>>) {
                if (response.isSuccessful) {
                    _favorites.postValue(response.body() as ArrayList<ProductModel>?)
                }
            }
            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                Log.e("MainViewModel", t.localizedMessage)
            }
        })
    }

    fun loadCategories() {
        RetrofitClient.getRetrofitClient().getCategories().enqueue(object: Callback<List<CategoryModel>> {
            override fun onResponse(
                call: Call<List<CategoryModel>>,
                response: Response<List<CategoryModel>>
            ) {
                if (response.isSuccessful) {
                    _categories.postValue(response.body() as MutableList<CategoryModel>?)
                }
            }

            override fun onFailure(call: Call<List<CategoryModel>>, t: Throwable) {
                Log.e("MainViewModel", t.localizedMessage)
            }
        })
    }

    private val _productsInCategory = MutableLiveData<MutableList<ProductModel>>()
    val catalogProducts: LiveData<MutableList<ProductModel>> = _productsInCategory
    fun loadCatalogProducts(cateId: Int) {
        val uid = MainActivity.auth.currentUser?.uid
        RetrofitClient.getRetrofitClient().getCatalogProducts(uid!!, cateId).enqueue(object: Callback<List<ProductModel>> {
            override fun onResponse(call: Call<List<ProductModel>>, response: Response<List<ProductModel>>) {
                if (response.isSuccessful) {
                    _productsInCategory.postValue(response.body() as ArrayList<ProductModel>?)
                }
            }
            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                Log.e("MainViewModel", t.localizedMessage)
            }
        })
    }

    private val _searchResults = MutableLiveData<MutableList<ProductModel>>()
    val searchResults: LiveData<MutableList<ProductModel>> = _searchResults
    fun loadSearchResults(query: String) {
        val uid = MainActivity.auth.currentUser?.uid
        RetrofitClient.getRetrofitClient().search(uid!!, query).enqueue(object : Callback<List<ProductModel>> {
            override fun onResponse(call: Call<List<ProductModel>>, response: Response<List<ProductModel>>) {
                if (response.isSuccessful) {
                    _searchResults.postValue(response.body() as ArrayList<ProductModel>?)
                }
            }
            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                Log.e("MainViewModel", "Error: ${t.message}")
            }
        })
    }

    private val _address = MutableLiveData<MutableList<UserAddressModel>>()
    val address: LiveData<MutableList<UserAddressModel>> = _address
    fun loadUserAddress() {
        val uid = MainActivity.auth.currentUser?.uid
        RetrofitClient.getRetrofitClient().userAddress(uid!!).enqueue(object : Callback<List<UserAddressModel>> {
            override fun onResponse(call: Call<List<UserAddressModel>>, response: Response<List<UserAddressModel>>) {
                if (response.isSuccessful) {
                    _address.postValue(response.body() as ArrayList<UserAddressModel>?)
                }
            }
            override fun onFailure(call: Call<List<UserAddressModel>>, t: Throwable) {
                Log.e("MainViewModel", "Error: ${t.message}")
            }
        })
    }

    private val _provinces = MutableLiveData<MutableList<AddressModel>>()
    val provinces: LiveData<MutableList<AddressModel>> = _provinces
    fun loadProvinces() {
        RetrofitClient.getRetrofitClient().getProvinces().enqueue(object: Callback<List<AddressModel>> {
            override fun onResponse(call: Call<List<AddressModel>>, response: Response<List<AddressModel>>) {
                if (response.isSuccessful) {
                    _provinces.postValue(response.body() as MutableList<AddressModel>?)
                }
            }
            override fun onFailure(call: Call<List<AddressModel>>, t: Throwable) {
                Log.e("MainViewModel", "Error: ${t.message}")
            }
        })
    }
    private val _districts = MutableLiveData<MutableList<AddressModel>>()
    val districts: LiveData<MutableList<AddressModel>> = _districts
    fun loadDistricts(parentId: String) {
        RetrofitClient.getRetrofitClient().getDistricts(parentId).enqueue(object: Callback<List<AddressModel>> {
            override fun onResponse(call: Call<List<AddressModel>>, response: Response<List<AddressModel>>) {
                if (response.isSuccessful) {
                    _districts.postValue(response.body() as MutableList<AddressModel>?)
                }
            }
            override fun onFailure(call: Call<List<AddressModel>>, t: Throwable) {
                Log.e("MainViewModel", "Error: ${t.message}")
            }
        })
    }
    private val _communeWard = MutableLiveData<MutableList<AddressModel>>()
    val communeWard: LiveData<MutableList<AddressModel>> = _communeWard
    fun loadCommuneWard(parentId: String) {
        RetrofitClient.getRetrofitClient().getCommuneWards(parentId).enqueue(object: Callback<List<AddressModel>> {
            override fun onResponse(call: Call<List<AddressModel>>, response: Response<List<AddressModel>>) {
                if (response.isSuccessful) {
                    _communeWard.postValue(response.body() as MutableList<AddressModel>?)
                }
            }
            override fun onFailure(call: Call<List<AddressModel>>, t: Throwable) {
                Log.e("MainViewModel", "Error: ${t.message}")
            }
        })
    }

    private val _orders = MutableLiveData<MutableList<OrderModel>>()
    val orders: LiveData<MutableList<OrderModel>> = _orders
    fun loadOrders(orderStatus: String) {
        val uid = MainActivity.auth.currentUser?.uid
        RetrofitClient.getRetrofitClient().getOrders(uid!!, orderStatus).enqueue(object: Callback<List<OrderModel>> {
            override fun onResponse(call: Call<List<OrderModel>>, response: Response<List<OrderModel>>) {
                if (response.isSuccessful) {
                    _orders.postValue(response.body() as ArrayList<OrderModel>?)
                }
            }
            override fun onFailure(call: Call<List<OrderModel>>, t: Throwable) {
                Log.e("MainViewModel", t.localizedMessage)
            }
        })
    }
}