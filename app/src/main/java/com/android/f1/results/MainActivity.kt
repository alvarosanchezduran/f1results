package com.android.f1.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.f1.results.databinding.MainActivityBinding
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var binding: MainActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDatabinding()
    }

    private fun setUpDatabinding() {
        binding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
                .apply {
                    lifecycleOwner = this@MainActivity
                }
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
