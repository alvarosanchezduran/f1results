package com.android.f1.results.di

import com.android.f1.results.ui.standings.ConstructorsStandingFragment
import com.android.f1.results.ui.standings.DriversStandingFragment
import com.android.f1.results.ui.currentseason.CurrentSeasonFragment
import com.android.f1.results.ui.currentseason.HistoricalSeasonFragment
import com.android.f1.results.ui.drivers.DriversFragment
import com.android.f1.results.ui.drivers.detail.DriverDetailFragment
import com.android.f1.results.ui.home.HomeFragment
import com.android.f1.results.ui.result.QualifyingFragment
import com.android.f1.results.ui.result.RaceFragment
import com.android.f1.results.ui.result.ResultFragment
import com.android.f1.results.ui.results.ResultsFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeCurrentFragment(): CurrentSeasonFragment

    @ContributesAndroidInjector
    abstract fun contributeHistoricalFragment(): HistoricalSeasonFragment

    @ContributesAndroidInjector
    abstract fun contributeDriversClasificationFragment(): DriversStandingFragment

    @ContributesAndroidInjector
    abstract fun contributeConstructorsClasificationFragment(): ConstructorsStandingFragment

    @ContributesAndroidInjector
    abstract fun contributeResultFragment(): ResultFragment

    @ContributesAndroidInjector
    abstract fun contributeQualifyingFragment(): QualifyingFragment

    @ContributesAndroidInjector
    abstract fun contributeRaceFragment(): RaceFragment

    @ContributesAndroidInjector
    abstract fun contributeResultsFragment(): ResultsFragment

    @ContributesAndroidInjector
    abstract fun contributeDriversFragment(): DriversFragment

    @ContributesAndroidInjector
    abstract fun contributeDriverDetailFragment(): DriverDetailFragment
}
