package com.example.grupo_7.proyectoft_mobile.ui.login.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

import com.example.grupo_7.proyectoft_mobile.R
import com.example.grupo_7.proyectoft_mobile.data.CompleteReclamo
import com.example.grupo_7.proyectoft_mobile.data.Evento
import com.example.grupo_7.proyectoft_mobile.data.Semestre

import com.example.grupo_7.proyectoft_mobile.data.Usuario
import com.example.grupo_7.proyectoft_mobile.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    var events: List<Evento>? = null
    var semester: List<Semestre>? = null

    var usuarioLogueado: Usuario? = null
    var tokenJWT: String? = ""

    var navBarV: String = ""

    lateinit var reclamoSelec: CompleteReclamo

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI(){
        initNavigation()
//        logout()


    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }

    private fun initNavigation(){
        val navHost = supportFragmentManager.findFragmentById(R.id.contenedor) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)

    }
}