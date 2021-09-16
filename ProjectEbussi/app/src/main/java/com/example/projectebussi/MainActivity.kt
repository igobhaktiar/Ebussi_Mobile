
package com.example.projectebussi

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.projectebussi.fragments.HomeFragment
import com.example.projectebussi.fragments.Keranjang
import com.example.projectebussi.fragments.ProfileFragment
import com.example.projectebussi.helper.SharedPref
import com.example.projectebussi.room.MyDatabase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_custom.*


class MainActivity : AppCompatActivity() {
    lateinit var myDb: MyDatabase
    private val fragmentHome: Fragment = HomeFragment()
    private val fragmentKeranjang: Fragment = Keranjang()
    private val fragmentProfile: Fragment = ProfileFragment()

    private var statusLogin  = false
    private lateinit var s:SharedPref

    private var dariDetail :Boolean= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myDb = MyDatabase.getInstance(this)!!

        s = SharedPref(this)

        makeCurrentFragment(fragmentHome)

        LocalBroadcastManager.getInstance(this).registerReceiver(Message, IntentFilter("event:keranjang"))

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Respon", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("Respon fcm:", token.toString())
        })

    }

    val Message : BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            dariDetail = true
        }

    }

    fun setBotNav(){
        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.nav_home -> {
                    makeCurrentFragment(fragmentHome)
                    Log.i(TAG, "Home Selected")
                    badgeClear(R.id.nav_home)
                }

                R.id.nav_keranjang -> {
                    if (s.getStatusLogin()) {
                        makeCurrentFragment(fragmentKeranjang)
                        Log.i(TAG, "Keranjang Selected")
                        badgeClear(R.id.nav_keranjang)
                    } else {
                        Toast.makeText(this, "Login dulu ya kakak ", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, login::class.java))
                    }
                }

                R.id.nav_profile -> {
                    if (s.getStatusLogin()) {
                        makeCurrentFragment(fragmentProfile)
                        Log.i(TAG, "Settings Selected")
                        badgeClear(R.id.nav_profile)
                    } else {
                        Toast.makeText(this, "Login dulu ya kakak ", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, login::class.java))
                    }
                }

            }
            true
        }
    }

    private fun badgeKeranjang(){
        val datakeranjang = myDb.daoKeranjang().getAll()

        if(datakeranjang.isNotEmpty()){
            badgeSetup(R.id.nav_keranjang, datakeranjang.size)
        } else{
            badgeClear(R.id.nav_keranjang)
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


    override fun onResume() {
        badgeKeranjang()
        setBotNav()

        if (dariDetail){
            dariDetail = false
            makeCurrentFragment(fragmentKeranjang)
        }
        super.onResume()
    }

}