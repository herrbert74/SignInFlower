package com.zsoltbertalan.signinflower.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.zsoltbertalan.signinflower.data.network.dto.Status
import kotlinx.parcelize.Parcelize

@Parcelize
data class Start(
	val id: Long = 0L,
	val name: String = "",
	val occupation: String = "",
	val img: String = "",
	val status: Status = Status.Unknown,
	val nickname: String = "",
	val appearance: String = "",
) : Parcelable
