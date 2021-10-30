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
    private val getFlag: MutableLiveData<Event<Unit>> = MutableLiveData()

    var raceRequest: LiveData<Resource<F1Response<RaceTableResponse>>> = Transformations
            .switchMap(getRace) {
                f1Repository.getNextRace(lastRace.value?.season?: "2021", ((lastRace.value?.round?: "0").toInt() + 1).toString())
            }

    var lastRaceRequest: LiveData<Resource<F1Response<RaceTableResponse>>> = Transformations
        .switchMap(getLastRace) {
            f1Repository.getLastResult()
        }

    var flagRequest: LiveData<Resource<List<CountryResponse>>> = Transformations
        .switchMap(getFlag) {
            flagRepository.getFlag(nextRace.value?.circuit?.location?.country?: "")
        }

    fun getRaceInfo() {
        getRace.value = Event(Unit)
    }

    fun getLastRaceInfo() {
        getLastRace.value = Event(Unit)
    }

    fun getFlagInfo() {
        getFlag.value = Event(Unit)
    }

    fun retry() {

    }
}
