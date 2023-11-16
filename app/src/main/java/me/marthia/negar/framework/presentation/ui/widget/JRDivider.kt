package me.marthia.negar.framework.presentation.ui.widget

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.marthia.negar.framework.presentation.theme.AppTheme

@Composable
fun JRDivider(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp),
        color = MaterialTheme.colorScheme.outline
    )
}

@Preview("default", showBackground = true)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun DividerPreview() {
    AppTheme {
        Box(Modifier.size(height = 10.dp, width = 100.dp)) {
            JRDivider(Modifier.align(Alignment.Center))
        }
    }
}