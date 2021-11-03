package com.android.f1.results.ui.result

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.QualifyingFragmentBinding
import com.android.f1.results.databinding.RaceFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.ui.home.HomeViewModel

class QualifyingFragment : BaseFragment<Any, QualifyingFragmentBinding>(R.layout.qualifying_fragment), Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val resultViewModel: ResultViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        setUpBinding()
        setUpObservers()
    }

    override fun setUpBinding() {}

    override fun setUpObservers() {
        resultViewModel.qualifyingRequest.observe(viewLifecycleOwner, { response ->
            val i = 0
        })
    }
}