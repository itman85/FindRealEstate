package ch.com.findrealestate.features.detail

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.com.findrealestate.components.Loading
import ch.com.findrealestate.domain.entity.PropertyDetail
import ch.com.findrealestate.features.detail.redux.DetailAction
import ch.com.findrealestate.features.detail.redux.DetailState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(propertyId: String?, navigator: DetailNavigator) {
    val viewModel: DetailStateViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        propertyId?.let { viewModel.dispatch(DetailAction.LoadDetailData(it)) }
    }
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
        if (propertyId != null) {
            val detailState by viewModel.rememberState()
            val modifier = Modifier.padding(paddingValues)
            when (detailState) {
                is DetailState.DetailDataLoading -> DetailLoadingComponent(modifier, propertyId)
                is DetailState.DetailDataLoadedError -> DetailLoadedErrorComponent(
                    modifier,
                    propertyId,
                    (detailState as DetailState.DetailDataLoadedError).errorMsg
                )
                is DetailState.DetailDataLoaded -> DetailInfoComponent(
                    modifier = modifier,
                    propertyDetail = (detailState as DetailState.DetailDataLoaded).propertyDetail
                )
                else -> {
                    // do nothing
                }
            }
        } else {
            Text(
                text = "Property Id invalid",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(paddingValues)
            )
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            Log.d("Phan", "detail composable disposed")
        }
    }
}

@Composable
fun DetailLoadingComponent(modifier: Modifier, propertyId: String) {
    Column(modifier = modifier) {
        Text(
            text = "Loading detail data for Property Id $propertyId",
            textAlign = TextAlign.Center,
            modifier = modifier
        )
        Spacer(modifier = modifier.padding(top = 16.dp))
        Loading()
    }
}

@Composable
fun DetailLoadedErrorComponent(modifier: Modifier, propertyId: String, errMsg: String) {
    Text(
        text = "Load detail data for Property Id $propertyId Error $errMsg",
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
fun DetailInfoComponent(modifier: Modifier, propertyDetail: PropertyDetail) {
    Column(modifier = modifier) {
        Text(text = propertyDetail.title)
        Spacer(modifier = modifier.padding(top = 8.dp))
        Text(text = propertyDetail.description)
    }
}
