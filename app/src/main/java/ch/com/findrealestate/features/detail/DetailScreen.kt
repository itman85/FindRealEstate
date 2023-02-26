package ch.com.findrealestate.features.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(propertyId: String?, navigator: DetailNavigator) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail Screen") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Gray),
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "detail back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Text(
            text = "Detail Info for Property: ${propertyId ?: "No Id Available"}",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        )
    }
}
