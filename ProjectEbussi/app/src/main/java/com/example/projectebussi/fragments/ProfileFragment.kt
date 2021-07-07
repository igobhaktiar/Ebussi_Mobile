package com.example.projectebussi.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.projectebussi.R
import com.example.projectebussi.helper.SharedPref
import com.example.projectebussi.login

class ProfileFragment : Fragment() {

    lateinit var s:SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment
        val logout = view.findViewById<Button>(R.id.logoutButton)


        s = SharedPref(requireActivity())

        logout.setOnClickListener {
            s.setStatusLogin(status = false)
            val intent = Intent(requireActivity(), login::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        return view
    }
}