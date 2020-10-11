package com.kebinr.karmaaplication.ui.content

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.kebinr.karmaaplication.R
import com.kebinr.karmaaplication.repository.FirebaseAuthRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        navigation_view.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_pfavor->{
                    Log.v("pru","AL MENOS LLEGO")
                    findNavController(this,R.id.nav_host_fragment).navigate(R.id.favorFragment)
                    true
                }
                R.id.nav_lfavor->{
                    findNavController(this,R.id.nav_host_fragment).navigate(R.id.tomarFavorFragment)
                    true
                }
                R.id.nav_fseleccionados->{
                    true
                }
                R.id.nav_perfil->{
                    true
                }
                R.id.nav_logout->{
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Sesion")
                        .setMessage("Está seguro que desea cerrar sesion")
                        .setPositiveButton("Aceptar") { dialog, which ->
                            // Respond to positive button press
                            findNavController(this,R.id.nav_host_fragment).navigate(R.id.saliendoAnimacionFragment)
                        }.setNegativeButton("Cancelar"){dialog, wich ->
                            // Respond to negative button press
                        }
                        .show()
                    true
                }else->false
            }
        }

        val drawerToggle = ActionBarDrawerToggle(this, drawer,toolbar, R.string.open, R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayShowTitleEnabled(true)
    }
    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}