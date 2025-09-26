package com.example.safe_clothing

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.safe_clothing.ui.theme.SafeclothingTheme
import java.io.IOException
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SafeclothingTheme {
                MainScreen()
            }
        }
    }
}

// --- Data Models ---
data class Product(
    val id: Int,
    val title: String,
    val imageName: String, // e.g., "1.jpeg"
    val price: Double
)

// --- Screen Definitions ---
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Cart : Screen("cart", "Cart", Icons.Filled.ShoppingCart)
    object About : Screen("about", "About", Icons.Filled.Info)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Cart,
    Screen.About
)

// --- App's Main Composable ---
@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val productList = remember {
        (1..18).map {
            Product(
                id = it,
                title = "Product $it",
                imageName = "$it.jpeg",
                price = Random.nextDouble(500.0, 800.01)
            )
        }
    }
    val cartItems = remember { mutableStateListOf<Product>() }

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentScreen == screen,
                        onClick = { currentScreen = screen }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (currentScreen) {
                Screen.Home -> HomeScreen(products = productList, onAddToCartClick = { product -> cartItems.add(product) })
                Screen.Cart -> CartScreen(cartItems) // We'll pass cartItems here later
                Screen.About -> AboutScreen()
            }
        }
    }
}

// --- Screen Composable: Home ---
@Composable
fun HomeScreen(products: List<Product>, onAddToCartClick: (Product) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) { product ->
            ProductCard(product = product, onAddToCartClick = onAddToCartClick)
        }
    }
}

// --- Composable for a single Product Card ---
@Composable
fun ProductCard(product: Product, onAddToCartClick: (Product) -> Unit) {
    val context = LocalContext.current
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(product.imageName) {
        try {
            context.assets.open("images/${product.imageName}").use { inputStream ->
                imageBitmap = BitmapFactory.decodeStream(inputStream).asImageBitmap()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = product.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            } ?: Box( 
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Loading Image...")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "%.2f EGP".format(product.price), style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onAddToCartClick(product) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Add to Cart")
            }
        }
    }
}


// --- Screen Composable: Cart ---
@Composable
fun CartScreen(cartItems: List<Product>) { // Updated to accept cartItems
    if (cartItems.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Your cart is empty.")
        }
    } else {
        // We will build the cart item display later
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
             Text("Cart Screen - ${cartItems.size} item(s). We will display them soon.")
        }
    }
}

// --- Screen Composable: About ---
@Composable
fun AboutScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("About Screen - Brand info and logo will be here")
    }
}

// --- Preview ---
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SafeclothingTheme {
        val sampleProducts = listOf(
            Product(1, "Sample Product 1", "1.jpeg", 550.0),
            Product(2, "Sample Product 2", "2.jpeg", 750.0)
        )
        HomeScreen(sampleProducts, onAddToCartClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    SafeclothingTheme {
        MainScreen()
    }
}
