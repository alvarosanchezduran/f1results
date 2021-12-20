package com.android.f1.results.ui.settings

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.SettingsFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.ui.common.adapters.ResultsAdapter
import com.android.f1.results.vo.SimpleDriver

import android.widget.ArrayAdapter
import com.android.f1.results.db.preferences.F1ResultsPreferences
import javax.inject.Inject
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SettingsFragment : BaseFragment<ResultsAdapter, SettingsFragmentBinding>(R.layout.settings_fragment),
    Injectable {

    @Inject
    lateinit var firebase: FirebaseFirestore

    @Inject
    lateinit var preferences: F1ResultsPreferences

    val drivers = mutableListOf(
        SimpleDriver("", "Sin favorito"),
        SimpleDriver("alonso", "Fernando Alonso"),
        SimpleDriver("hamilton", "Lewis Hamilton"),
        SimpleDriver("bottas", "Valtteri Bottas"),
        SimpleDriver("perez", "Carlos Pérez"),
        SimpleDriver("leclerc", "Charles Leclerc"),
        SimpleDriver("ricciardo", "Daniel Ricciardo"),
        SimpleDriver("norris", "Lando Norris"),
        SimpleDriver("gasly", "Piere Gasly"),
        SimpleDriver("tsunoda", "Yuki Tsunoda"),
        SimpleDriver("ocon", "Esteban Ocon"),
        SimpleDriver("vettel", "Sebastian Vettel"),
        SimpleDriver("giovinazzi", "Antonio Giovinazzi"),
        SimpleDriver("russell", "George Russell"),
        SimpleDriver("stroll", "Lance Stroll"),
        SimpleDriver("raikkonen", "Kimi Raikkonen"),
        SimpleDriver("mazepin", "Nikita Mazepin"),
        SimpleDriver("mick_schumacher", "Mick Schumacher"),
        SimpleDriver("max_verstappen", "Max Verstappen"),
        SimpleDriver("latifi", "Nicholas Latifi"),
        SimpleDriver("sainz", "Carlos Sainz"),
    )

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        setUpObservers()
        (activity as MainActivity).setToolbarTitle(getString(R.string.settings_title))
        showLoading(false)
    }

    override fun setUpObservers() {

    }

    override fun setUpBinding() {
        binding.apply {
            context?.let {
                val adapter = ArrayAdapter(it, R.layout.spinner_driver_item, drivers)
                spinnerFavoriteDriver.adapter = adapter
                val favoriteDriver = preferences.getFavoriteDriver()
                drivers.forEachIndexed { i, d ->
                    if (d.id == favoriteDriver) spinnerFavoriteDriver.setSelection(i)
                }

                spinnerFavoriteDriver.setOnItemSelectedListener(object : OnItemSelectedListener {
                    override fun onItemSelected(
                        adapterView: AdapterView<*>?,
                        view: View,
                        i: Int,
                        l: Long
                    ) {
                        saveInFirestore(drivers.get(i).id)
                    }

                    override fun onNothingSelected(adapterView: AdapterView<*>?) {
                        return
                    }
                })
            }
        }
    }

    fun saveInFirestore(driverId: String) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.email?.let {
            showLoading(true)
            if(driverId == "") {
                firebase.collection("users").document(it).delete().addOnCompleteListener {
                    showLoading(false)
                    preferences.setFavoriteDriver(driverId)

                }
            } else {
                firebase.collection("users").document(it).set(
                    hashMapOf("favoriteDriverId" to driverId)
                ).addOnCompleteListener {
                    showLoading(false)
                    preferences.setFavoriteDriver(driverId)
                }
            }
        }
    }
}