package com.android.f1.results.ui.circuits.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.android.f1.results.AppExecutors
import com.android.f1.results.R
import com.android.f1.results.databinding.TotalWinnerItemBinding
import com.android.f1.results.databinding.WinnerItemBinding
import com.android.f1.results.ui.common.DataBoundListAdapter
import com.android.f1.results.vo.DriverTotalWins
import com.android.f1.results.vo.Race

class TotalWinnsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val onClickCallback: ((DriverTotalWins) -> Unit)?,
) : DataBoundListAdapter<DriverTotalWins, TotalWinnerItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<DriverTotalWins>() {
        override fun areItemsTheSame(oldItem: DriverTotalWins, newItem: DriverTotalWins): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: DriverTotalWins, newItem: DriverTotalWins): Boolean {
            return false
        }
    }
) {

    override fun createBinding(parent: ViewGroup): TotalWinnerItemBinding {
        val binding = DataBindingUtil.inflate<TotalWinnerItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.total_winner_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.driverTotal?.let {
                onClickCallback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: TotalWinnerItemBinding, item: DriverTotalWins) {
        binding.driverTotal = item
    }
}