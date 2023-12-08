package com.example.grupo_7.proyectoft_mobile.ui.menues

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.grupo_7.proyectoft_mobile.R
import com.example.grupo_7.proyectoft_mobile.databinding.FragmentOptionsBinding
import com.example.grupo_7.proyectoft_mobile.databinding.FragmentReclamosBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class Fragment_Options : Fragment() {
    private var _binding: FragmentOptionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var btn_cerrarSesion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOptionsBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    fun initUI(){
        navControllerOptions()
    }

    fun navControllerOptions(){
        var navBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView)

        btn_cerrarSesion = binding.textViewCerarSession

        btn_cerrarSesion.setOnClickListener(){
            navBar.visibility = View.INVISIBLE
            findNavController().navigate(R.id.action_fragment_Options_to_fragment_Login)
        }
    }

}