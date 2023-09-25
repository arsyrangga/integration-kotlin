package com.rangga.submission.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Account(
    val id: String?,
    val avatar: String?
): Parcelable