package ch.com.findrealestate.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import ch.com.findrealestate.ui.theme.FindRealEstateTheme
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun SmgImage(urlToImage: String, modifier: Modifier) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(urlToImage)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build(),
        loading = { CircularProgressIndicator() },
        error = { Text("error loading image") },
        contentDescription = "url image",
        contentScale = ContentScale.FillBounds,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun SmgImagePreview() {
    FindRealEstateTheme {
        SmgImage(
            urlToImage = "https://media2.homegate.ch/listings/" +
                "heia/104123262/image/8944c80cb8afb8d5d579ca4faf7dbbb4.jpg",
            modifier = Modifier
        )
    }
}
