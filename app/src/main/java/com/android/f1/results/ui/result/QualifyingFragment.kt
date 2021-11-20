package com.android.f1.results.ui.result

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.QualifyingFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.util.DriversImages
import com.android.f1.results.vo.DriverQualifying
import com.android.f1.results.vo.QualifyingRow

class QualifyingFragment : BaseFragment<QualifyingAdapter, QualifyingFragmentBinding>(R.layout.qualifying_fragment), Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val resultViewModel: ResultViewModel by viewModels {
        viewModelFactory
    }

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
                ivDriver1.setImageResource(R.drawable.alonso)
            }

            DriversImages.DRIVERS_IMAGES.get(qualifyingRow.secondPosition?.driver?.driverId?: "")?.let {
                ivDriver2.setImageResource(it)
            }?: run {
                ivDriver2.setImageResource(R.drawable.alonso)
            }
            adapter.setSelectedRow(qualifyingRow)
        }

    }
}