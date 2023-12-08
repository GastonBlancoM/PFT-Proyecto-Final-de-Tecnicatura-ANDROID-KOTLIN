package com.example.grupo_7.proyectoft_mobile.ui.menues

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.grupo_7.proyectoft_mobile.R
import com.example.grupo_7.proyectoft_mobile.databinding.FragmentCrearReclamoBinding
import com.example.grupo_7.proyectoft_mobile.databinding.FragmentReclamosBinding
import com.example.grupo_7.proyectoft_mobile.ui.login.home.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class Fragment_Reclamos : Fragment() {
    private var _binding: FragmentReclamosBinding? = null
    private val binding get() = _binding!!

    private lateinit var btn_verListReclamo: TextView
    private lateinit var btn_crearNueoReclamo: TextView
    private lateinit var btn_editReclamo: TextView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReclamosBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI(){
        navControllerReclamo()
    }

    private fun navControllerReclamo(){
        var navBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView)

        btn_verListReclamo = binding.textViewVerReclamos

        btn_verListReclamo.setOnClickListener(){
            navBar.visibility = View.INVISIBLE
            findNavController().navigate(R.id.action_fragment_Reclamos2_to_fragment_listaReclamos)
        }

        btn_crearNueoReclamo = binding.textViewCrearReclamo

        btn_crearNueoReclamo.setOnClickListener(){
            navBar.visibility = View.INVISIBLE
            findNavController().navigate(R.id.action_fragment_Reclamos2_to_fragment_crearReclamo)
        }

        btn_editReclamo = binding.textViewEliminarModificarReclamo

        btn_editReclamo.setOnClickListener(){
            navBar.visibility = View.INVISIBLE
            findNavController().navigate(R.id.action_fragment_Reclamos2_to_fragment_editarReclamo)
        }
    }

}