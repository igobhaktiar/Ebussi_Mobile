package com.example.projectebussi.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.projectebussi.*
import com.example.projectebussi.R.menu
import com.example.projectebussi.adapter.AdapterProduk
import com.example.projectebussi.adapter.AdapterlistProduk
import com.example.projectebussi.app.ApiConfig
import com.example.projectebussi.helper.SharedPref
import com.example.projectebussi.model.Produk
import com.example.projectebussi.model.ResponModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_riwayat.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.pb
import kotlinx.android.synthetic.main.login_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    lateinit var rvProduk: RecyclerView
    private var listProduk:ArrayList<Produk> = ArrayList()
    lateinit var s: SharedPref
    lateinit var tvNama: TextView
    lateinit var tvError: TextView
    lateinit var searchView: SearchView
    lateinit var refreshHome : SwipeRefreshLayout
    lateinit var btnSearch : ImageButton
    lateinit var pb : ProgressBar
    lateinit var load : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View ? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)


        s = SharedPref(requireActivity())
        init(view)
        getProduk()
        getlistProduk()
        refresh()
        displaySearch()

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

    private fun refresh() {
        refreshHome.setOnRefreshListener {
            getProduk()
            refreshHome.isRefreshing = false
        }
    }


    fun displayProduk(){

//        grid produk
        val layoutManager = GridLayoutManager(activity, 2)
        layoutManager.orientation = GridLayoutManager.VERTICAL

        rvProduk.adapter = AdapterProduk(requireActivity(),listProduk)
        rvProduk.layoutManager = layoutManager

    }

    //        list produk

    fun displaylistProduk(){

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_listproduk.adapter = AdapterlistProduk(requireActivity(),listProduk)
        rv_listproduk.layoutManager = layoutManager

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



    fun getlistProduk(){
        load.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.getProduk().enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>){
                val res = response.body()!!
                if(res.success == 1){
                    load.visibility = View.GONE
                    listProduk = res.produks
                    displaylistProduk()
                }
            }
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
            }
        })
    }

    private fun displaySearch() {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isEmpty() == true){
                    layout_banner.visibility = View.VISIBLE
                    layout_produk.visibility = View.VISIBLE
                    rv_listproduk.visibility = View.GONE
                } else{
                    pb.visibility = View.VISIBLE
                    layout_banner.visibility = View.GONE
                    layout_produk.visibility = View.GONE
                    ApiConfig.instanceRetrofit.getSearch(query?.toLowerCase()!!).enqueue(object :  Callback<ResponModel>{
                        override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                            pb.visibility = View.GONE
                            val res = response.body()!!
                            if(res.success == 1) {
                                rv_listproduk.visibility = View.VISIBLE
                                listProduk = res.produks
                                displaylistProduk()
                            } else if (res.produks == null){
                                tvError.visibility = View.VISIBLE
                                error(res.message)
                            }
                        }

                        override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                            pb.visibility = View.GONE
                            tvError.visibility = View.VISIBLE
                            error(t.message.toString())
                        }

                    })
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty() == true){
                    layout_banner.visibility = View.VISIBLE
                    layout_produk.visibility = View.VISIBLE
                    rv_listproduk.visibility = View.GONE

                } else{
                    pb.visibility = View.VISIBLE
                    layout_banner.visibility = View.GONE
                    layout_produk.visibility = View.GONE
                    ApiConfig.instanceRetrofit.getSearch(newText?.toLowerCase()!!).enqueue(object :  Callback<ResponModel>{
                        override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                            pb.visibility = View.GONE
                            val res = response.body()!!
                            if(res.success == 1) {
                                rv_listproduk.visibility = View.VISIBLE
                                listProduk = res.produks
                                displaylistProduk()
                            } else if (res.produks == null){
                                tvError.visibility = View.VISIBLE
                                error(res.message)
                            }
                        }

                        override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                            pb.visibility = View.GONE
                            tvError.visibility = View.VISIBLE
                            error(t.message.toString())
                        }

                    })
                }

                return false
            }

        })
    }

    fun init(view: View){
        rvProduk = view.findViewById(R.id.rv_produkHome)
        tvNama = view.findViewById(R.id.selamatdatang)
        refreshHome = view.findViewById(R.id.refreshHome)
        searchView = view.findViewById(R.id.edt_search)
        pb = view.findViewById(R.id.pb)
        load = view.findViewById(R.id.load)
        tvError = view.findViewById(R.id.tv_error)
    }

}

