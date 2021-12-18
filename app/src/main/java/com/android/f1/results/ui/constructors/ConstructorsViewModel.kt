package com.android.f1.results.ui.constructors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.f1.results.repository.*
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.util.Constants
import com.android.f1.results.util.Event
import com.android.f1.results.vo.*
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class ConstructorsViewModel
@Inject constructor(
    constructorsRepository: ConstructorsRepository
) : ViewModel() {

    var constructor = MutableLiveData<Constructor?>()

    val year = MutableLiveData<String?>()

    private val getConstructors: MutableLiveData<Event<Unit>> = MutableLiveData()
    private val getConstructorInfo: MutableLiveData<Event<Unit>> = MutableLiveData()

    var constructorsRequest: LiveData<Resource<F1Response<ConstructorsTableResponse>>> = Transformations
        .switchMap(getConstructors) {
            constructorsRepository.getConstructors(getYearSelected())
        }

    var totalGPWinnedRequest: LiveData<Resource<F1Response<TotalResponse>>> = Transformations
        .switchMap(getConstructorInfo) {
            constructorsRepository.getConstructorsGPWinned(constructor.value?.constructorId?: "")
        }

    var totalGPChampionshipsRequest: LiveData<Resource<F1Response<TotalResponse>>> = Transformations
        .switchMap(getConstructorInfo) {
            constructorsRepository.getConstructorsChampionshipsWinned(constructor.value?.constructorId?: "")
        }

    var driversOfConstructorRequest: LiveData<Resource<F1Response<DriversTableResponse>>> = Transformations
        .switchMap(getConstructorInfo) {
            constructorsRepository.getConstructorsDrivers(constructor.value?.constructorId?: "")
        }

    fun getConstructorsInfo() {
        getConstructors.value = Event(Unit)
    }

    fun getConstructorInfo() {
        getConstructorInfo.value = Event(Unit)
    }
    fun getYearSelected(): String {
        return year.value?: Constants.CURRENT_YEAR
    }
}