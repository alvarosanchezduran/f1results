package com.android.f1.results.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.android.f1.results.repository.F1Repository
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.util.AbsentLiveData
import com.android.f1.results.vo.Resource
import javax.inject.Inject

@OpenForTesting
class HomeViewModel
@Inject constructor(f1Repository: F1Repository) : ViewModel() {
    private val _ejemplo = MutableLiveData<String?>()
    val ej: LiveData<String?>
        get() = _ejemplo

    val ejemplo: LiveData<Resource<Any>> = _ejemplo.switchMap { login ->
        if (login == null) {
            AbsentLiveData.create()
        } else {
            f1Repository.example()
        }
    }

    fun retry() {
        _ejemplo.value?.let {
            _ejemplo.value = it
        }
    }
}
