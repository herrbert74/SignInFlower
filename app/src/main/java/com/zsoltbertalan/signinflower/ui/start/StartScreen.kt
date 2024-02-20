package com.zsoltbertalan.signinflower.ui.start

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
fun StartScreen(component: StartComp) {

	val model by component.state.subscribeAsState()

	CircularProgressIndicator()

	if (model.isLoggedIn == true) {
		component.onPushMain()
	} else if (model.isLoggedIn == false) {
		component.onPushWelcome()
	}

}
