package com.domain.myapplication.enums

import com.domain.myapplication.R

enum class ItemCategories(typeId: Int, typeName: String, typeIcon: Int) {
    NormalCar(1,"Normal Car", R.drawable.ic_normal_car),
    GTCar(2, "GT", R.drawable.ic_gt_car),
    SuperCar(3, "Super Car", R.drawable.ic_super_car),
    MegaCar(4, "Mega Car", R.drawable.ic_mega_car)
}