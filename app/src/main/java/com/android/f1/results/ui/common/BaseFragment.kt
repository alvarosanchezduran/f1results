package com.android.f1.results.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.f1.results.AppExecutors
import com.android.f1.results.MainActivity
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.util.autoCleared
import com.android.f1.results.vo.Status
import javax.inject.Inject

@OpenForTesting
abstract class BaseFragment<AdapterType : Any, FragmentBinding : ViewDataBinding> constructor(
    private val fragmentId: Int
) : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var adapter by autoCleared<AdapterType>()
    var binding by autoCleared<FragmentBinding>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding =
            DataBindingUtil.inflate<FragmentBinding>(inflater, fragmentId, container, false)
        binding = dataBinding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
    }

    protected fun showLoading(status: Status) {
        (activity as MainActivity).loading(status)
    }

    abstract protected fun setUpBinding()
    abstract protected fun setUpObservers()

}