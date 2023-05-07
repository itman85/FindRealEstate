package ch.com.findrealestate.features.home.components.similarproperties

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.com.findrealestate.components.SmgImage
import ch.com.findrealestate.domain.entity.Property
import ch.com.findrealestate.ui.theme.FindRealEstateTheme

@Composable
fun HomeSimilarPropertiesComponent(properties: List<Property>, onItemClick: (Property) -> Unit) {
    Column {
        Text(
            text = "Similar properties",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge,
        )
        LazyRow {
            items(properties, key = { it.id }) {
                PropertyCard(property = it, onClick = onItemClick)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyCard(modifier: Modifier = Modifier, property: Property, onClick: (Property) -> Unit) {
    Card(
        modifier = modifier
            .width(250.dp)
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(),
        onClick = { onClick(property) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
            contentAlignment = Alignment.Center
        ) {
            SmgImage(
                property.imageUrl, modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Column(modifier = Modifier.height(100.dp)) {
            Text(
                text = property.title,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            Text(
                text = "${property.price}$",
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
            )
        }

    }
}



@Preview
@Composable
fun HomeSimilarPropertiesComponent_Preview() {
    FindRealEstateTheme {
        HomeSimilarPropertiesComponent(createPropertiesData(), {})
    }
}

fun createPropertiesData(): List<Property> {
    return listOf(
        Property(
            id = "1",
            imageUrl = "https://media2.homegate.ch/listings/heia/3001688318/image/f42f1c6550d215ffa3380beb5ddeb957.jpg",
            title = "property 1",
            price = 100000,
            address = "",
            isFavorite = false
        ),
        Property(
            id = "2",
            imageUrl = "https://media2.homegate.ch/listings/heia/3001688318/image/e67641cb83c9081535bb95a0b7ffe265.jpg",
            title = "property 2",
            price = 100000,
            address = "",
            isFavorite = false
        ),
        Property(
            id = "3",
            imageUrl = "https://media2.homegate.ch/listings/heia/3001688318/image/bbb5b2fb8a1cf58ce690e0cfca23d266.jpg",
            title = "property 3",
            price = 100000,
            address = "",
            isFavorite = false
        )
    )
}
