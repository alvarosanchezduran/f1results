package com.android.f1.results.ui.common.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.f1.results.repository.F1Repository
import com.android.f1.results.repository.FlagRepository
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.util.Event
import com.android.f1.results.vo.*
import javax.inject.Inject

@OpenForTesting
class FlagsViewModel
@Inject constructor(
    flagRepository: FlagRepository
) : ViewModel() {

    var currentIndexFlag = 0
    var lastResultsRaces = mutableListOf<Race>()

    private val getFlag: MutableLiveData<Event<String>> = MutableLiveData()
    private val getFlagLastResults: MutableLiveData<Event<String>> = MutableLiveData()

    var flagRequest: LiveData<Resource<List<CountryResponse>>> = Transformations
        .switchMap(getFlag) {
            flagRepository.getFlag(it.getContentIfNotHandledOrReturnNull()?: "")
        }

    var flagLastResultsRequest: LiveData<Resource<List<CountryResponse>>> = Transformations
        .switchMap(getFlagLastResults) {
            flagRepository.getFlag(it.getContentIfNotHandledOrReturnNull()?: "")
        }

    fun getFlagInfo(country: String) {
        getFlag.value = Event(country)
    }

    fun getFlagInfoLastResults(country: String) {
        getFlagLastResults.value = Event(country)
    }
}