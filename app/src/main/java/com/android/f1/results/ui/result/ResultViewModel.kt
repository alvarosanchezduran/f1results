package com.android.f1.results.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
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
class ResultViewModel
@Inject constructor(
    resultRepository: ResultRepository
) : ViewModel() {

    val race = MutableLiveData<Race>()
    val year = MutableLiveData<String?>()

    private val getQualifying: MutableLiveData<Event<Unit>> = MutableLiveData()
    private val getResults: MutableLiveData<Event<Unit>> = MutableLiveData()


    var qualifyingRequest: LiveData<Resource<F1Response<RaceTableResponse>>> = Transformations
        .switchMap(getQualifying) {
            race.value?.season?.let { season ->
                race.value?.round?.let { round ->
                    resultRepository.getQualifying(season, round)
                }

            }
        }

    var resultsRequest: LiveData<Resource<F1Response<RaceTableResponse>>> = Transformations
        .switchMap(getResults) {
            resultRepository.getLastResults(year.value)
        }


    fun getQualifying() {
        getQualifying.value = Event(Unit)
    }

    fun getResultsInfo() {
        getResults.value = Event(Unit)
    }

    fun getYearSelected(): String {
        return year.value?: Constants.CURRENT_YEAR
    }
}