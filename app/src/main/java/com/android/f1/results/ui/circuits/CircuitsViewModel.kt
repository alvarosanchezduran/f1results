package com.android.f1.results.ui.circuits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.f1.results.repository.CircuitRepository
import com.android.f1.results.repository.DriversRepository
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.util.Constants
import com.android.f1.results.util.Event
import com.android.f1.results.vo.*
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class CircuitsViewModel
@Inject constructor(
    circuitRepository: CircuitRepository
) : ViewModel() {

    var circuit = MutableLiveData<Circuit?>()

    val year = MutableLiveData<String?>()

    private val getCircuits: MutableLiveData<Event<Unit>> = MutableLiveData()
    private val getCircuit: MutableLiveData<Event<Unit>> = MutableLiveData()

    var circuitsRequest: LiveData<Resource<F1Response<CircuitsTableResponse>>> = Transformations
        .switchMap(getCircuits) {
            circuitRepository.getCicuits(getYearSelected())
        }

    var circuitTotalGPRequest: LiveData<Resource<F1Response<RaceTableResponse>>> = Transformations
        .switchMap(getCircuit) {
            circuitRepository.getCircuitTotalGP(circuit.value?.circuitId?: "")
        }

    fun getCircuitsInfo() {
        getCircuits.value = Event(Unit)
    }

    fun getCircuitInfo() {
        getCircuit.value = Event(Unit)
    }

    fun getYearSelected(): String {
        return year.value?: Constants.CURRENT_YEAR
    }
}