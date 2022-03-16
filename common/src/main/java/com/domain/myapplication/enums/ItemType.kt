package com.domain.myapplication.enums

import com.domain.myapplication.R

enum class ItemType(typeName: String, typeIcon: Int) {
    NormalCar("", R.drawable.ic_normal_car),
    GTCar("", R.drawable.ic_gt_car),
    SuperCar("", R.drawable.ic_super_car),
    MegaCar("", R.drawable.ic_mega_car)
}