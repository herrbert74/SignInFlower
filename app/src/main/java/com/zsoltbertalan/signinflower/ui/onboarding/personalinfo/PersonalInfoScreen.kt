package com.zsoltbertalan.signinflower.ui.onboarding.personalinfo

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
fun PersonalInfoScreen(component: PersonalInfoComp) {

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
					Text("Personal Info")
				},
				navigationIcon = {
					IconButton(onClick = { component.onBackClicked() }) {
						Icon(
							imageVector = Icons.AutoMirrored.Filled.ArrowBack,
							contentDescription = "Finish",
							tint = MaterialTheme.colorScheme.onPrimaryContainer
						)
					}
				}
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
		) {
			var firstName by rememberSaveable { mutableStateOf(model.firstName) }
			var lastName by rememberSaveable { mutableStateOf(model.lastName) }
			var phoneNumber by rememberSaveable { mutableStateOf(model.phoneNumber) }
			Text(
				text = "Please enter your personal information",
				modifier = Modifier
					.padding(16.dp),

				style = SignInFlowerTypography.labelLarge
			)
			TextField(
				modifier = Modifier.padding(16.dp),
				value = firstName,
				onValueChange = {
					firstName = it
					component.firstNameChanged(it)
				},
				label = { Text("First name") }
			)
			TextField(
				modifier = Modifier.padding(16.dp),
				value = lastName,
				onValueChange = {
					lastName = it
					component.lastNameChanged(it)
				},
				label = { Text("Last name") }
			)
			TextField(
				modifier = Modifier.padding(16.dp),
				value = phoneNumber,
				onValueChange = {
					phoneNumber = it
					component.phoneNumberChanged(it)
				},
				label = { Text("Phone number") }
			)
			Spacer(modifier = Modifier.weight(1f))
			Button(
				modifier = Modifier.padding(16.dp),
				enabled = model.canContinue,
				onClick = { component.onForwardClicked() }
			) {
				Text("Continue")
			}
		}
	}

}

@Preview
@Composable
fun CredentialsScreenPreview() {
	val componentContext = DefaultComponentContext(lifecycle = LifecycleRegistry())
	PersonalInfoScreen(
		PersonalInfoComponent(componentContext, Dispatchers.Main, Dispatchers.Main, defaultSingInFlowerRepository()) {}
	)
}
