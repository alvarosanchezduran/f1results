package com.android.f1.results.di

import com.android.f1.results.ui.home.HomeFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): HomeFragment
}
