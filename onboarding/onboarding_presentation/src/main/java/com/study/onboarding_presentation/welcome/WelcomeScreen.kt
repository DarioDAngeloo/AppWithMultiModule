package com.study.onboarding_presentation.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.study.core.R
import com.study.core_ui.LocalSpacing
import com.study.onboarding_presentation.component.ActionButton

@Composable
fun WelcomeScreen(
    onNextClick: () -> Unit,
) {
    val space = LocalSpacing.current

    Column(
        modifier = Modifier.fillMaxSize().padding(space.spaceSmall),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome_text),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.height(space.spaceMedium))
        ActionButton(
            stringResource(id = R.string.next),
            onClick = { onNextClick() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}