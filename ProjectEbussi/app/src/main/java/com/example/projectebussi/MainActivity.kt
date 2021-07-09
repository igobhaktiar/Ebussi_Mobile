
package com.example.projectebussi

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.projectebussi.fragments.HomeFragment
import com.example.projectebussi.fragments.Keranjang
import com.example.projectebussi.fragments.NotifikasiFragment
import com.example.projectebussi.fragments.ProfileFragment
import com.example.projectebussi.helper.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private var statusLogin  = false
    private lateinit var s:SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        s = SharedPref(this)


        val homeFragment = HomeFragment()
        val notifikasiFragment = NotifikasiFragment()
        val keranjang   = Keranjang()
        val  profileFragment = ProfileFragment()

        makeCurrentFragment(homeFragment)
        Handler().postDelayed({
            badgeSetup(R.id.nav_profile,7)
        },2000)

        badgeSetup(R.id.nav_keranjang,4)

        badgeSetup(R.id.nav_notif,20000)


        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)


        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.nav_home -> {
                    makeCurrentFragment(homeFragment)
                    Log.i(TAG, "Home Selected")
                    badgeClear(R.id.nav_home)
                }

                R.id.nav_keranjang -> {
                    makeCurrentFragment(keranjang)
                    Log.i(TAG,"Keranjang Selected")
                    badgeClear(R.id.nav_keranjang)
                }

                R.id.nav_notif -> {
                    makeCurrentFragment(notifikasiFragment)
                    Log.i(TAG, "Favourites Selected")
                    badgeClear(R.id.nav_notif)
                }
                R.id.nav_profile -> {
                    if (s.getStatusLogin()) {
                        makeCurrentFragment(profileFragment)
                        Log.i(TAG, "Settings Selected")
                        badgeClear(R.id.nav_profile)
                    } else {
                        startActivity(Intent(this, login::class.java))
                    }
                }

            }
            true
        }

    }


    private fun badgeSetup(id: Int, alerts: Int) {
        val bottom_navigation2 = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val badge = bottom_navigation2.getOrCreateBadge(id)
        badge.isVisible = true
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

}