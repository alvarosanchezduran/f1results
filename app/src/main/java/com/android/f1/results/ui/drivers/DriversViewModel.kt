package com.android.f1.results.ui.drivers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.f1.results.repository.DriversRepository
import com.android.f1.results.repository.F1Repository
import com.android.f1.results.repository.FlagRepository
import com.android.f1.results.repository.ResultRepository
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.util.Constants
import com.android.f1.results.util.Event
import com.android.f1.results.vo.*
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class DriversViewModel
@Inject constructor(
    driversRepository: DriversRepository
) : ViewModel() {

    val year = MutableLiveData<String?>()

    private val getDrivers: MutableLiveData<Event<Unit>> = MutableLiveData()

    var driversRequest: LiveData<Resource<F1Response<DriversTableResponse>>> = Transformations
        .switchMap(getDrivers) {
            driversRepository.getDrivers(getYearSelected())
        }

    fun getDriversInfo() {
        getDrivers.value = Event(Unit)
    }

    fun getYearSelected(): String {
        return year.value?: Constants.CURRENT_YEAR
    }
}