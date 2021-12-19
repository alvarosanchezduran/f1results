package com.android.f1.results.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.HomeFragmentBinding
import com.android.f1.results.databinding.LoginFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.ui.common.adapters.ResultsAdapter
import com.android.f1.results.ui.common.viewmodels.FlagsViewModel
import com.android.f1.results.ui.home.HomeViewModel
import com.android.f1.results.ui.result.ResultViewModel
import com.android.f1.results.vo.Status
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class LoginFragment : BaseFragment<ResultsAdapter, LoginFragmentBinding>(R.layout.login_fragment),
    Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        setUpObservers()
        showLoading(false)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun setUpObservers() {

    }

    override fun setUpBinding() {
        binding.btnLoginGoogle.setOnClickListener {
            (activity as MainActivity).signInGoogle()
        }
    }
}