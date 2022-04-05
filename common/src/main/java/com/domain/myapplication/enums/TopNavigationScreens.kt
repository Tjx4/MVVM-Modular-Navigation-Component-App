package com.domain.myapplication.enums

import com.domain.myapplication.R

enum class TopNavigationScreens(val index: Int, val fragmentId: Int) {
    dashboard(0, R.id.dashboardFragment),
    videos(1, R.id.videosFragment),
    downloads(2, R.id.downloadsFragment)
}