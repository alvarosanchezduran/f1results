package com.android.f1.results.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.f1.results.util.TestsUtils
import com.android.f1.results.util.getOrAwaitValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FlagDaoTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertAndRead() {
        val flag = TestsUtils.createFlag("spain")
        db.flagsDao().insert(flag)
        val loaded = db.flagsDao().findFlagByCountryName("spain").getOrAwaitValue()
        assertThat(loaded, notNullValue())
        assertThat(loaded.size, `is`(1))
        assertThat(loaded[0].countryName, `is`("spain"))
        assertThat(loaded[0].flags.png, `is`("spain.png"))
        assertThat(loaded[0].flags.svg, `is`("spain.svg"))
    }
}