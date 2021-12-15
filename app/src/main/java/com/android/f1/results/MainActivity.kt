package com.android.f1.results

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.f1.results.databinding.MainActivityBinding
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import androidx.appcompat.app.ActionBarDrawerToggle
import com.android.f1.results.util.SupportActionManager
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import com.android.f1.results.vo.Status
import com.google.android.material.navigation.NavigationView
import android.widget.ArrayAdapter
import com.android.f1.results.util.Constants.Companion.CURRENT_YEAR
import com.android.f1.results.util.SpinnerManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, SupportActionManager, NavigationView.OnNavigationItemSelectedListener {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDatabinding()
        setUpMenu()
    }

    private fun setUpSpinner(showCurrentYear: Boolean) {
        var i = CURRENT_YEAR.toInt()
        if(!showCurrentYear) i--
        val items = mutableListOf<Int>()
        while (i >= 1950){
            items.add(i)
            i--
        }
        val adapter = ArrayAdapter(this, R.layout.spinner_item, items)
        binding.spYear.setAdapter(adapter)
    }

    override fun onNavigationItemSelected(mi: MenuItem): Boolean {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        when(mi.itemId) {
            R.id.nav_current_season -> redirectTo(R.id.currentSeasonFragment)
            R.id.nav_home -> redirectTo(R.id.homeFragment)
            R.id.nav_past_season -> redirectTo(R.id.historicalSeasonFragment)
            R.id.nav_drivers -> redirectTo(R.id.driversFragment)
            R.id.nav_constructors -> redirectTo(R.id.constructorsFragment)
        }

        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setUpToggle()
    }

    private fun setUpDatabinding() {
        binding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
                .apply {
                    lifecycleOwner = this@MainActivity
                }
    }

    private fun setUpMenu() {
        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun setToolbarTitle(title: String, iconSrc: Int?, iconOnClick: View.OnClickListener?) {
        binding.toolbar.setTitle(title)
        iconSrc?.let {
            binding.toolbarIcon.visibility = View.VISIBLE
            binding.toolbarIcon.setImageResource(iconSrc)
            binding.toolbarIcon.setOnClickListener(iconOnClick)
        }?: run {
            binding.toolbarIcon.visibility = View.GONE
        }


        setSupportActionBar(binding.toolbar)
        setUpToggle()
    }

    fun setSpinnerToolbarVisibility(spinnerManager: SpinnerManager?, showCurrentYear: Boolean = false) {
        spinnerManager?.let {
            setUpSpinner(showCurrentYear)
            binding.spYear.visibility = View.VISIBLE
            binding.spYear.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    spinnerManager.onSpinnerChangeItem(CURRENT_YEAR.toInt() - i)
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    return
                }
            }
        }?: run {
            binding.spYear.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun redirectTo(id: Int) {
        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(id)
    }

    private fun setUpToggle() {
        val toggle = ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.toolbar, R.string.app_name, R.string.app_name)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    fun loading(status: Status?) {
        binding.progressBar.setProgressBarVisibility(status == Status.LOADING)
    }

    fun loading(status: Boolean) {
        binding.progressBar.setProgressBarVisibility(status)
    }
}
