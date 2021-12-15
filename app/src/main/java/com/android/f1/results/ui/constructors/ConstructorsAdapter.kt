package com.android.f1.results.ui.constructors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.android.f1.results.AppExecutors
import com.android.f1.results.R
import com.android.f1.results.databinding.ConstructorItemBinding
import com.android.f1.results.databinding.DriverItemBinding
import com.android.f1.results.databinding.RaceItemBinding
import com.android.f1.results.ui.common.DataBoundListAdapter
import com.android.f1.results.util.ConstructorsColors
import com.android.f1.results.vo.Constructor
import com.android.f1.results.vo.Driver
import com.android.f1.results.vo.Result

class ConstructorsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val onClickCallback: ((Constructor) -> Unit)?,
) : DataBoundListAdapter<Constructor, ConstructorItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Constructor>() {
        override fun areItemsTheSame(oldItem: Constructor, newItem: Constructor): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Constructor, newItem: Constructor): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ConstructorItemBinding {
        val binding = DataBindingUtil.inflate<ConstructorItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.constructor_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.constructor?.let {
                onClickCallback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: ConstructorItemBinding, item: Constructor) {
        binding.constructor = item
        ConstructorsColors.getConstructorColorSaved(item.constructorId)?.let {
            binding.clConstructorContainer.setBackgroundResource(it)
        }?: run {
            val color = ConstructorsColors.getConstructorColorProvisional(item.constructorId)
            binding.clConstructorContainer.setBackgroundColor(color)
        }
    }
}