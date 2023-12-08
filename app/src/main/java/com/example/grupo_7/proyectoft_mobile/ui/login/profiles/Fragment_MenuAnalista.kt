package com.example.grupo_7.proyectoft_mobile.ui.login.profiles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.grupo_7.proyectoft_mobile.R


class Fragment_MenuAnalista : Fragment() {

    private lateinit var rootView: View

    private lateinit var botonLogout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment__menu_analista, container, false)

//        logout()

        return rootView
    }

//    private fun logout(){
//
//        botonLogout = rootView.findViewById(R.id.buttonLogOut)
//
//        botonLogout.setOnClickListener{
//            findNavController().navigate(R.id.action_fragment_MenuAnalista_to_fragment_Login)
//        }
//
//    }
}