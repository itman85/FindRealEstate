package ch.com.findrealestate.features.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ch.com.findrealestate.features.home.HomeStateViewModel
import ch.com.findrealestate.ui.screens.PropertiesList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: HomeStateViewModel) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Detail Property") })
    }) { paddingValues ->
        Text(text = "Detail Property", modifier = Modifier.padding(paddingValues))
    }
}
