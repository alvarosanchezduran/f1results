package com.android.f1.results.util

import android.view.View

interface SupportActionManager {
    fun setToolbarTitle(title: String, iconSrc: Int? = null, iconOnClick: View.OnClickListener? = null)
}