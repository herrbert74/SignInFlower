package com.zsoltbertalan.signinflower.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.zsoltbertalan.signinflower.design.SignInFlowerTheme
import com.zsoltbertalan.signinflower.design.SignInFlowerTypography
import com.zsoltbertalan.signinflower.ui.defaultSingInFlowerRepository
import kotlinx.coroutines.Dispatchers

@Composable
fun MainScreen(component: MainComp) {

	val model by component.state.subscribeAsState()

	BackHandler(onBack = { component.onBackClicked() })

	Scaffold(
		topBar = {
			TopAppBar(
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = SignInFlowerTheme.colorScheme.primaryContainer,
					titleContentColor = SignInFlowerTheme.colorScheme.primary,
				),
				title = {
					Text("Main")
				},
			)
		}
	) { innerPadding ->
		if (model.isLoading) {
			Box(
				contentAlignment = Alignment.Center,
				modifier = Modifier.fillMaxSize()
			) {
				CircularProgressIndicator()
			}
		} else if (model.isSignedOut) {
			component.onSignOut()
		} else {
			Column(
				modifier = Modifier
					.padding(innerPadding)
					.fillMaxSize()
			) {
				Text(
					text = "Hello, ${model.firstName} ${model.lastName}",
					modifier = Modifier
						.padding(16.dp)
						.weight(1f),

					style = SignInFlowerTypography.bodyLarge
				)
				Button(
					modifier = Modifier.padding(16.dp),
					onClick = { component.onSignOutClicked() }
				) {
					Text("Sign Out")
				}
			}
		}
	}

}

@Preview
@Composable
fun MainScreenPreview() {
	val componentContext = DefaultComponentContext(lifecycle = LifecycleRegistry())
	SignInFlowerTheme {
		MainScreen(
			MainComponent(componentContext, defaultSingInFlowerRepository(), Dispatchers.Main, Dispatchers.Main, {}) {}
		)
	}
}
