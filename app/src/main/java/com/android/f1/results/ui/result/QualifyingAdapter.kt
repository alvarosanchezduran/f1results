package com.android.f1.results.ui.result

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.android.f1.results.AppExecutors
import com.android.f1.results.R
import com.android.f1.results.databinding.QualifyingRowItemBinding
import com.android.f1.results.databinding.ResultItemBinding
import com.android.f1.results.ui.common.DataBoundListAdapter
import com.android.f1.results.vo.CountryResponse
import com.android.f1.results.vo.QualifyingRow
import com.android.f1.results.vo.Race
import com.bumptech.glide.Glide

class QualifyingAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val onClickCallback: ((QualifyingRow) -> Unit)?
) : DataBoundListAdapter<QualifyingRow, QualifyingRowItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<QualifyingRow>() {
        override fun areItemsTheSame(oldItem: QualifyingRow, newItem: QualifyingRow): Boolean {
            return oldItem.firstPosition == newItem.firstPosition
        }

        override fun areContentsTheSame(oldItem: QualifyingRow, newItem: QualifyingRow): Boolean {
            return oldItem.firstPosition == newItem.firstPosition
        }
    }
) {

    override fun createBinding(parent: ViewGroup): QualifyingRowItemBinding {
        val binding = DataBindingUtil.inflate<QualifyingRowItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.qualifying_row_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.qualifyingRow?.let {
                onClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: QualifyingRowItemBinding, item: QualifyingRow) {
        binding.qualifyingRow = item
    }

    fun setSelectedRow(item: QualifyingRow) {
        currentList.forEach {
            it.selected = item.firstPosition == it.firstPosition
        }
        notifyDataSetChanged()
    }
}