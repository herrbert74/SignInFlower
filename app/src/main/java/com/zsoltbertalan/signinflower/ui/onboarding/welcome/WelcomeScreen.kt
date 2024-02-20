package com.zsoltbertalan.signinflower.ui.onboarding.welcome

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.zsoltbertalan.signinflower.design.SignInFlowerTheme
import com.zsoltbertalan.signinflower.design.SignInFlowerTypography
import kotlinx.coroutines.Dispatchers

@Composable
fun WelcomeScreen(component: WelcomeComp) {

	BackHandler(onBack = { component.onBackClicked() })

	Scaffold(
		topBar = {
			TopAppBar(
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = SignInFlowerTheme.colorScheme.primaryContainer,
					titleContentColor = SignInFlowerTheme.colorScheme.primary,
				),
				title = {
					Text("Welcome")
				},
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
		) {
			Text(
				text = "Please register",
				modifier = Modifier
					.padding(16.dp)
					.weight(1f),

				style = SignInFlowerTypography.titleLarge
			)
			Button(
				modifier = Modifier.padding(16.dp),
				onClick = { component.onForwardClicked() }
			) {
				Text("Continue")
			}
		}
	}

}

@Preview
@Composable
fun WelcomeScreenPreview() {
	val componentContext = DefaultComponentContext(lifecycle = LifecycleRegistry())
	WelcomeScreen(
		WelcomeComponent(componentContext, Dispatchers.Main, {}) {}
	)
}
