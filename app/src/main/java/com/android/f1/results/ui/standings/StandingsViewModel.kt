package com.android.f1.results.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.f1.results.repository.F1Repository
import com.android.f1.results.repository.FlagRepository
import com.android.f1.results.repository.ResultRepository
import com.android.f1.results.repository.StandingsRepository
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.util.Event
import com.android.f1.results.vo.*
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class StandingsViewModel
@Inject constructor(
    standingsRepository: StandingsRepository
) : ViewModel() {

    private val CURRENT_YEAR = "2021"

    var yearSelected: String? = null

    private val getDriverStanding: MutableLiveData<Event<Unit>> = MutableLiveData()
    private val getConstructorStanding: MutableLiveData<Event<Unit>> = MutableLiveData()

    var driverStandingRequest: LiveData<Resource<F1Response<StandingsTableResponse>>> = Transformations
        .switchMap(getDriverStanding) {
            standingsRepository.getDriverStanding(yearSelected?: CURRENT_YEAR)
        }

    var constructorsStandingRequest: LiveData<Resource<F1Response<StandingsTableResponse>>> = Transformations
        .switchMap(getConstructorStanding) {
            standingsRepository.getConstructorsStanding(yearSelected?: CURRENT_YEAR)
        }


    fun getStandings() {
        getDriverStanding.value = Event(Unit)
        getConstructorStanding.value = Event(Unit)
    }
}