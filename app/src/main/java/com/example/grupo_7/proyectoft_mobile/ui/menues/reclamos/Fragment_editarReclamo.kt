package com.example.grupo_7.proyectoft_mobile.ui.menues.reclamos

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.grupo_7.proyectoft_mobile.R
import com.example.grupo_7.proyectoft_mobile.data.Evento
import com.example.grupo_7.proyectoft_mobile.data.Reclamo
import com.example.grupo_7.proyectoft_mobile.data.Semestre
import com.example.grupo_7.proyectoft_mobile.databinding.FragmentEditarReclamoBinding
import com.example.grupo_7.proyectoft_mobile.domain.ApiClient
import com.example.grupo_7.proyectoft_mobile.ui.login.home.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragment_editarReclamo : Fragment() {

    private var _binding: FragmentEditarReclamoBinding? = null
    private val binding get() = _binding!!

    lateinit var mainActivity: MainActivity
    private val apiService = ApiClient.apiService

    //Variables para editar el reclamo
    private var eventoSelec: Evento? = null
    private var semestreSelec: Semestre? = null
    private var tipoSelec: String? = null

    private val listaDeTipos = listOf("Seleccionar tipo","EVENTO_VME", "ACTIVIDAD_APE")

    //Definicion de calls
    private var callEventos: Call<List<Evento>>? = null

    private var callSemestres: Call<List<Semestre>>? = null

    private var callUpdateReclamo: Call<Reclamo>? = null

    private var callDeleteReclamo: Call<Reclamo>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mainActivity = activity as MainActivity
        _binding = FragmentEditarReclamoBinding.inflate(layoutInflater, container, false)

        loadSpinnerType()
        loadSpinnerSemestre()
        loadSpinnerEvento()

        val editTextTitle = binding.editTextEditarTitulo
        editTextTitle.text =
            Editable.Factory.getInstance().newEditable(mainActivity.reclamoSelec?.titulo)

        val editTextDetalle= binding.editTextEditarDetalle
        editTextDetalle.text =
            Editable.Factory.getInstance().newEditable(mainActivity.reclamoSelec?.detalle)

//        setSpinners()
        setSpinnersEvento()
        setSpinnersSemestre()
        setSpinnersTipo()
        buttonBack()

        val btnEliminar= binding.btnEliminarReclamo
        btnEliminar.setOnClickListener(){
            dialogConfirmacionEliminar()
        }

        val btnEditar = binding.btnModificarReclamo
        btnEditar.setOnClickListener(){
            dialogConfirmacionEditar()
        }

        return binding.root
    }

    private fun dialogConfirmacionEliminar() {
        val builder = AlertDialog.Builder(requireContext())

        val dialog = builder.setTitle("Confirmación")
            .setMessage("¿Estás seguro de realizar esta acción?")
            .setPositiveButton("Sí") { _, _ ->
                // Acción a realizar si el usuario hace clic en "Sí"
                eliminarReclamo()
            }
            .setNegativeButton("No") { dialog, _ ->
                // Cerrar el diálogo al hacer clic en "No"
                dialog.dismiss()
            }
            .create()

        dialog.show()


    }

    private fun dialogConfirmacionEditar() {
        val builder = AlertDialog.Builder(requireContext())

        val dialog = builder.setTitle("Confirmación")
            .setMessage("¿Estás seguro de realizar esta acción?")
            .setPositiveButton("Sí") { _, _ ->
                // Acción a realizar si el usuario hace clic en "Sí"
                editarReclamo()
            }
            .setNegativeButton("No") { dialog, _ ->
                // Cerrar el diálogo al hacer clic en "No"
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    fun eliminarReclamo(){
        callDeleteReclamo = apiService.deleteReclamo(mainActivity.reclamoSelec?.idReclamo,"Bearer "+(activity as MainActivity).tokenJWT)

        callDeleteReclamo?.enqueue(object : Callback<Reclamo>{
            override fun onResponse(call: Call<Reclamo>, response: Response<Reclamo>) {
                if (response.code() == 200) {
                    var mensaje = "Reclamo eliminado correctamente"
                    Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_fragment_editarReclamo_to_fragment_listaReclamos)
                } else {
                    Toast.makeText(requireContext(), response.errorBody()?.string(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Reclamo>, t: Throwable) {
                println(t.message)
            }

        })
    }
    fun editarReclamo(){
        var idEstudiante = mainActivity.usuarioLogueado?.idUsuario?.toLong()

        val editTextTitle = binding.editTextEditarTitulo
        val tituloSelec = editTextTitle.text.toString()

        val editTextDetalle = binding.editTextEditarDetalle
        val detalleSelec = editTextDetalle.text.toString()

        val reclamoEditado = Reclamo(mainActivity.reclamoSelec.idReclamo,tituloSelec,tipoSelec,detalleSelec,semestreSelec?.idSemestre,mainActivity.reclamoSelec.estado,idEstudiante,eventoSelec?.idEvento)

        callUpdateReclamo = apiService.updateReclamo("Bearer "+(activity as MainActivity).tokenJWT,reclamoEditado)

        callUpdateReclamo?.enqueue(object : Callback<Reclamo>{
            override fun onResponse(call: Call<Reclamo>, response: Response<Reclamo>) {
                if (response.isSuccessful) {
                    var mensaje = "Reclamo editado correctamente"
                    Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_fragment_editarReclamo_to_fragment_listaReclamos)
                } else {
                    Toast.makeText(requireContext(), response.errorBody()?.string(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Reclamo>, t: Throwable) {
                println(t.message)
            }

        })
    }

    private fun loadSpinnerType(){
        // Encuentra tu Spinner en el diseño XML
        val spinner =
            binding.spinnerEditarTipoReclamo

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
                    mainActivity.events = response.body() ?: emptyList()
                    buildSpinnerEvento(mainActivity.events!!)
                } else {
                    println("Error trayendo los eventos ${response.code()}")
                }
            }
            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    private fun buildSpinnerEvento(lista: List<Evento>) {
        val labelSemester: TextView = binding.textViewReclamoSemestre
        val spinnerSemester: Spinner = binding.spinnerEditarSemestre
        val listaConNull = listOf(null) + lista

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_items,
            listaConNull.map {
                it?.let { Fragment_crearReclamo.EventoSpinnerItem(it.titulo, it) } ?: "Seleccionar evento"
            })
        val spinnerEvento: Spinner = binding.spinnerEditarEvento
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
                if (selectedItem is Fragment_crearReclamo.EventoSpinnerItem) {
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

    private fun loadSpinnerSemestre(){
        callSemestres = apiService.getAllSemestres("Bearer "+(activity as MainActivity).tokenJWT)

        callSemestres?.enqueue(object : Callback<List<Semestre>> {
            override fun onResponse(call: Call<List<Semestre>>, response: Response<List<Semestre>>) {
                println(response)
                if (response.isSuccessful) {
                    mainActivity.semester = response.body() ?: emptyList()
                    buildSpinnerSemester(mainActivity.semester!!)

                } else {
                    println("Error trayendo los semestres ${response.code()}")
                }
            }
            override fun onFailure(call: Call<List<Semestre>>, t: Throwable) {
                println(t.message)
            }
        })
    }
    private fun buildSpinnerSemester(lista: List<Semestre>){
        val listaConNull = listOf(null) + lista

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_items,
            listaConNull.map {
                it?.let { Fragment_crearReclamo.SemestreSpinnerItem(it.nombre, it) } ?: "Seleccionar Semestre"
            })
        val spinnerSemester: Spinner = binding.spinnerEditarSemestre
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
                if (selectedItem is Fragment_crearReclamo.SemestreSpinnerItem) {

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
        val spinnerSemestre: Spinner = binding.spinnerEditarSemestre
        var semestreSeleccionado: Semestre = mainActivity.reclamoSelec.semestre
        var positionSemestre = mainActivity.semester!!.indexOf(semestreSeleccionado)
        spinnerSemestre.setSelection(positionSemestre)
    }

    fun setSpinnersTipo(){
        val spinnerTipo: Spinner = binding.spinnerEditarTipoReclamo

        var tipoSeleccionado: String? = mainActivity.reclamoSelec.tipoReclamo



        var positionTipo = listaDeTipos.indexOf(tipoSeleccionado)

        spinnerTipo.setSelection(positionTipo)

    }

    fun setSpinnersEvento(){
        callEventos = apiService.getAllEventos("Bearer "+(activity as MainActivity).tokenJWT)

        callEventos?.enqueue(object : Callback<List<Evento>> {
            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                if (response.isSuccessful) {
                    var eventos = response.body() ?: emptyList()
                    val spinnerEvento: Spinner? = binding.spinnerEditarEvento

                    var eventoSeleccionado: Evento = mainActivity.reclamoSelec.evento
                    var positionEvento = eventos.indexOf(eventoSeleccionado)
                    spinnerEvento?.setSelection(positionEvento+1)
                    println(positionEvento)
                } else {
                    println("Error trayendo los eventos ${response.code()}")
                }
            }
            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    fun setSpinnersSemestre(){
        callSemestres = apiService.getAllSemestres("Bearer "+(activity as MainActivity).tokenJWT)

        callSemestres?.enqueue(object : Callback<List<Semestre>> {
            override fun onResponse(call: Call<List<Semestre>>, response: Response<List<Semestre>>) {
                if (response.isSuccessful) {
                    var semestres = response.body() ?: emptyList()
                    val spinnerSemestre: Spinner? = binding.spinnerEditarSemestre

                    var semestreSeleccionado: Semestre = mainActivity.reclamoSelec.semestre
                    var positionSemestre = semestres.indexOf(semestreSeleccionado)
                    spinnerSemestre?.setSelection(positionSemestre+1)
                    println(positionSemestre)
                } else {
                    println("Error trayendo los semestres ${response.code()}")
                }
            }
            override fun onFailure(call: Call<List<Semestre>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    fun buttonBack(){
        var buttonBack = binding.btnBackEditarReclamo
        var navBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView)

        buttonBack.setOnClickListener(){
            navBar.visibility = View.VISIBLE
            findNavController().navigate(R.id.action_fragment_editarReclamo_to_fragment_Reclamos2)
        }
    }
}