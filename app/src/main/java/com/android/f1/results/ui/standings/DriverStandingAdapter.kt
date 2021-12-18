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
import com.android.f1.results.ui.common.DataBoundListAdapter
import com.android.f1.results.util.ConstructorsColors
import com.android.f1.results.util.DriversImages

class DriverStandingAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val onClickCallback: ((DriverStanding) -> Unit)?,
    private val onLongClickCallback: ((DriverStanding) -> Unit)?
) : DataBoundListAdapter<DriverStanding, DriverStandingItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<DriverStanding>() {
        override fun areItemsTheSame(oldItem: DriverStanding, newItem: DriverStanding): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DriverStanding, newItem: DriverStanding): Boolean {
            return oldItem == newItem
        }
    }
) {

    var isOnLongActived = false

    override fun createBinding(parent: ViewGroup): DriverStandingItemBinding {
        val binding = DataBindingUtil.inflate<DriverStandingItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.driver_standing_item,
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

    override fun bind(binding: DriverStandingItemBinding, item: DriverStanding) {
        binding.standing = item
        binding.clStandingContainer.context?.let { context ->
            ConstructorsColors.getConstructorColorSaved(item.constructors.last().constructorId)?.let {
                binding.llConstructorColor.setBackgroundResource(it)
                binding.llConstructorColorBottom.setBackgroundResource(it)
                binding.tvTeamMateGap.setTextColor(ContextCompat.getColor(context, it))
                if(DriversImages.DRIVERS_IMAGES.get(item.driver.driverId) == null) {
                    binding.ivDriver.setColorFilter(ContextCompat.getColor(context, it))
                } else {
                    binding.ivDriver.colorFilter = null
                }
            }?: run {
                val color = ConstructorsColors.getConstructorColorProvisional(item.constructors.last().constructorId)
                binding.llConstructorColor.setBackgroundColor(color)
                binding.llConstructorColorBottom.setBackgroundColor(color)
                binding.tvTeamMateGap.setTextColor(color)
                if(DriversImages.DRIVERS_IMAGES.get(item.driver.driverId) == null) {
                    binding.ivDriver.setColorFilter(color)
                } else {
                    binding.ivDriver.colorFilter = null
                }
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

    fun setSelectedResult(item: DriverStanding) {
        if(!isOnLongActived) {
            currentList.forEach {
                it.selected = item == it
            }
            notifyDataSetChanged()
        }
    }
}