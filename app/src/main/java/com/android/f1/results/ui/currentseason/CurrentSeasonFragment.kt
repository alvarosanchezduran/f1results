package com.android.f1.results.ui.currentseason

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
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.CurrentSeasonFragmentBinding
import com.android.f1.results.databinding.HomeFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.RetryCallback
import com.android.f1.results.ui.home.HomeViewModel
import com.android.f1.results.util.autoCleared
import javax.inject.Inject

class CurrentSeasonFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appExecutors: AppExecutors

    var binding by autoCleared<CurrentSeasonFragmentBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val homeViewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<com.android.f1.results.databinding.CurrentSeasonFragmentBinding>(
                inflater,
                R.layout.current_season_fragment,
                container,
                false,
                dataBindingComponent
        )

        binding = dataBinding

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        (activity as MainActivity).setToolbarTitle(getString(R.string.current_season_title))
    }
}