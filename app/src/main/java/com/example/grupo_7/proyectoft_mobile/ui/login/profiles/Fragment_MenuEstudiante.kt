package com.example.grupo_7.proyectoft_mobile.ui.login.profiles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.grupo_7.proyectoft_mobile.R
import com.example.grupo_7.proyectoft_mobile.databinding.FragmentMenuEstudianteBinding
import com.example.grupo_7.proyectoft_mobile.ui.login.home.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call

class Fragment_MenuEstudiante : Fragment() {

    private var _binding: FragmentMenuEstudianteBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMenuEstudianteBinding.inflate(layoutInflater, container, false)
//        goToAltaReclamo()
//        goToListaReclamo()


        return binding.root
    }

//    fun goToAltaReclamo(){
//        val btnAltaReclamo: Button = binding.buttonAltaReclamo
//        btnAltaReclamo.setOnClickListener{
//            findNavController().navigate(R.id.action_fragment_MenuEstudiante_to_fragment_crearReclamo)
//        }
//    }
//
//    fun goToListaReclamo(){
//        val btnListaReclamo: Button = binding.buttonListaReclamo
//        btnListaReclamo.setOnClickListener{
//            findNavController().navigate(R.id.action_fragment_MenuEstudiante_to_fragment_listaReclamos)
//        }
//    }



}