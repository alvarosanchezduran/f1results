package com.android.f1.results.ui.circuits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.android.f1.results.AppExecutors
import com.android.f1.results.R
import com.android.f1.results.databinding.CircuitItemBinding
import com.android.f1.results.ui.common.DataBoundListAdapter
import com.android.f1.results.vo.Circuit

class CircuitsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val onClickCallback: ((Circuit) -> Unit)?,
) : DataBoundListAdapter<Circuit, CircuitItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Circuit>() {
        override fun areItemsTheSame(oldItem: Circuit, newItem: Circuit): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Circuit, newItem: Circuit): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun createBinding(parent: ViewGroup): CircuitItemBinding {
        val binding = DataBindingUtil.inflate<CircuitItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.circuit_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.circuit?.let {
                onClickCallback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: CircuitItemBinding, item: Circuit) {
        binding.circuit = item
    }
}