package com.study.onboarding_presentation.weight

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.study.core.R
import com.study.core.util.UiEvent
import com.study.core_ui.LocalSpacing
import com.study.onboarding_presentation.component.ActionButton
import com.study.onboarding_presentation.component.UnitTextField
import kotlinx.coroutines.flow.collect


@Composable
fun WeightScreen(
    scaffoldState: ScaffoldState,
    onNextClick: () -> Unit,
    weightViewModel: WeightViewModel = hiltViewModel(),
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        weightViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onNextClick()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_weight),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = weightViewModel.weight,
                onValueChange = weightViewModel::onWeightEnter,
                unit = stringResource(id = R.string.kg)
            )
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = weightViewModel::onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}