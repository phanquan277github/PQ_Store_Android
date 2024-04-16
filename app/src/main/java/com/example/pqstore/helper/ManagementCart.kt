package com.example.pqstore.helper

import android.content.Context
import android.widget.Toast
import com.example.pqstore.model.ProductModel

class ManagementCart(val context: Context) {

//    private val tinyDB = TinyDB(context)

    fun insertFood(item: ProductModel) {
        var lists = getListCart()
        val existAlready = lists.any { it.name == item.name }
        val index = lists.indexOfFirst { it.name == item.name }

        if (existAlready) {
            lists[index].quantity = item.quantity
        } else {
            lists.add(item)
        }
//        tinyDB.putListObject("CartList", listFood)
//        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show()
    }

    fun getListCart(): ArrayList<ProductModel> {
        val products = ArrayList<ProductModel>()
        products.add(ProductModel(
            name = "Snar pham 1",
            price = 100.0,
            images = arrayListOf("https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg",
                "https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg",
                "https://cdn.shopvnb.com/img/300x300/uploads/gallery/d72-pro-white-black-3-800x800_1711589823.webp"),
            sizes = arrayListOf("38", "39", "40", "41", "42", "43"),
            colors = arrayListOf("https://product.hstatic.net/1000312752/product/agct074-27-2_e4862903f90c405ca2cbe6fe7f9b8ec3.jpg",
                "https://product.hstatic.net/1000312752/product/agct015-19-3_7ea6ec068608467fb003dbe53fb06f50.jpg",
                "https://cdn.shopvnb.com/uploads/gallery/giay-cau-l…umpoo-kh-d72-pro-trang-chinh-hang_1711589817.webp",
                "https://cdn.shopvnb.com/img/300x300/uploads/gallery/d72-pro-white-black-3-800x800_1711589823.webp"),
            rating = 4.5,
            quantity = 10,
            description = "This is a sample product description."
        ))
        products.add(ProductModel(
            name = "Snar pham 1",
            price = 100.0,
            images = arrayListOf("https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg",
                "https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg",
                "https://cdn.shopvnb.com/img/300x300/uploads/gallery/d72-pro-white-black-3-800x800_1711589823.webp"),
            sizes = arrayListOf("38", "39", "40", "41", "42", "43"),
            colors = arrayListOf("https://product.hstatic.net/1000312752/product/agct074-27-2_e4862903f90c405ca2cbe6fe7f9b8ec3.jpg",
                "https://product.hstatic.net/1000312752/product/agct015-19-3_7ea6ec068608467fb003dbe53fb06f50.jpg",
                "https://cdn.shopvnb.com/uploads/gallery/giay-cau-l…umpoo-kh-d72-pro-trang-chinh-hang_1711589817.webp",
                "https://cdn.shopvnb.com/img/300x300/uploads/gallery/d72-pro-white-black-3-800x800_1711589823.webp"),
            rating = 4.5,
            quantity = 10,
            description = "Kumpoo KH-D72 Pro là mẫu giày thuộc phân khúc tầm trung hướng đến đối tượng người chơi phong trào hay bán chuyên, đảm bảo đáp ứng những nhu cầu cơ bản của môn thể thao này, bảo vệ đôi chân khỏi những chấn thương không đáng có. Đế giày còn có lớp ngoài làm từ cao su tự nhiên cùng với các trụ và mặt dưới mặt đất để hỗ trợ di chuyển với tốc độ cao mà vẫn tăng độ cao trên sân, chịu áp mài cao. Bên cạnh đó, chất liệu này có thể chơi tốt với mọi mặt sàn từ thảm cầu lông, thảm thi đấu đa năng, sân xi măng, sân gạch hay sân bê tông. Tuy nhiên, để giữ độ bền cho đế giày, người chơi nên chọn sân thảm chuyên dụng.\n" +
                    "\n" +
                    "Lót giày Kumpoo KH-D72 Pro còn thêm một lớp đệm khí ở phần gan bàn chân và phần gót tăng thêm độ êm ái cho người dùng. Ngoài ra, với thiết kế ôm gọn bàn chân, thoáng khí, mặt đế cao su tự nhiên rất bám sàn, chống trơn trượt, giúp người chơi cầu lông linh hoạt trên sân đấu, hạn chế tối đa chấn thương."
        ))
        products.add(ProductModel(
            name = "Snar pham 1",
            price = 100.0,
            images = arrayListOf("https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg",
                "https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg",
                "https://cdn.shopvnb.com/img/300x300/uploads/gallery/d72-pro-white-black-3-800x800_1711589823.webp"),
            sizes = arrayListOf("38", "39", "40", "41", "42", "43"),
            colors = arrayListOf("https://product.hstatic.net/1000312752/product/agct074-27-2_e4862903f90c405ca2cbe6fe7f9b8ec3.jpg",
                "https://product.hstatic.net/1000312752/product/agct015-19-3_7ea6ec068608467fb003dbe53fb06f50.jpg",
                "https://cdn.shopvnb.com/uploads/gallery/giay-cau-l…umpoo-kh-d72-pro-trang-chinh-hang_1711589817.webp",
                "https://cdn.shopvnb.com/img/300x300/uploads/gallery/d72-pro-white-black-3-800x800_1711589823.webp"),
            rating = 4.5,
            quantity = 10,
            description = "This is a sample product description."
        ))
        products.add(ProductModel(
            name = "Snar pham 1",
            price = 100.0,
            images = arrayListOf("https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg",
                "https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg",
                "https://cdn.shopvnb.com/img/300x300/uploads/gallery/d72-pro-white-black-3-800x800_1711589823.webp"),
            sizes = arrayListOf("38", "39", "40", "41", "42", "43"),
            colors = arrayListOf("https://product.hstatic.net/1000312752/product/agct074-27-2_e4862903f90c405ca2cbe6fe7f9b8ec3.jpg",
                "https://product.hstatic.net/1000312752/product/agct015-19-3_7ea6ec068608467fb003dbe53fb06f50.jpg",
                "https://cdn.shopvnb.com/uploads/gallery/giay-cau-l…umpoo-kh-d72-pro-trang-chinh-hang_1711589817.webp",
                "https://cdn.shopvnb.com/img/300x300/uploads/gallery/d72-pro-white-black-3-800x800_1711589823.webp"),
            rating = 4.5,
            quantity = 10,
            description = "This is a sample product description."
        ))
        products.add(ProductModel(
            name = "Snar pham 1",
            price = 100.0,
            images = arrayListOf("https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg",
                "https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg",
                "https://cdn.shopvnb.com/img/300x300/uploads/gallery/d72-pro-white-black-3-800x800_1711589823.webp"),
            sizes = arrayListOf("38", "39", "40", "41", "42", "43"),
            colors = arrayListOf("https://product.hstatic.net/1000312752/product/agct074-27-2_e4862903f90c405ca2cbe6fe7f9b8ec3.jpg",
                "https://product.hstatic.net/1000312752/product/agct015-19-3_7ea6ec068608467fb003dbe53fb06f50.jpg",
                "https://cdn.shopvnb.com/uploads/gallery/giay-cau-l…umpoo-kh-d72-pro-trang-chinh-hang_1711589817.webp",
                "https://cdn.shopvnb.com/img/300x300/uploads/gallery/d72-pro-white-black-3-800x800_1711589823.webp"),
            rating = 4.5,
            quantity = 10,
            description = "This is a sample product description."
        ))
        products.add(ProductModel(
            name = "Snar pham 1",
            price = 100.0,
            images = arrayListOf("https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg",
                "https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg",
                "https://cdn.shopvnb.com/img/300x300/uploads/gallery/d72-pro-white-black-3-800x800_1711589823.webp"),
            sizes = arrayListOf("38", "39", "40", "41", "42", "43"),
            colors = arrayListOf("https://product.hstatic.net/1000312752/product/agct074-27-2_e4862903f90c405ca2cbe6fe7f9b8ec3.jpg",
                "https://product.hstatic.net/1000312752/product/agct015-19-3_7ea6ec068608467fb003dbe53fb06f50.jpg",
                "https://cdn.shopvnb.com/uploads/gallery/giay-cau-l…umpoo-kh-d72-pro-trang-chinh-hang_1711589817.webp",
                "https://cdn.shopvnb.com/img/300x300/uploads/gallery/d72-pro-white-black-3-800x800_1711589823.webp"),
            rating = 4.5,
            quantity = 10,
            description = "This is a sample product description."
        ))
        val productss = ArrayList<ProductModel>()

        return products
//        return tinyDB.getListObject("CartList") ?: arrayListOf()
    }

    fun minusItem(lists: ArrayList<ProductModel>, position: Int, listener: ChangeNumberItemsListener) {
        if (lists[position].quantity == 1) {
            lists.removeAt(position)
        } else {
            lists[position].quantity--
        }
//        tinyDB.putListObject("CartList", lists)
        listener.onChanged()
    }

    fun plusItem(listFood: ArrayList<ProductModel>, position: Int, listener: ChangeNumberItemsListener) {
        listFood[position].quantity++
//        tinyDB.putListObject("CartList", listFood)
        listener.onChanged()
    }

    fun getTotal(): Double {
        val lists = getListCart()
        var fee = 0.0
        for (item in lists) {
            fee += item.price * item.quantity
        }
        return fee
    }
}