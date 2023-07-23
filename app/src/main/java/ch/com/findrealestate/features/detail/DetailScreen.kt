package ch.com.findrealestate.features.detail

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import ch.com.findrealestate.components.Loading
import ch.com.findrealestate.domain.entity.PropertyDetail
import ch.com.findrealestate.features.detail.redux.DetailAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(propertyId: String?, navigator: DetailNavigator) {
    val viewModel: DetailStateViewModel =
        hiltViewModel<DetailStateViewModel>().apply { this.setNavigator(navigator) }
    val detailState by viewModel.rememberState()

    LaunchedEffect(Unit) {
        propertyId?.let { viewModel.startLoadData(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail Screen") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Gray),
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "detail back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (propertyId != null) {

            val modifier = Modifier.padding(paddingValues)
            when {
                detailState.isLoadingState() -> DetailLoadingComponent(modifier, propertyId)
                detailState.isErrorState() -> DetailLoadedErrorComponent(
                    modifier,
                    propertyId,
                    detailState.errorMsg!!
                )
                detailState.isDataLoaded() -> DetailInfoComponent(
                    modifier = modifier,
                    propertyDetail = detailState.detailProperty!!,
                    onOpenWebsiteClick = { viewModel.dispatch(DetailAction.OpenPropertyWebsiteClick) }
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

    if (detailState.isShowInfoBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.dispatch(
                    DetailAction.ToggleShowPropertyInfoBottomSheet(
                        false
                    )
                )
            },
            //  sheetState = modalSheetState,
            shape = RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = "Display some property info")
                Button(
                    onClick = {
                        // coroutineScope.launch { modalSheetState.hide() }
                        viewModel.dispatch(DetailAction.ToggleShowPropertyInfoBottomSheet(false))
                    }
                ) {
                    Text(text = "Hide Sheet")
                }
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    // Handle start event
                    Log.d("Phan", "Activity resume")
                    viewModel.dispatch(DetailAction.ScreenResumed(viewModel.navigationValue))
                }
                Lifecycle.Event.ON_STOP -> {
                    // Handle stop event
                    Log.d("Phan", "Activity Stop")
                }
                // Handle other lifecycle events as needed
                else -> {
                    Log.d("Phan", "Activity in other states")
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            Log.d("Phan", "detail composable disposed")
            lifecycleOwner.lifecycle.removeObserver(observer)
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
fun DetailInfoComponent(
    modifier: Modifier,
    propertyDetail: PropertyDetail,
    onOpenWebsiteClick: () -> Unit
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState()).testTag("detail_data")) {
        Text(text = propertyDetail.title)
        Spacer(modifier = modifier.padding(top = 8.dp))
        Button(onClick = onOpenWebsiteClick) {
            Text(text = "Open property website")
        }
        Spacer(modifier = modifier.padding(top = 8.dp))
        Text(text = propertyDetail.description)
    }
}

