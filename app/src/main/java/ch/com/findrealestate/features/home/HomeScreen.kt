package ch.com.findrealestate.ui.screens

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ch.com.findrealestate.components.Loading
import ch.com.findrealestate.components.PriceView
import ch.com.findrealestate.components.SmgErrorView
import ch.com.findrealestate.components.SmgImage
import ch.com.findrealestate.domain.entity.Property
import ch.com.findrealestate.features.home.redux.HomeAction
import ch.com.findrealestate.features.home.redux.HomeState
import ch.com.findrealestate.features.home.HomeStateViewModel
import ch.com.findrealestate.ui.theme.FindRealEstateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeStateViewModel) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Real Estate") })
    }) { paddingValues ->
        PropertiesList(viewModel, paddingValues)
    }
}

@Composable
fun PropertiesList(viewModel: HomeStateViewModel, paddingValues: PaddingValues) {
    val homeState by viewModel.rememberState()

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
            is HomeState.PropertiesListUpdated -> {
                val propertiesList = homeState!!.properties
                items(
                    count = propertiesList.size,
                    key = { index -> propertiesList[index].id }) { index ->
                    propertiesList[index].let { property ->
                        PropertyItem(
                            property,
                            toggleFavorite = { id ->
                                viewModel.dispatch(HomeAction.FavoriteClick(id))
                            },
                            propertyClick = { id ->
                                viewModel.dispatch(HomeAction.PropertyClick(id))
                            }
                        )
                        Divider()
                    }
                }
            }
            else -> {
                Log.i("HomeScreen", "Not update ui for this state $homeState")
            }
        }
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }
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
        address = "Ha noi, Viet nam",
        isFavorite = true
    )
    FindRealEstateTheme {
        PropertyItem(property, toggleFavorite = { }, propertyClick = {})
    }
}
