package com.example.grupo_7.proyectoft_mobile.ui.menues.reclamos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.navigation.fragment.findNavController
import com.example.grupo_7.proyectoft_mobile.R
import com.example.grupo_7.proyectoft_mobile.data.CompleteReclamo
import com.example.grupo_7.proyectoft_mobile.databinding.FragmentListaReclamosBinding
import com.example.grupo_7.proyectoft_mobile.domain.ApiClient
import com.example.grupo_7.proyectoft_mobile.ui.login.home.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragment_listaReclamos : Fragment() {

    private var _binding: FragmentListaReclamosBinding? = null
    private val binding get() = _binding!!

    var reclamoSelec : CompleteReclamo? = null
    var callReclamos: Call<List<CompleteReclamo>>? = null



    val apiService = ApiClient.apiService



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListaReclamosBinding.inflate(layoutInflater, container, false)



        initUI()

        return binding.root
    }

    private fun initUI()  {

        loadReclamos()
        buttonBack()
    }

    fun loadReclamos(){
        callReclamos = apiService.getAllReclamos("Bearer "+(activity as MainActivity).tokenJWT)

        callReclamos?.enqueue(object : Callback<List<CompleteReclamo>> {
            override fun onResponse(call: Call<List<CompleteReclamo>>, response: Response<List<CompleteReclamo>>) {
                println(response)
                if (response.isSuccessful) {
                    val reclamos: List<CompleteReclamo> = response.body() ?: emptyList()
                    buildListReclamos(reclamos)
                } else {
                    println("Error trayendo los reclamos ${response.code()}")
                }
            }
            override fun onFailure(call: Call<List<CompleteReclamo>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    private fun buildListReclamos(lista: List<CompleteReclamo>){

        val mainActivity = activity as MainActivity
        val listViewReclamos: ListView = binding.listViewReclamos
        val adapter = ArrayAdapter(requireContext(),R.layout.list_items,lista.map { it.titulo })

        listViewReclamos.adapter = adapter

        listViewReclamos.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = lista[position]
            mainActivity.reclamoSelec = selectedItem
            reclamoSelec = selectedItem

            findNavController().navigate(R.id.action_fragment_listaReclamos_to_fragment_editarReclamo)

        }
    }

    fun buttonBack(){
        var buttonBack = binding.btnBackPage
        var navBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView)

        buttonBack.setOnClickListener(){
            navBar.visibility = View.VISIBLE
            findNavController().navigate(R.id.action_fragment_listaReclamos_to_fragment_Reclamos2)
        }
    }
}