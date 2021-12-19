package com.android.f1.results.ui.circuits.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.android.f1.results.AppExecutors
import com.android.f1.results.R
import com.android.f1.results.databinding.WinnerItemBinding
import com.android.f1.results.ui.common.DataBoundListAdapter
import com.android.f1.results.vo.Race

class LastWinnersAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val onClickCallback: ((Race) -> Unit)?,
) : DataBoundListAdapter<Race, WinnerItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Race>() {
        override fun areItemsTheSame(oldItem: Race, newItem: Race): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Race, newItem: Race): Boolean {
            return false
        }
    }
) {

    override fun createBinding(parent: ViewGroup): WinnerItemBinding {
        val binding = DataBindingUtil.inflate<WinnerItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.winner_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.race?.let {
                onClickCallback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: WinnerItemBinding, item: Race) {
        binding.race = item
    }
}