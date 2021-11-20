package com.android.f1.results.ui.common.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.android.f1.results.AppExecutors
import com.android.f1.results.R
import com.android.f1.results.databinding.ResultItemBinding
import com.android.f1.results.ui.common.DataBoundListAdapter
import com.android.f1.results.vo.CountryResponse
import com.android.f1.results.vo.Race
import com.bumptech.glide.Glide

class ResultsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    private val context: Context,
    appExecutors: AppExecutors,
    private val onClickCallback: ((Race) -> Unit)?
) : DataBoundListAdapter<Race, ResultItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Race>() {
        override fun areItemsTheSame(oldItem: Race, newItem: Race): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: Race, newItem: Race): Boolean {
            return oldItem.date == newItem.date
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ResultItemBinding {
        val binding = DataBindingUtil.inflate<ResultItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.result_item,
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

    override fun bind(binding: ResultItemBinding, item: Race) {
        binding.race = item
        Glide.with(context)
            .load(item.flag)
            .into(binding.ivFlag)
    }

    fun setFlag(country: CountryResponse, currentIndexFlag: Int) {
        val item = currentList.get(currentIndexFlag)
        item?.let {
            it.flag = country.flags.png
        }

        this.notifyDataSetChanged()
    }
}