package com.example.projectebussi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.projectebussi.R
import kotlinx.android.synthetic.main.fragment_keranjang.*

class Keranjang : Fragment() {
    internal var minteger = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_keranjang, container, false)
        val plus = rootView.findViewById<Button>(R.id.increase)
        val minus = rootView.findViewById<Button>(R.id.decrease)

        plus.setOnClickListener {
            increaseInteger(plus)
        }

        minus.setOnClickListener {
            decreaseInteger(minus)
        }
        
        return rootView
    }
    fun increaseInteger(view: View?) {
        minteger += 1
        display (minteger)
    }

    fun decreaseInteger(view: View) {
        minteger -= 1
        display(minteger)
    }

    private fun display(number: Int) {
        val displayInteger = view?.findViewById<View>(
            R.id.tv_count) as TextView
        displayInteger.text = "" + number
    }
    // Inflate the layout for this fragment
}