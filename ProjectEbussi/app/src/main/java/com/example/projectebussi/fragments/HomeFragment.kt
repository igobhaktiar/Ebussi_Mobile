package com.example.projectebussi.fragments

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.projectebussi.R
import com.example.projectebussi.R.menu
import com.example.projectebussi.adapter.AdapterProduk
import com.example.projectebussi.app.ApiConfig
import com.example.projectebussi.model.Produk
import com.example.projectebussi.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    lateinit var rvProduk: RecyclerView
    private var listProduk:ArrayList<Produk> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View ? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        getProduk()

        // Inflate the layout for this fragment
        val imageSlider = view.findViewById<ImageSlider>(R.id.imageSlider)
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://img.freepik.com/free-photo/healthy-vegetables-wooden-table_1150-38014.jpg?size=626&ext=jpg"))
        imageList.add(SlideModel("https://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/articles/health_tools/12_powerhouse_vegetables_slideshow/intro_cream_of_crop.jpg"))
        imageList.add(SlideModel("https://www.healthyeating.org/images/default-source/home-0.0/nutrition-topics-2.0/general-nutrition-wellness/2-2-2-2foodgroups_vegetables_detailfeature.jpg?sfvrsn=226f1bc7_6"))
        imageList.add(SlideModel("https://www.vegetables.co.nz/assets/Uploads/vegetables.jpg"))

        imageSlider.setImageList(imageList, ScaleTypes.FIT)

        return view
    }


    fun displayProduk(){
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rvProduk.adapter = AdapterProduk(requireActivity(),listProduk)
        rvProduk.layoutManager = layoutManager

    }



    fun getProduk(){
        ApiConfig.instanceRetrofit.getProduk().enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>){
                val res = response.body()!!
                if(res.success == 1){
                    listProduk = res.produks
                    displayProduk()
                }
            }
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
            }
        })
    }

    fun init(view: View){
        rvProduk = view.findViewById(R.id.rv_produkHome)
    }
}

