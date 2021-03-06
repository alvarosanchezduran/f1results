package com.android.f1.results.ui.result

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.QualifyingFragmentBinding
import com.android.f1.results.db.preferences.F1ResultsPreferences
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.util.DriversImages
import com.android.f1.results.vo.DriverQualifying
import com.android.f1.results.vo.QualifyingRow
import javax.inject.Inject

class QualifyingFragment : BaseFragment<QualifyingAdapter, QualifyingFragmentBinding>(R.layout.qualifying_fragment), Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val resultViewModel: ResultViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var preferences: F1ResultsPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        setUpBinding()
        setUpObservers()
    }

    override fun setUpBinding() {
        adapter = QualifyingAdapter(dataBindingComponent, appExecutors) {
            selectRow(it)

        }
        binding.rvQualifyingResult.adapter = adapter
    }

    override fun setUpObservers() {
        resultViewModel.qualifyingRequest.observe(viewLifecycleOwner, { response ->
            showLoading(response.status)
            response.data?.data?.raceTable?.races?.let {
                if(it.size == 1) {
                    binding.clNoQualifyingData.visibility = View.GONE
                    val qualifyingRowList = mutableListOf<QualifyingRow>()
                    val qualifyingResultList = it.get(0).qualifyingResults
                    qualifyingResultList?.forEachIndexed { index, qualifyingResult ->
                        if(index%2 == 0) {
                            var firstDriver = DriverQualifying(qualifyingResult.driver, qualifyingResult.position, qualifyingResult.getQualificationTime())
                            var secondDriver: DriverQualifying? = null
                            if(qualifyingResultList.size > index + 1) {
                                secondDriver = DriverQualifying(qualifyingResultList.get(index+1).driver, qualifyingResultList.get(index+1).position, qualifyingResultList.get(index+1).getQualificationTime())
                            }
                            qualifyingRowList.add(
                                QualifyingRow(
                                    firstDriver,
                                    secondDriver
                                )
                            )
                        }
                    }
                    if(qualifyingRowList.size > 0) {
                        adapter.submitList(qualifyingRowList)
                        selectRow(qualifyingRowList.get(0))
                    }
                } else {
                    binding.clNoQualifyingData.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun selectRow(qualifyingRow: QualifyingRow) {
        binding.apply {
            qualifyingRowDetail = qualifyingRow
            DriversImages.DRIVERS_IMAGES.get(qualifyingRow.firstPosition.driver.driverId)?.let {
                ivDriver1.setImageResource(it)
            }?: run {
                ivDriver1.setImageResource(R.drawable.ic_silueta2)
                context?.let {
                    ivDriver1.setColorFilter(ContextCompat.getColor(it, R.color.colorPrimary))
                }
            }
            context?.let {
                if (qualifyingRow.firstPosition.driver.driverId == preferences.getFavoriteDriver()) {
                    tvNameDriver1.setTextColor(ContextCompat.getColor(it, R.color.favYellow))
                    tvSurnameDriver1.setTextColor(ContextCompat.getColor(it, R.color.favYellow))
                    tvPosition1.setTextColor(ContextCompat.getColor(it, R.color.favYellow))
                    tvTime1.setTextColor(ContextCompat.getColor(it, R.color.favYellow))
                } else {
                    tvNameDriver1.setTextColor(ContextCompat.getColor(it, R.color.colorPrimary))
                    tvSurnameDriver1.setTextColor(ContextCompat.getColor(it, R.color.colorPrimary))
                    tvPosition1.setTextColor(ContextCompat.getColor(it, R.color.colorPrimary))
                    tvTime1.setTextColor(ContextCompat.getColor(it, R.color.colorPrimary))
                }
            }

            DriversImages.DRIVERS_IMAGES.get(qualifyingRow.secondPosition?.driver?.driverId?: "")?.let {
                ivDriver2.setImageResource(it)
            }?: run {
                ivDriver2.setImageResource(R.drawable.ic_silueta2)
                context?.let {
                    ivDriver2.setColorFilter(ContextCompat.getColor(it, R.color.colorPrimary))
                }
            }
            context?.let {
                if(qualifyingRow.secondPosition?.driver?.driverId == preferences.getFavoriteDriver()) {
                    tvNameDriver2.setTextColor(ContextCompat.getColor(it, R.color.favYellow))
                    tvSurnameDriver2.setTextColor(ContextCompat.getColor(it, R.color.favYellow))
                    tvPosition2.setTextColor(ContextCompat.getColor(it, R.color.favYellow))
                    tvTime2.setTextColor(ContextCompat.getColor(it, R.color.favYellow))
                } else {
                    tvNameDriver2.setTextColor(ContextCompat.getColor(it, R.color.colorPrimary))
                    tvSurnameDriver2.setTextColor(ContextCompat.getColor(it, R.color.colorPrimary))
                    tvPosition2.setTextColor(ContextCompat.getColor(it, R.color.colorPrimary))
                    tvTime2.setTextColor(ContextCompat.getColor(it, R.color.colorPrimary))
                }
            }


            adapter.setSelectedRow(qualifyingRow)
        }

    }
}