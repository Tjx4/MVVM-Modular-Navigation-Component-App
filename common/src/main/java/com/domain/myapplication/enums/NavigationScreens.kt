package com.domain.myapplication.enums

import com.domain.myapplication.R

enum class NavigationScreens(val index: Int, val fragmentId: Int) {
    dashboard(0, R.id.dashboardScreen),
    videos(1, R.id.videosScreen),
    downloads(2, R.id.downloadsScreen)
}