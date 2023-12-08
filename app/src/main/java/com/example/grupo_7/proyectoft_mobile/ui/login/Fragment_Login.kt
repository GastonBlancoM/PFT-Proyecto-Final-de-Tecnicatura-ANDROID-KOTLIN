package com.example.grupo_7.proyectoft_mobile.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.example.grupo_7.proyectoft_mobile.R
import com.example.grupo_7.proyectoft_mobile.domain.ApiClient
import com.example.grupo_7.proyectoft_mobile.domain.UrlConfig
import com.example.grupo_7.proyectoft_mobile.data.loginSolicitud
import com.example.grupo_7.proyectoft_mobile.data.respuestaUsuario
import com.example.grupo_7.proyectoft_mobile.ui.login.home.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Response


class Fragment_Login : Fragment() {

    private lateinit var rootView: View

    private lateinit var botonLogin: Button

    private lateinit var mainActivity: MainActivity

    private lateinit var cardViewLogin: ConstraintLayout

    private lateinit var navBar: BottomNavigationView


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
        rootView = inflater.inflate(R.layout.fragment__login, container, false)

        mainActivity = activity as MainActivity

        navBar = requireActivity().findViewById(R.id.bottomNavView)
        navBar.visibility = View.INVISIBLE

        initUI()

        return rootView
    }


    fun initUI(){

        buttonLogin()
        goToRegister()
    }
    fun buttonLogin() {
        botonLogin = rootView.findViewById(R.id.botonLogin)

        botonLogin.setOnClickListener {
            sendLogin()
        }
    }

    @SuppressLint("WrongViewCast")
    private fun sendLogin(){

        val editTextUsuario = rootView.findViewById<EditText>(R.id.editTextUsuario)
        val editTextPassword = rootView.findViewById<EditText>(R.id.editTextPassword)

        val nombreUsuario = editTextUsuario.text.toString()
        val contrasenia = editTextPassword.text.toString()

        println("Nombre usuario: $nombreUsuario, contrasenia: $contrasenia")

        val apiService = ApiClient.apiService

        val usuLog = loginSolicitud(username = nombreUsuario, password = contrasenia)
        val call = apiService.loginUsuario(usuLog)


        call.enqueue(object : retrofit2.Callback<respuestaUsuario> {

            override fun onResponse(call: Call<respuestaUsuario>, response: Response<respuestaUsuario>) {
                if (response.isSuccessful) {
                    mainActivity.tokenJWT = response.body()?.token
                    mainActivity.usuarioLogueado = response.body()?.usuario

                    hideLogin()

                    findNavController().navigate(R.id.action_fragment_Login_to_fragment_Home2)
//                    when (response.body()?.usuario?.tipoUsuario) {
//                        "ANALISTA" -> goToMenuAnalista()
//                        "ESTUDIANTE" -> goToMenuEstudiante()
//                        "TUTOR" -> goToMenuTutor()
//                    }

                }else {
                            when (response.code()) {
                                404 -> Toast.makeText(requireContext(), "El usuario no existe", Toast.LENGTH_LONG).show()
                                401 -> Toast.makeText(requireContext(), "La contraseña es incorrecta", Toast.LENGTH_LONG).show()
                                else -> Toast.makeText(requireContext(), "Error en el servidor intentelo mas tarde", Toast.LENGTH_LONG).show()
//                                404 -> mostrarMensajeError("El usuario no existe")
//                                401 -> mostrarMensajeError("La contraseña es incorrecta")
//                                else -> mostrarMensajeError("Error en el servidor intentelo mas tarde")
                            }
                        }
            }

            override fun onFailure(call: retrofit2.Call<respuestaUsuario>, t: Throwable) {
                println(t.message)
            }
        })

    }

    private fun goToRegister(){

        val goRegister = rootView.findViewById<TextView>(R.id.textViewGoToRegister)

        goRegister.setOnClickListener {
            val urlConfig = UrlConfig()
            val serverUrl = urlConfig.getServerUrl()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$serverUrl/ProyectoFT/faces/register_page.xhtml"))
            intent.resolveActivity(requireActivity().packageManager)
            startActivity(intent)
        }

    }

    fun hideLogin(){
        cardViewLogin = requireActivity().findViewById(R.id.cardViewLogin)
        cardViewLogin.visibility = View.GONE
        navBar.visibility = View.VISIBLE
    }

}

