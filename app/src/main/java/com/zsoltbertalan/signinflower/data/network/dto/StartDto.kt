package com.zsoltbertalan.signinflower.data.network.dto

import com.zsoltbertalan.signinflower.model.Start
import com.babestudios.base.data.mapNullInputList

data class StartDto(
	val char_id: Long,
	val name: String,
	val birthday: String? = null,
	val occupation: List<String>?  = null,
	val img: String? = null,
	val status: Status? = null,
	val nickname: String? = null,
	val appearance: List<Int>? = null,
	val portrayed: String? = null,
	val category: String? = null,
	val better_call_saul_appearance: List<Int>?  = null
)

//Remove or replace this with enumValueOf as appropriate
enum class Status(val value: String) {
	Alive("Alive"),
	Deceased("Deceased"),
	PresumedDead("Presumed dead"),
	Unknown("Unknown");

	companion object {
		fun fromValue(value: String): Status = when (value) {
			"Alive" -> Alive
			"Deceased" -> Deceased
			"Presumed dead" -> PresumedDead
			"Unknown" -> Unknown
			else -> throw IllegalArgumentException()
		}
	}
}

/**
 * TODO: This is an example mapping. Modify or remove as it fits. Remove this note afterwards.
 * Also used for inside Response class mapping.
*/
fun List<StartDto>.toStartList(): List<Start> = mapNullInputList(this) { startDto ->
	startDto.toStart(
		::mapOccupation,
		::mapAppearance,
	)
}

fun StartDto.toStart(occupationMapper: (List<String>) -> String, appearanceMapper: (List<Int>?) -> String) = Start(
	this.char_id,
	this.name,
	occupationMapper(this.occupation ?: emptyList()),
	this.img ?: "",
	this.status ?: Status.Unknown,
	this.nickname ?: "",
	appearanceMapper(this.appearance)
)

fun mapOccupation(occupationList: List<String>): String = occupationList.joinToString(", ")

fun mapAppearance(appearanceList: List<Int>?): String = appearanceList?.joinToString(", ") ?: ""
