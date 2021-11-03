package com.android.f1.results.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.android.f1.results.AppExecutors
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.DriversClasificationFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.home.HomeViewModel
import com.android.f1.results.util.autoCleared
import javax.inject.Inject

class DriversStandingFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appExecutors: AppExecutors

    var binding by autoCleared<DriversClasificationFragmentBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val homeViewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<DriversClasificationFragmentBinding>(
                inflater,
                R.layout.drivers_clasification_fragment,
                container,
                false,
                dataBindingComponent
        )

        binding = dataBinding

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
    }
}