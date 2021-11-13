package com.android.f1.results.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.android.f1.results.AppExecutors
import com.android.f1.results.R
import com.android.f1.results.databinding.RaceItemBinding
import com.android.f1.results.ui.common.DataBoundListAdapter
import com.android.f1.results.vo.QualifyingRow
import com.android.f1.results.vo.Result

class RaceAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val onClickCallback: ((Result) -> Unit)?,
    private val onLongClickCallback: ((Result) -> Unit)?
) : DataBoundListAdapter<Result, RaceItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
) {

    var isOnLongActived = false

    override fun createBinding(parent: ViewGroup): RaceItemBinding {
        val binding = DataBindingUtil.inflate<RaceItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.race_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.result?.let {
                onClickCallback?.invoke(it)
            }
        }

        binding.root.setOnLongClickListener {
            binding.result?.let {
                onLongClickCallback?.invoke(it)
            }
            return@setOnLongClickListener true
        }
        return binding
    }

    override fun bind(binding: RaceItemBinding, item: Result) {
        binding.result = item
    }

    fun setAllSelected() {
        isOnLongActived = !isOnLongActived
        currentList.forEachIndexed { index, result ->
            result.selected = isOnLongActived || (!isOnLongActived && index == 0)
        }
        notifyDataSetChanged()
    }

    fun setSelectedResult(item: Result) {
        if(!isOnLongActived) {
            currentList.forEach {
                it.selected = item == it
            }
            notifyDataSetChanged()
        }
    }
}