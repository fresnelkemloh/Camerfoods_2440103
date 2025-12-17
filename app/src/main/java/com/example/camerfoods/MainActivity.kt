package com.example.camerfoods

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.camerfoods.ui.theme.CamerFoodsTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.annotations.TestOnly

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
        titleResource = R.string.settings,
        icon =  Icons.Default.Settings
    )

    object Listes: Screen(
        route = "listes",
        titleResource = R.string.shop,
        icon = Icons.Default.ShoppingCart
    )

    companion object{
        val items = listOf(Home,Settings, Listes)
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(false)}
            CamerFoodsTheme(darkTheme = isDarkTheme) {
                 AppNavigation(
                     isDarkTheme = isDarkTheme,
                     onThemeChange = {isDarkTheme= !isDarkTheme}
                 )
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.image_fond),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )
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
                color = Color.White
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Nous vous souhaitons la bienvenu dans l'application dedie aux recettes africaine et en particulier les recettes camerounaise , vous y decouvrierez de nombreuses recette sans manquer les plus connus des camerounais . nous vous souhaitons une bonne exploration de nos rectte",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(onClick = {
                navController.navigate("liste_recettes")
                {
                    popUpTo(navController.graph.startDestinationId) {
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
}
//////////////////////
@Composable
fun  SettingsScreen(
    isDarkTheme: Boolean,
    onThemeChange:() -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.Settings,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Changer la langue",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SelectionLangue()

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Changer le theme",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Spacer(modifier= Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Mode sombre",
                style = MaterialTheme.typography.headlineSmall
            )
            Switch(
                checked = isDarkTheme,
                onCheckedChange = {onThemeChange()}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionLangue(){
    val langages = listOf("Francais","Anglais","Espagnol")
    var expanded by remember { mutableStateOf(false) }
    var langueSelectionner by rememberSaveable { mutableStateOf(langages[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}
    ) { TextField(
        value = langueSelectionner,
        onValueChange = {},
        readOnly = true,
        label = {Text("Choisir une langue")},
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
        },
        modifier = Modifier
            .menuAnchor()
            .fillMaxWidth()
    )
    ExposedDropdownMenu(
        expanded = expanded,
        onDismissRequest = {expanded = false}
    ) {
        langages.forEach { langage ->
            DropdownMenuItem(
                text = {Text(langage)},
                onClick = {
                    langueSelectionner = langage
                    expanded = false
                }
            )
        }
    }}
}

@Composable
fun RecetteScreen(context: Context,
                  modifier: Modifier = Modifier,
                  showFavoritesOnly: Boolean = false
){
    var listerecettes by remember { mutableStateOf(emptyList<Recette>()) }
    var recherche by rememberSaveable{mutableStateOf("")}
    LaunchedEffect(Unit) {
        listerecettes = context.lireRecettes()
    }

    fun toggleFavori(recetteId: Int){
        val nouvelleListe = listerecettes.map { recette ->
            if (recette.id == recetteId){
                recette.copy(favoris = !recette.favoris)
            }else{
                recette
            }
        }
        listerecettes = nouvelleListe
        context.sauvegardeRecettes(nouvelleListe)
    }
    val recettesFiltrer = listerecettes.filter { recette ->
     val filtreRecherche =   recette.titre.contains(recherche, ignoreCase = true) || recette.description.contains(recherche, ignoreCase = true)
        val filtreFavori = if (showFavoritesOnly) recette.favoris else true
        filtreRecherche && filtreFavori
    }
    Column(modifier = modifier.fillMaxSize()) {
        if (showFavoritesOnly){
            Text(
                text = "Vos favoris",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        } else {
        OutlinedTextField(
            value = recherche,
            onValueChange = {recherche=it},
            label = {Text("Rechercher une recette")},
            leadingIcon = {Icon(Icons.Default.Search,contentDescription = null)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true
        )
            }
        if (recettesFiltrer.isEmpty()){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(
                    text = if (showFavoritesOnly) "Aucun favoris enregistrer"
                    else "aucune recette",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            ) {
                items(recettesFiltrer) { recette ->
                    FicheRecette(
                        recette = recette,
                        onFavoriClick = { toggleFavori(recette.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun FicheRecette(
    recette: Recette,
    onFavoriClick:()-> Unit
){
    val context = LocalContext.current
    var estEtendu by remember {mutableStateOf(false)}
    val idRessourceImage = remember(recette.image) {
        val id = context.resources.getIdentifier(recette.image,"drawable",context.packageName)
        if (id!=0) id else R.drawable.ic_launcher_foreground
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { estEtendu = !estEtendu },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = idRessourceImage),
                    contentDescription = recette.titre,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = recette.titre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = recette.temps,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                IconButton(onClick = onFavoriClick) {
                    Icon(
                        imageVector = if (recette.favoris) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favori",
                        tint = if (recette.favoris) Color.Red else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = recette.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if(estEtendu) Int.MAX_VALUE else 2
            )
            if (estEtendu){
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Ingredients :",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                recette.ingredients.forEach { ingredient ->
                    Text("• $ingredient", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Preparation :",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    recette.instructions.forEach { etape->
                        Text("* $etape", style = MaterialTheme.typography.bodyMedium)
                    }
            }
        }
    }
}

@Composable
fun ListesScreen(modifier: Modifier = Modifier){
    val context = LocalContext.current
    var listes by remember { mutableStateOf(emptyList<Ingredient>()) }
    var ingredientAjouter by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        listes = context.lireListes()
    }

    fun ajouterIngredient(){
        if (ingredientAjouter.isNotBlank()){
            val nouveau = Ingredient(nom = ingredientAjouter, acheter = false)
            val nouvelleListe = listes + nouveau
            listes = nouvelleListe
            context.sauvegarderListes(nouvelleListe)
            ingredientAjouter= ""
        }
    }

    fun toggleAcheter(ingredient: Ingredient){
        val nouvelleListe = listes.map { article ->
            if (article == ingredient){
                article.copy(acheter = !article.acheter)
            } else  {
                article
            }
        }
        listes = nouvelleListe
        context.sauvegarderListes(nouvelleListe)
    }

    fun viderListe(){
        val nouvelleListe = listes.filter { !it.acheter }
        listes = nouvelleListe
        context.sauvegarderListes(nouvelleListe)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Votre Liste de Courses 🛒 a acheter",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = ingredientAjouter,
                onValueChange = {ingredientAjouter = it},
                label = {Text("Ajouter un articles a la liste...")},
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {ajouterIngredient()}) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (listes.isEmpty()){
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),contentAlignment = Alignment.Center){
                Text("Votre liste est vide", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listes){article->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { toggleAcheter(article) },
                        colors = CardDefaults.cardColors(
                            containerColor = if (article.acheter) Color.LightGray.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = article.acheter,
                                onCheckedChange = {toggleAcheter(article)}
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = article.nom,
                                style = MaterialTheme.typography.bodyLarge,
                                textDecoration = if (article.acheter) androidx.compose.ui.text.style.TextDecoration.LineThrough else null
                            )
                        }
                    }
                }
            }
        }
        if (listes.any{it.acheter}){
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {viderListe()},
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cliquer pour supprimer les articles payer")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    isDarkTheme: Boolean,
    onThemeChange: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    var menuExpanded by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("CamerFoods")},
                actions = {
                    IconButton(onClick = {menuExpanded = true}) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Options")
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = {menuExpanded=false}
                    ) {
                        DropdownMenuItem(
                            text =  {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Spacer(Modifier.width(8.dp))
                                    Text("Favoris")
                                }
                            }, onClick = {
                                menuExpanded =false
                                navController.navigate("favoris")
                                {
                                    popUpTo ( navController.graph.startDestinationId){
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
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
            composable(Screen.Settings.route){
                SettingsScreen(
                    isDarkTheme = isDarkTheme,
                    onThemeChange = onThemeChange
                )
            }
            composable("liste_recettes"){
                RecetteScreen(context = context, showFavoritesOnly = false)
            }
            composable("favoris") {
                RecetteScreen(context = context, showFavoritesOnly = true)
            }
            composable ( Screen.Listes.route ){
                ListesScreen()
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