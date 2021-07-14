
package com.example.projectebussi

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.projectebussi.fragments.HomeFragment
import com.example.projectebussi.fragments.Keranjang
import com.example.projectebussi.fragments.NotifikasiFragment
import com.example.projectebussi.fragments.ProfileFragment
import com.example.projectebussi.helper.SharedPref
import com.example.projectebussi.room.MyDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.toolbar_custom.*


class MainActivity : AppCompatActivity() {
    lateinit var myDb: MyDatabase
    private val fragmentHome: Fragment = HomeFragment()
    private val fragmentKeranjang: Fragment = Keranjang()
    private val fragmentProfile: Fragment = ProfileFragment()
    private val fragmentNotifikasi: Fragment = NotifikasiFragment()


    private var statusLogin  = false
    private lateinit var s:SharedPref

    private var dariDetail :Boolean= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        s = SharedPref(this)



        makeCurrentFragment(fragmentHome)
//        Handler().postDelayed({
//            badgeSetup(R.id.nav_profile,7)
//        },2000)

        badgeSetup(R.id.nav_keranjang,0)

//        badgeSetup(R.id.nav_notif,3)


        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)


        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.nav_home -> {
                    makeCurrentFragment(fragmentHome)
                    Log.i(TAG, "Home Selected")
                    badgeClear(R.id.nav_home)
                }

                R.id.nav_keranjang -> {
                    makeCurrentFragment(fragmentKeranjang)
                    Log.i(TAG,"Keranjang Selected")
                    badgeClear(R.id.nav_keranjang)
                }

                R.id.nav_notif -> {
                    makeCurrentFragment(fragmentNotifikasi)
                    Log.i(TAG, "Favourites Selected")
                    badgeClear(R.id.nav_notif)
                }
                R.id.nav_profile -> {
                    if (s.getStatusLogin()) {
                        makeCurrentFragment(fragmentProfile)
                        Log.i(TAG, "Settings Selected")
                        badgeClear(R.id.nav_profile)
                    } else {
                        startActivity(Intent(this, login::class.java))
                    }
                }

            }
            true
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(Message, IntentFilter("event:keranjang"))

    }

    val Message : BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            dariDetail = true
        }

    }


    private fun badgeSetup(id: Int, alerts: Int) {
        val bottom_navigation2 = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val badge = bottom_navigation2.getOrCreateBadge(id)
            badge.isVisible = false
            badge.number = alerts
    }


    private fun badgeClear(id: Int) {
        val bottom_navigation3 = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val badgeDrawable = bottom_navigation3.getBadge(id)
        if (badgeDrawable != null) {
            badgeDrawable.isVisible = false
            badgeDrawable.clearNumber()
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

    override fun onResume() {
        if (dariDetail){
            dariDetail = false
            makeCurrentFragment(fragmentKeranjang)
        }
        super.onResume()
    }

}