package ch.com.findrealestate.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ch.com.findrealestate.ui.theme.FindRealEstateTheme

@Composable
fun PriceView(price: Long,) {
    Text(
        text = "$price €"
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PriceViewPreview() {
    FindRealEstateTheme {
        PriceView(1000L)
    }
}
