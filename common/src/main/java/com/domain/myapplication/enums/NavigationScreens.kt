package com.domain.myapplication.enums

import com.domain.myapplication.R

enum class NavigationScreens(val index: Int, val fragmentId: Int) {
    Dashboard(0, R.id.dashboardScreen),
    Videos(1, R.id.videosScreen),
    Latest(2, R.id.latestScreen),
    Downloads(3, R.id.downloadsScreen),
    More(4, R.id.moreScreen)
}