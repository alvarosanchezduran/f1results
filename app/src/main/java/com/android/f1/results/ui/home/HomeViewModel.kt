package com.android.f1.results.ui.home

import androidx.lifecycle.*
import com.android.f1.results.repository.F1Repository
import com.android.f1.results.repository.FlagRepository
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.util.AbsentLiveData
import com.android.f1.results.util.Event
import com.android.f1.results.vo.*
import javax.inject.Inject

@OpenForTesting
class HomeViewModel
@Inject constructor(
    f1Repository: F1Repository,
    flagRepository: FlagRepository
) : ViewModel() {

    val nextRace = MutableLiveData<Race>()
    val lastRace = MutableLiveData<RaceTable>()

    private val getRace: MutableLiveData<Event<Unit>> = MutableLiveData()
    private val getLastRace: MutableLiveData<Event<Unit>> = MutableLiveData()
    private val getLastResults: MutableLiveData<Event<Unit>> = MutableLiveData()

    var raceRequest: LiveData<Resource<F1Response<RaceTableResponse>>> = Transformations
            .switchMap(getRace) {
                f1Repository.getNextRace(lastRace.value?.season?: "2021", ((lastRace.value?.round?: "0").toInt() + 1).toString())
            }

    var lastRaceRequest: LiveData<Resource<F1Response<RaceTableResponse>>> = Transformations
        .switchMap(getLastRace) {
            f1Repository.getLastResult()
        }

    var lastResultsRequest: LiveData<Resource<F1Response<RaceTableResponse>>> = Transformations
        .switchMap(getLastResults) {
            f1Repository.getLastResults()
        }

    fun getRaceInfo() {
        getRace.value = Event(Unit)
    }

    fun getLastRaceInfo() {
        getLastRace.value = Event(Unit)
    }

    fun getLastResultsInfo() {
        getLastResults.value = Event(Unit)
    }
}
