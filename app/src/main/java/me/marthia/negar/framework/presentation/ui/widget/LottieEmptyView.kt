package me.marthia.negar.framework.presentation.ui.widget

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.marthia.negar.R
import me.marthia.negar.framework.presentation.theme.AppTheme

@Composable
fun LottieEmptyView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieView(
            file = "empty.json",
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text(
            text = stringResource(id = R.string.text_no_data_found),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
        )
    }
}

@Preview(
    showBackground = true,
    name = "Light Mode"
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun LottieEmptyViewPreview() {
    AppTheme {
        LottieEmptyView()
    }
}