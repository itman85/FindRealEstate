package ch.com.findrealestate.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ch.com.findrealestate.R
import ch.com.findrealestate.ui.theme.FindRealEstateTheme

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(
                stringResource(R.string.loading_text)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    FindRealEstateTheme {
        Loading()
    }
}
