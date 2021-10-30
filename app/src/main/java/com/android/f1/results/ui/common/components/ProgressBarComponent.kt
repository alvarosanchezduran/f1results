package com.android.f1.results.ui.common.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.f1.results.R

class ProgressBarComponent(context: Context, attributeSet: AttributeSet?) :
    LinearLayout(context, attributeSet) {

    init {
        LayoutInflater.from(context).inflate(R.layout.component_progress_bar, this, true)
    }

    fun setProgressBarVisibility(visible: Boolean?) {
        visible?.let {
            findViewById<ConstraintLayout>(R.id.progress_container).apply {
                visibility = if (visible) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
    }
}