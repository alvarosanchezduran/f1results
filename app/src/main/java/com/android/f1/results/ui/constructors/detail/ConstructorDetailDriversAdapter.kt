package com.android.f1.results.ui.constructors.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.android.f1.results.AppExecutors
import com.android.f1.results.R
import com.android.f1.results.databinding.ConstructorDetailItemBinding
import com.android.f1.results.databinding.DriverDetailItemBinding
import com.android.f1.results.ui.common.DataBoundListAdapter
import com.android.f1.results.util.ConstructorsColors
import com.android.f1.results.vo.Constructor
import com.android.f1.results.vo.Driver

class ConstructorDetailDriversAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val onClickCallback: ((Driver) -> Unit)?,
) : DataBoundListAdapter<Driver, DriverDetailItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Driver>() {
        override fun areItemsTheSame(oldItem: Driver, newItem: Driver): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Driver, newItem: Driver): Boolean {
            return false
        }
    }
) {

    override fun createBinding(parent: ViewGroup): DriverDetailItemBinding {
        val binding = DataBindingUtil.inflate<DriverDetailItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.driver_detail_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.driver?.let {
                onClickCallback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: DriverDetailItemBinding, item: Driver) {
        binding.driver = item
    }
}