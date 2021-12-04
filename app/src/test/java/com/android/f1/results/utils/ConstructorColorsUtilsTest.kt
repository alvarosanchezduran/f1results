package com.android.f1.results.utils

import com.android.f1.results.util.ConstructorsColors
import org.hamcrest.CoreMatchers
import org.hamcrest.core.IsNull
import org.junit.Assert
import org.junit.Test

class ConstructorColorsUtilsTest {

    @Test
    fun getColorSaved() {
        var color = ConstructorsColors.getConstructorColorSaved("alpine")
        Assert.assertThat(color, IsNull.notNullValue())
        color = ConstructorsColors.getConstructorColorSaved("")
        Assert.assertThat(color, IsNull.nullValue())
    }

    @Test
    fun getColorProvisional() {
        Assert.assertThat(ConstructorsColors.CONSTRUCTORS_COLORS_PROVISIONAL.keys.size, CoreMatchers.`is`(0))
        ConstructorsColors.CONSTRUCTORS_COLORS_PROVISIONAL["test"] = -1
        var color = ConstructorsColors.getConstructorColorProvisional("test")
        Assert.assertThat(color, IsNull.notNullValue())
        Assert.assertThat(color, CoreMatchers.`is`(-1))
    }
}