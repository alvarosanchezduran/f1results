package com.android.f1.results.ui.standings

import com.android.f1.results.databinding.DriverStandingItemBinding
import com.android.f1.results.vo.DriverStanding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.android.f1.results.AppExecutors
import com.android.f1.results.R
import com.android.f1.results.databinding.ConstructorStandingItemBinding
import com.android.f1.results.ui.common.DataBoundListAdapter
import com.android.f1.results.util.ConstructorsColors
import com.android.f1.results.vo.ConstructorStanding

class ConstructorStandingAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val onClickCallback: ((ConstructorStanding) -> Unit)?,
    private val onLongClickCallback: ((ConstructorStanding) -> Unit)?
) : DataBoundListAdapter<ConstructorStanding, ConstructorStandingItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<ConstructorStanding>() {
        override fun areItemsTheSame(oldItem: ConstructorStanding, newItem: ConstructorStanding): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ConstructorStanding, newItem: ConstructorStanding): Boolean {
            return oldItem == newItem
        }
    }
) {

    var isOnLongActived = false

    override fun createBinding(parent: ViewGroup): ConstructorStandingItemBinding {
        val binding = DataBindingUtil.inflate<ConstructorStandingItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.constructor_standing_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.standing?.let {
                onClickCallback?.invoke(it)
            }
        }

        binding.root.setOnLongClickListener {
            binding.standing?.let {
                onLongClickCallback?.invoke(it)
            }
            return@setOnLongClickListener true
        }
        return binding
    }

    override fun bind(binding: ConstructorStandingItemBinding, item: ConstructorStanding) {
        binding.standing = item
        binding.clStandingContainer.context?.let { context ->
            ConstructorsColors.getConstructorColorSaved(item.constructor.constructorId)?.let {
                binding.llConstructorColor.setBackgroundResource(it)
                binding.llConstructorColorBottom.setBackgroundResource(it)
            }?: run {
                val color = ConstructorsColors.getConstructorColorProvisional(item.constructor.constructorId)
                binding.llConstructorColor.setBackgroundColor(color)
                binding.llConstructorColorBottom.setBackgroundColor(color)
            }
        }
    }

    fun setAllSelected() {
        isOnLongActived = !isOnLongActived
        currentList.forEachIndexed { index, result ->
            result.selected = isOnLongActived || (!isOnLongActived && index == 0)
        }
        notifyDataSetChanged()
    }

    fun setSelectedResult(item: ConstructorStanding) {
        if(!isOnLongActived) {
            currentList.forEach {
                it.selected = item == it
            }
            notifyDataSetChanged()
        }
    }
}