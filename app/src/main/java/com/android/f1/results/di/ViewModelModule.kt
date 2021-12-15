package com.android.f1.results.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.f1.results.ui.common.viewmodels.FlagsViewModel
import com.android.f1.results.ui.constructors.ConstructorsViewModel
import com.android.f1.results.ui.drivers.DriversViewModel
import com.android.f1.results.ui.home.HomeViewModel
import com.android.f1.results.ui.result.ResultViewModel
import com.android.f1.results.ui.standings.StandingsViewModel
import com.android.f1.results.viewmodel.ViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FlagsViewModel::class)
    abstract fun bindFlagsViewModel(flagsViewModel: FlagsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResultViewModel::class)
    abstract fun bindResultViewModel(resultViewModel: ResultViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StandingsViewModel::class)
    abstract fun bindStandingsViewModel(standingsViewModel: StandingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DriversViewModel::class)
    abstract fun bindDriversViewModel(driversViewModel: DriversViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConstructorsViewModel::class)
    abstract fun bindConstructorsViewModel(constructorsViewModel: ConstructorsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
