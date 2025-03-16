package com.example.todolistapp.ui.appbars

import android.icu.text.AlphabeticIndex.Bucket.LabelType
import androidx.compose.ui.graphics.vector.ImageVector

data class TabItem(
    val label: String,
    val icon: ImageVector,
    val route:String
)