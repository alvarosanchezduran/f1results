package com.android.f1.results.ui.drivers.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.android.f1.results.AppExecutors
import com.android.f1.results.R
import com.android.f1.results.databinding.ConstructorDetailItemBinding
import com.android.f1.results.ui.common.DataBoundListAdapter
import com.android.f1.results.util.ConstructorsColors
import com.android.f1.results.vo.Constructor

class DriverDetailConstructorsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val onClickCallback: ((Constructor) -> Unit)?,
) : DataBoundListAdapter<Constructor, ConstructorDetailItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Constructor>() {
        override fun areItemsTheSame(oldItem: Constructor, newItem: Constructor): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Constructor, newItem: Constructor): Boolean {
            return false
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ConstructorDetailItemBinding {
        val binding = DataBindingUtil.inflate<ConstructorDetailItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.constructor_detail_item,
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

    override fun bind(binding: ConstructorDetailItemBinding, item: Constructor) {
        binding.constructor = item

        ConstructorsColors.getConstructorColorSaved(item.constructorId)?.let {
            binding.llConstructorColor.setBackgroundResource(it)
            binding.llConstructorColorTop.setBackgroundResource(it)
        }?: run {
            val color = ConstructorsColors.getConstructorColorProvisional(item.constructorId)
            binding.llConstructorColor.setBackgroundColor(color)
            binding.llConstructorColorTop.setBackgroundColor(color)
        }
    }
}