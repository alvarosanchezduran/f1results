package com.android.f1.results.ui.drivers.detail

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.DriverDetailFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.ui.drivers.DriversViewModel
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.*
import android.content.Intent
import android.net.Uri
import com.android.f1.results.util.ConstructorsColors
import com.android.f1.results.vo.Status


class DriverDetailFragment : BaseFragment<DriverDetailConstructorsAdapter, DriverDetailFragmentBinding>(R.layout.driver_detail_fragment),
    Injectable {

    var translator: Translator? = null

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val driversViewModel: DriversViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        setUpObservers()
        (activity as MainActivity).setToolbarTitle(driversViewModel.driver.value?.getName()?: "")
        setUpTranslator()
        driversViewModel.getDriverTotalGP()
        driversViewModel.driverConstructors = mutableListOf()
    }

    private fun setUpTranslator() {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(Locale.getDefault().getLanguage())
            .build()
        val ts = Translation.getClient(options)
        ts.downloadModelIfNeeded().addOnSuccessListener {
            translator = ts
            translator?.translate(driversViewModel.driver.value?.nationality)?.addOnSuccessListener {
                binding.tvDriverNationality.text = it
            }
        }
    }

    override fun setUpObservers() {
        driversViewModel.driverTotalGPRequest.observe(viewLifecycleOwner, { response ->
            if(response.status == Status.SUCCESS) {
                response.data?.data?.raceTable?.races?.last()?.results?.first()?.constructor?.let {
                    ConstructorsColors.getConstructorColorSaved(it.constructorId)?.let {
                        binding.llLineDriver.setBackgroundResource(it)
                    }?: run {
                        val color = ConstructorsColors.getConstructorColorProvisional(it.constructorId)
                        binding.llLineDriver.setBackgroundColor(color)
                    }
                }
                response.data?.data?.raceTable?.races?.let {
                    var lastYear = ""
                    it.forEach { race ->
                        race.results?.first()?.let {
                            val last = if(driversViewModel.driverConstructors.size == 0) null else driversViewModel.driverConstructors.last()
                            if(last == null || (last != null && last.constructorId != it.constructor.constructorId)) {
                                driversViewModel.driverConstructors.add(it.constructor)
                                driversViewModel.driverConstructors.last().carreras = 1
                                driversViewModel.driverConstructors.last().years = 1
                                lastYear = race.season
                            } else {
                                driversViewModel.driverConstructors.last().carreras++
                            }
                            if(race.season != lastYear) {
                                driversViewModel.driverConstructors.last().years++
                                lastYear = race.season
                            }
                        }
                    }
                }


                binding.tvTotalGp.text = response.data?.data?.total?: ""
                adapter.submitList(driversViewModel.driverConstructors.reversed())
            }
            showLoading(response.status)
        })

        driversViewModel.driverTotalGPWinnedRequest.observe(viewLifecycleOwner, { response ->
            showLoading(response.status)
            if(response.status == Status.SUCCESS) {
                binding.tvTotalGpWinned.text = response.data?.data?.total?: ""
            }
        })

        driversViewModel.driverTotalGPChampionshipsRequest.observe(viewLifecycleOwner, { response ->
            showLoading(response.status)
            if(response.status == Status.SUCCESS) {
                binding.tvTotalNumberChampionship.text = response.data?.data?.total?: ""
            }
        })
    }

    override fun setUpBinding() {
        binding.apply {
            viewModel = driversViewModel
            cvWikipedia.setOnClickListener { openWikipedia(driversViewModel.driver.value?.url) }
            adapter = DriverDetailConstructorsAdapter(dataBindingComponent, appExecutors) {

            }
            rvConstructors.adapter = adapter
        }
    }

    private fun openWikipedia(url: String?) {
        url?.let {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }
}