package com.android.f1.results.ui.home

import androidx.lifecycle.*
import com.android.f1.results.repository.F1Repository
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.util.AbsentLiveData
import com.android.f1.results.util.Event
import com.android.f1.results.vo.*
import javax.inject.Inject

@OpenForTesting
class HomeViewModel
@Inject constructor(f1Repository: F1Repository) : ViewModel() {

    val nextRace = MutableLiveData<Race>()

    private val getRace: MutableLiveData<Event<Unit>> = MutableLiveData()

    var raceRequest: LiveData<Resource<F1Response<RaceTableResponse>>> = Transformations
            .switchMap(getRace) {
                f1Repository.example()
            }

    fun getRaceInfo() {
        getRace.value = Event(Unit)
    }

    fun retry() {

    }
}
