package ch.com.findrealestate.features.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import ch.com.findrealestate.components.Loading
import ch.com.findrealestate.components.PriceView
import ch.com.findrealestate.components.SmgErrorView
import ch.com.findrealestate.components.SmgImage
import ch.com.findrealestate.domain.entity.Property
import ch.com.findrealestate.features.home.components.similarproperties.HomeSimilarPropertiesComponent
import ch.com.findrealestate.features.home.components.similarproperties.redux.HomeSimilarPropertiesAction
import ch.com.findrealestate.features.home.redux.HomeAction
import ch.com.findrealestate.features.home.redux.HomeState
import ch.com.findrealestate.ui.theme.FindRealEstateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navigator: HomeNavigator) {
    val viewModel: HomeStateViewModel =
        hiltViewModel<HomeStateViewModel>().apply { this.setNavigator(navigator) }

    val homeStateValue =
        viewModel.rememberState().value

    Scaffold(topBar = {
        TopAppBar(title = { Text("Real Estate") })
    }) { paddingValues ->
        PropertiesList(
            homeStateValue,
            paddingValues,
            favoriteClick = { id -> viewModel.dispatch(HomeAction.FavoriteClick(id)) },
            propertyClick = { id -> viewModel.dispatch(HomeAction.PropertyClick(id)) },
            similarPropertyClick = { id ->
                viewModel.dispatch(
                    HomeSimilarPropertiesAction.SimilarPropertyClick(
                        id
                    )
                )
            },
            favoriteDialogYesClick = { viewModel.dispatch(HomeAction.FavoriteDialogYesClick) },
            confirmRemoveFavoriteDialogYes = { id ->
                viewModel.dispatch(HomeAction.ConfirmRemoveFavoriteYesClick(id))
            },
            confirmRemoveFavoriteDialogNo = { viewModel.dispatch(HomeAction.ConfirmRemoveFavoriteNoClick) })
    }
    DisposableEffect(Unit) {
        onDispose {
            Log.d("Home", "home composable disposed")
        }
    }
}

@Composable
fun PropertiesList(
    homeState: HomeState,
    paddingValues: PaddingValues,
    favoriteClick: (String) -> Unit,
    propertyClick: (String) -> Unit,
    similarPropertyClick: (String) -> Unit,
    favoriteDialogYesClick: () -> Unit,
    confirmRemoveFavoriteDialogYes: (String) -> Unit,
    confirmRemoveFavoriteDialogNo: () -> Unit
) {
    val scrollableState = rememberLazyListState()
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        state = scrollableState,
        modifier = Modifier.padding(paddingValues)
    ) {
        item {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
        }
        when (homeState) {
            is HomeState.Loading -> {
                item {
                    Loading()
                }
            }
            is HomeState.Error -> {
                item {
                    SmgErrorView()
                }
            }
            is HomeState.PropertiesLoaded,
            is HomeState.AddFavoriteSuccessful,
            is HomeState.ConfirmFavoriteRemoved,
            is HomeState.PropertiesListUpdated -> {
                val homeItemsList = homeState.items
                items(
                    count = homeItemsList.size,
                    key = { index -> homeItemsList[index].itemId }) { index ->
                    homeItemsList[index].let { homeItem ->
                        when (homeItem) {
                            is HomeItem.PropertyItem -> PropertyItem(
                                homeItem.property,
                                toggleFavorite = favoriteClick,
                                propertyClick = propertyClick
                            )
                            is HomeItem.SimilarPropertiesItem -> HomeSimilarPropertiesComponent(
                                properties = homeItem.properties,
                                onItemClick = { similarPropertyClick(it.id) }
                            )
                        }
                        Divider()
                    }
                }
            }
            else -> {
                Log.i("Home", "Not update ui for this state $homeState")
            }
        }
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }
    if (homeState is HomeState.AddFavoriteSuccessful) {
        AddFavoriteSuccessfulDialog(
            property = homeState.favoriteProperty,
            onYesClick = favoriteDialogYesClick
        )
    } else if (homeState is HomeState.ConfirmFavoriteRemoved) {
        ConfirmFavoriteRemovedDialog(
            propertyId = homeState.propertyId,
            confirmRemoveFavoriteDialogYes,
            confirmRemoveFavoriteDialogNo
        )
    }

}

@Composable
fun AddFavoriteSuccessfulDialog(
    property: Property,
    onYesClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onYesClick() // same logic to yes click
        },
        title = { Text("Add favorite for property id ${property.id} successfully!") },
        confirmButton = {
            TextButton(onClick = {
                // Handle yes button click
                onYesClick()
            }) {
                Text("Close")
            }
        }
    )
}

@Composable
fun ConfirmFavoriteRemovedDialog(
    propertyId: String,
    onYesClick: (String) -> Unit,
    onNoClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onNoClick() // same logic to No click
        },
        title = { Text("Are you sure you want to remove favorite for property id  $propertyId ?") },
        confirmButton = {
            TextButton(onClick = {
                // Handle yes button click
                onYesClick(propertyId)
            }) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                // Handle undo button click
                onNoClick()
            }) {
                Text("No")
            }
        }
    )
}

@Composable
fun PropertyItem(
    property: Property,
    toggleFavorite: (String) -> Unit,
    propertyClick: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .clickable { propertyClick(property.id) }
    ) {
        Box {
            Box(
                modifier = Modifier
                    .padding(top = 20.dp, end = 10.dp)
                    .zIndex(1f)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
                    .align(Alignment.TopEnd)
                    .clickable {
                        toggleFavorite(property.id)
                    }
            ) {
                if (property.isFavorite) {
                    Icon(
                        Icons.Outlined.Favorite,
                        contentDescription = "Favorite",
                        tint = Color.Red,
                        modifier = Modifier.padding(10.dp)
                    )
                } else {
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
            SmgImage(
                property.imageUrl,
                modifier = Modifier.padding(top = 10.dp)
            )
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.BottomStart)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(6.dp)
            ) {
                PriceView(
                    property.price
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            property.title,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            property.address,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PropertyItemPreview() {
    val property = Property(
        id = "id",
        imageUrl = "https://media2.homegate.ch/listings/heia/104123262/image/8944c80cb8afb8d5d579ca4faf7dbbb4.jpg",
        title = "This is a title",
        price = 1900L,
        address = "Bern",
        isFavorite = true
    )
    FindRealEstateTheme {
        PropertyItem(property, toggleFavorite = { }, propertyClick = {})
    }
}
