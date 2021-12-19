package com.android.f1.results

import android.content.Intent
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
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, SupportActionManager, NavigationView.OnNavigationItemSelectedListener {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDatabinding()
        setUpMenu()
        FirebaseAuth.getInstance().currentUser?.let {
            showLoginItem(false)
        }?: run {
            showLoginItem(true)
        }
    }

    private fun showLoginItem(show: Boolean) {
        binding.navigationView.menu.findItem(R.id.nav_login).setVisible(show)
        binding.navigationView.menu.findItem(R.id.nav_logout).setVisible(!show)
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
            R.id.nav_circuits -> redirectTo(R.id.circuitsFragment)
            R.id.nav_login -> redirectTo(R.id.loginFragment)
            R.id.nav_logout -> logout()
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

    fun signInGoogle() {
        val conf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val client: GoogleSignInClient = GoogleSignIn.getClient(this, conf)
        client.signOut()
        val signInIntent = client.signInIntent
        startActivityForResult(signInIntent, 1)
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        showLoginItem(true)
        redirectTo(R.id.homeFragment)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                loading(true)
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if(it.isSuccessful) {
                            showLoginItem(false)
                            changeHeaderMenuUsername(account.email)
                            redirectTo(R.id.homeFragment)
                        } else {}
                        loading(false)
                    }
                }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    private fun changeHeaderMenuUsername(text: String?) {
        text?.let {
            binding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.tv_user_email).text = text
        }?: run {
            binding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.tv_user_email).text = ""
        }
    }
}
