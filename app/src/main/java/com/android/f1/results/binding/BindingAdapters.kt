package com.android.f1.results.binding

import androidx.databinding.BindingAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.marginStart
import androidx.core.view.updateLayoutParams

/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("imageResource")
    fun setImageSrc(imageView: ImageView, res: Int) {
        imageView.setImageResource(res)
    }

    @JvmStatic
    @BindingAdapter("margin_start")
    fun setMarginStart(view: View, dimen: Float) {
        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            marginStart = dimen.toInt()
        }
    }
}
