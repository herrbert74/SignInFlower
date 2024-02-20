package com.zsoltbertalan.signinflower.ui.main

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.babestudios.base.kotlin.ext.test
import com.zsoltbertalan.signinflower.domain.api.SignInFlowerRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MainStoreTest {

	private val signInFlowerRepository = mockk<SignInFlowerRepository>(relaxed = true)

	private lateinit var mainStore: Store<Intent, State, Nothing>

	private val testCoroutineDispatcher = StandardTestDispatcher()

	@Before
	fun setUp() {

		coEvery { signInFlowerRepository.getUserFirstName() } answers { "Zsolt" }
		coEvery { signInFlowerRepository.getUserLastName() } answers { "Bertalan" }

		mainStore = mainStore(
			DefaultStoreFactory(),
			testCoroutineDispatcher,
			testCoroutineDispatcher,
			signInFlowerRepository
		)
	}

	@Test
	fun `when user signed in then dispatch signed in message`() = runTest(testCoroutineDispatcher) {

		val states = mainStore.states.test()
		advanceUntilIdle()
		coVerify(exactly = 1) { signInFlowerRepository.getUserFirstName() }
		coVerify(exactly = 1) { signInFlowerRepository.getUserLastName() }
		states.last().firstName shouldBe "Zsolt"
		states.last().lastName shouldBe "Bertalan"
	}

	@Test
	fun `when user not signed in then dispatch not signed in message`() = runTest(testCoroutineDispatcher) {

		coEvery { signInFlowerRepository.deleteAllUserData() } answers { }

		val states = mainStore.states.test()

		mainStore.accept(Intent.SignOut)

		advanceUntilIdle()
		coVerify(exactly = 1) { signInFlowerRepository.deleteAllUserData() }
		states.last().isSignedOut shouldBe true
	}

}


