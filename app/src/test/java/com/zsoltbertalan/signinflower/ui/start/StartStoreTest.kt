package com.zsoltbertalan.signinflower.ui.start

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.babestudios.base.kotlin.ext.test
import com.zsoltbertalan.signinflower.data.network.SignInFlowerRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class StartStoreTest {

	private val signInFlowerRepository = mockk<SignInFlowerRepository>(relaxed = true)

	private lateinit var startStore: Store<Nothing, State, Nothing>

	private val testCoroutineDispatcher = StandardTestDispatcher()

	@Before
	fun setUp() {

		startStore = startStore(
			DefaultStoreFactory(),
			testCoroutineDispatcher,
			testCoroutineDispatcher,
			signInFlowerRepository
		)
	}

	@Test
	fun `when user signed in then dispatch signed in message`() = runTest(testCoroutineDispatcher) {

		coEvery { signInFlowerRepository.isUserSignedIn() } answers { true }

		val states = startStore.states.test()
		advanceUntilIdle()
		coVerify(exactly = 1) { signInFlowerRepository.isUserSignedIn() }
		states.last().isLoggedIn shouldBe true
	}

	@Test
	fun `when user not signed in then dispatch not signed in message`() = runTest(testCoroutineDispatcher) {

		coEvery { signInFlowerRepository.isUserSignedIn() } answers { false }

		val states = startStore.states.test()
		advanceUntilIdle()
		coVerify(exactly = 1) { signInFlowerRepository.isUserSignedIn() }
		states.last().isLoggedIn shouldBe false
	}

}


