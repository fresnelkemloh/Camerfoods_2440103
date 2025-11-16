package com.example.camerfoods

import android.R.attr.contentDescription
import android.os.Bundle
import android.provider.Settings
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.camerfoods.ui.theme.CamerFoodsTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.annotations.ApiStatus

sealed class Screen(
    val route: String,
    val titleResource: Int,
    val icon: ImageVector
) {
    object  Home : Screen(
        route = "home",
        titleResource = R.string.home,
        icon = Icons.Default.Home
    )
    object Settings : Screen(
        route = "settings",
        titleResource = R.string.home,
        icon =  Icons.Default.Settings
    )

    companion object{
        val items = listOf(Home,Settings)
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CamerFoodsTheme {
                 AppNavigation()
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome from CamerFoods",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Nous vous souhaitons la bienvenu dans l'application dedie aux recettes africaine et en particulier les recettes camerounaise , vous y decouvrierez de nombreuses recette sans manquer les plus connus des camerounais . nous vous souhaitons une bonne exploration de nos rectte",
            style  = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(onClick = {
            navController.navigate("liste_recettes")
            {
                popUpTo(navController.graph.startDestinationId){
                    saveState = true
                }
                launchSingleTop = true
                restoreState = false
            }
        }) {
            Text("liste des recettes")
        }
    }
}

@Composable
fun  SettingsScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
        Icons.Default.Settings,
        contentDescription = null,
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Changer la langue",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun RecetteScreen(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text("Liste des Recettes",
            style = MaterialTheme.typography.headlineMedium)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CamerFoods")}
            )
        },
        bottomBar = {
            NavigationBar {
                Screen.items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                screen.icon,
                                contentDescription = stringResource(screen.titleResource)
                            )
                        },
                        label = {Text(stringResource(screen.titleResource))},
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route){
                HomeScreen(navController)
            }
            composable(Screen.Home.route){
                SettingsScreen()
            }
            composable("liste_recettes"){
                RecetteScreen()
            }
        }
    }
}


@Preview
@Composable
fun AppPreview(){
    CamerFoodsTheme{
        HomeScreen(rememberNavController())
    }
}