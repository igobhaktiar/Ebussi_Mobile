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
import com.example.projectebussi.helper.SharedPref
import com.example.projectebussi.login

class ProfileFragment : Fragment() {

    lateinit var s:SharedPref
    lateinit var btnlagout: RelativeLayout
    lateinit var tvNama: TextView
    lateinit var tvEmail: TextView
    lateinit var tvPhone: TextView

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
        tvNama.text = s.getString(s.nama)
        tvEmail.text = s.getString(s.email)
        tvPhone.text = s.getString(s.nohp)
    }

    private fun mainButton() {
        btnlagout.setOnClickListener {
            s.setStatusLogin(status = false)
            val intent = Intent(requireActivity(), login::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    private fun init(view: View) {
        btnlagout = view.findViewById(R.id.logoutButton)
        tvNama = view.findViewById(R.id.tv_Nama)
        tvEmail = view.findViewById(R.id.tv_Email)
        tvPhone = view.findViewById(R.id.tv_Phone)

    }
}