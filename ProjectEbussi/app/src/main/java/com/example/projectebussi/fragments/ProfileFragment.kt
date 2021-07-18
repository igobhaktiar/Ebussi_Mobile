package com.example.projectebussi.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.projectebussi.R
import com.example.projectebussi.Riwayat
import com.example.projectebussi.helper.SharedPref
import com.example.projectebussi.login

class ProfileFragment : Fragment() {

    lateinit var s:SharedPref
    lateinit var btnlagout: RelativeLayout
    lateinit var tvNama: TextView
    lateinit var tvEmail: TextView
    lateinit var tvPhone: TextView
    lateinit var btnRiwayat: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment
        s = SharedPref(requireActivity())
        init(view)
        mainButton()
        setData()

        return view
    }

    private fun setData() {

        if (s.getUser() == null){
            val intent = Intent(activity, login::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            return
        }


        val user = s.getUser()!!

        tvNama.text = user.name
        tvPhone.text = user.nohp
        tvEmail.text = user.email
    }

    private fun mainButton() {
        btnlagout.setOnClickListener {
            s.setStatusLogin(status = false)
            val intent = Intent(requireActivity(), login::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        btnRiwayat.setOnClickListener {
            startActivity(Intent(requireContext(), Riwayat::class.java))
        }
    }

    private fun init(view: View) {
        btnlagout = view.findViewById(R.id.logoutButton)
        tvNama = view.findViewById(R.id.tv_Nama)
        tvEmail = view.findViewById(R.id.tv_Email)
        tvPhone = view.findViewById(R.id.tv_Phone)
        btnRiwayat = view.findViewById(R.id.btn_riwayat)

    }

}

