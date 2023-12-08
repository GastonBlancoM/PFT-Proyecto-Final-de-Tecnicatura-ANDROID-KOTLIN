package com.example.grupo_7.proyectoft_mobile.ui.menues.reclamos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.grupo_7.proyectoft_mobile.R
import com.example.grupo_7.proyectoft_mobile.data.Evento
import com.example.grupo_7.proyectoft_mobile.data.Reclamo
import com.example.grupo_7.proyectoft_mobile.data.Semestre
import com.example.grupo_7.proyectoft_mobile.databinding.FragmentCrearReclamoBinding
import com.example.grupo_7.proyectoft_mobile.domain.ApiClient
import com.example.grupo_7.proyectoft_mobile.ui.login.home.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragment_crearReclamo : Fragment() {

    private var _binding: FragmentCrearReclamoBinding? = null
    private val binding get() = _binding!!

    private var eventoSelec: Evento? = null
    private var semestreSelec: Semestre? = null
    private var tipoSelec: String? = null

    private val apiService = ApiClient.apiService

    private var callEventos: Call<List<Evento>>? = null

    private var callSemestres: Call<List<Semestre>>? = null

    private var callCreateReclamo: Call<Reclamo>? = null

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
        _binding = FragmentCrearReclamoBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI(){
        loadSpinnerEvento()
        loadSpinnerType()
        loadSpinnerSemestre()
        createReclamo()
        buttonBack()
    }

    private fun createReclamo() {
        val btnCrearReclamo = binding.btnCrearReclamo
        btnCrearReclamo.setOnClickListener {
            val mainActivity = activity as MainActivity //mainAM para abrebiar mainActivity_Menues

            var student = mainActivity.usuarioLogueado?.idUsuario?.toLong()

            val editTextTitle =
                binding.editTextTitulo
            var textTitle: String? = editTextTitle.text.toString()

            val editTextDetail =
                binding.editTextDetalle
            var textDetail: String? = editTextDetail.text.toString()

            val reclamo = Reclamo(-1,textTitle,tipoSelec,textDetail,
                semestreSelec?.idSemestre,
                "EN_PROCESO",student, eventoSelec?.idEvento
            )
            callCreateReclamo = apiService.createReclamo("Bearer "+(activity as MainActivity).tokenJWT,reclamo)
            callCreateReclamo?.enqueue(object : Callback<Reclamo> {

                override fun onResponse(call: Call<Reclamo>, response: Response<Reclamo>) {
                    if (response.isSuccessful) {
                        val mensaje = "Reclamo creado exitosamente"
                        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
                    }else if (response.code() == 400){
                        Toast.makeText(requireContext(), response.errorBody()?.string(), Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(requireContext(), response.errorBody()?.string(), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Reclamo>, t: Throwable) {
                    val mensaje = "Error!, al ingresar el reclamo."
                    Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun buildSpinnerEvento(lista: List<Evento>) {
        val labelSemester: TextView = binding.textViewReclamoSemestre
        val spinnerSemester: Spinner = binding.spinnerSemestre
        val listaConNull = listOf(null) + lista

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_items,
            listaConNull.map {
                it?.let { EventoSpinnerItem(it.titulo, it) } ?: "Seleccionar evento"
            })
        val spinnerEvento: Spinner = binding.spinnerEvento
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerEvento.adapter = adapter

        spinnerEvento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position)
                if (selectedItem is EventoSpinnerItem) {
                    // Hacer algo con el objeto completo del evento seleccionado
                    eventoSelec = selectedItem.evento
                    spinnerSemester.visibility = View.VISIBLE
                    labelSemester.visibility = View.VISIBLE


                } else {
                    // El elemento seleccionado es nulo o "Seleccione un evento," manejarlo según sea necesario
                    eventoSelec = null
                    spinnerSemester.visibility = View.INVISIBLE
                    labelSemester.visibility = View.INVISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                spinnerSemester.visibility = View.INVISIBLE
                labelSemester.visibility = View.INVISIBLE
            }
        }
    }

    private fun buildSpinnerSemester(lista: List<Semestre>){
        val listaConNull = listOf(null) + lista

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_items,
            listaConNull.map {
                it?.let { SemestreSpinnerItem(it.nombre, it) } ?: "Seleccionar Semestre"
            })
        val spinnerSemester: Spinner = binding.spinnerSemestre
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerSemester.adapter = adapter

        spinnerSemester.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position)
                if (selectedItem is SemestreSpinnerItem) {

                    semestreSelec = selectedItem.semestre

                } else {
                    // El elemento seleccionado es nulo o "Seleccione un semestre," manejarlo según sea necesario
                    semestreSelec = null
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                semestreSelec = null
            }
        }
    }

    private fun loadSpinnerType(){
        val listaDeTipos = listOf("Seleccionar tipo","EVENTO_VME", "ACTIVIDAD_APE")

        // Encuentra tu Spinner en el diseño XML
        val spinner =
            binding.spinnerTipoReclamo

        // Crea un ArrayAdapter utilizando la lista de tipos y un diseño de spinner predeterminado
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_items, listaDeTipos)

        // Establece el diseño que se usará cuando el Spinner esté desplegado
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Vincula el ArrayAdapter con el Spinner
        if (spinner != null) {
            spinner.adapter = adapter

            // Opcionalmente, puedes configurar un escuchador para manejar eventos de selección en el Spinner
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    // Maneja la selección aquí
                    val selectedItem = listaDeTipos[position]

                    if (selectedItem != "Seleccione un tipo"){
                        tipoSelec = selectedItem
                    }else{
                        tipoSelec = null
                    }
                    // Puedes usar 'selectedItem' para hacer algo con el tipo seleccionado
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    tipoSelec = null
                }
            }
        }


    }

    private fun loadSpinnerEvento(){
        callEventos = apiService.getAllEventos("Bearer "+(activity as MainActivity).tokenJWT)

        callEventos?.enqueue(object : Callback<List<Evento>> {
            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                println(response)
                if (response.isSuccessful) {
                    val events: List<Evento> = response.body() ?: emptyList()
                    buildSpinnerEvento(events)
                } else {
                    println("Error trayendo los eventos ${response.code()}")
                }
            }
            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    private fun loadSpinnerSemestre(){
        callSemestres = apiService.getAllSemestres("Bearer "+(activity as MainActivity).tokenJWT)

        callSemestres?.enqueue(object : Callback<List<Semestre>> {
            override fun onResponse(call: Call<List<Semestre>>, response: Response<List<Semestre>>) {
                println(response)
                if (response.isSuccessful) {
                    val semester: List<Semestre> = response.body() ?: emptyList()
                    buildSpinnerSemester(semester)
                } else {
                    println("Error trayendo los semestres ${response.code()}")
                }
            }
            override fun onFailure(call: Call<List<Semestre>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    data class EventoSpinnerItem(val nombre: String, val evento: Evento) {
        override fun toString(): String {
            return nombre
        }
    }

    data class SemestreSpinnerItem(val nombre: String, val semestre: Semestre){
        override fun toString(): String {
            return nombre
        }
    }

    fun buttonBack(){
        var buttonBack = binding.btnBackNuevoReclamo
        var navBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView)

        buttonBack.setOnClickListener(){
            navBar.visibility = View.VISIBLE
            findNavController().navigate(R.id.action_fragment_crearReclamo_to_fragment_Reclamos2)
        }
    }
}