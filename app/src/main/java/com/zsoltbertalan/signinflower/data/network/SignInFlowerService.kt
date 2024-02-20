package com.zsoltbertalan.signinflower.data.network

import com.zsoltbertalan.signinflower.data.network.dto.StartDto
import retrofit2.http.GET

interface SignInFlowerService {
	@GET("api/start")
	suspend fun getStart(): List<StartDto>
}