package edu.rodriguezdavid.appdummy
import android.content.Intent
import android.util.Patterns
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

// ─── screens/PantallaListado.kt ───────────────────────────────────────────────────────────────
// R5 - Modelo de datos simple (será reemplazado por data class real en B2)
data class LibroUI(
    val id: Int = 0,
    val titulo: String = "",
    val autor: String = "",
    val year: Int? = 1900,
    val isbn: String = "",
    val cover: String = "",
    val esFavorito: Boolean = false,
    val leido: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListado() {
    val context = LocalContext.current

    // Estado local de la pantalla (en B2 pasará al ViewModel)
    var busqueda by rememberSaveable { mutableStateOf("") }
    var autorSeleccionado by rememberSaveable { mutableStateOf("Todos") }
    // R5 - Una lista de ejemplos con al menos 5 libros.
    var libros by rememberSaveable {
        mutableStateOf(
            listOf(
                LibroUI(
                    id = 1,
                    titulo = "Proyecto Hail Mary",
                    autor = "Andy Weir",
                    year = 2021,
                    isbn = "9788418037016",
                    cover = "https://covers.openlibrary.org/b/isbn/9788418037016-L.jpg",
                    esFavorito = true,
                    leido = false
                ),
                LibroUI(
                    id = 2,
                    titulo = "Juego de tronos",
                    autor = "George R.R. Martin",
                    year = 1996,
                    isbn = "9780307951182",
                    cover = "https://covers.openlibrary.org/b/isbn/9780307951182-L.jpg",
                    esFavorito = true,
                    leido = true
                ),
                LibroUI(
                    id = 3,
                    titulo = "Festín de cuervos",
                    autor = "George R.R. Martin",
                    year = 2005,
                    isbn = "9780307951212",
                    esFavorito = false,
                    leido = false
                ),
                LibroUI(
                    id = 4,
                    titulo = "Cementerio de Animales",
                    autor = "Stephen King",
                    year = 1983,
                    isbn = "9788401499845",
                    esFavorito = false,
                    leido = true
                ),
                LibroUI(
                    id = 5,
                    titulo = "El juego de Ender",
                    autor = "Orson Scott Card",
                    year = 1985,
                    isbn = "9788498720068",
                    esFavorito = false,
                    leido = true
                )
            )
        )
    }

    val autores = listOf("Todos") + libros.map { it.autor }.distinct().sorted()

    // Filtrado reactivo
    val librosFiltrados = libros.filter { libro ->
        val coincideBusqueda = busqueda.isBlank() ||
                libro.titulo.contains(busqueda, ignoreCase = true)
        val coincideGenero = autorSeleccionado == "Todos" ||
                libro.autor == autorSeleccionado
        coincideBusqueda && coincideGenero
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // R11 - Los colores y textos usan MaterialTheme.colorScheme y MaterialTheme.typography
                    Text(
                        text = "AppDummy",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                // Nota: Esto controla el icono de perfil. Aparentemente, suele ser el uso esperado.
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Perfil")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Barra de búsqueda
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = {
                    Text(
                        text = "Buscar libros...",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    // R8 - Campo de búsqueda por título con un botón de borrado que aparece y desaparece con AnimatedVisibility.
                    AnimatedVisibility(visible = busqueda.isNotEmpty()) {
                        IconButton(onClick = { busqueda = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Borrar búsqueda")
                        }
                    }
                },
                singleLine = true
            )

            // Chips de autores
            // Nota: Parece que hay dos tipos de Columns y dos tipos de Rows, pero los Lazy sí que permiten el scroll por defecto.
            // Nota: Parece que los Lazy no comparten funciones especializadas con los Column y Rows normales.
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                items(autores) { autor ->
                    // R9 - Filtro por autor mediante FilterChip.
                    // La búsqueda y el filtro se combinan y la rejilla se actualiza de forma reactiva.
                    FilterChip(
                        selected = autor == autorSeleccionado,
                        onClick = { autorSeleccionado = autor },
                        label = {
                            Text(
                                text = autor,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    )
                }
            }

            // R10 - Si no hay resultados, se muestra un estado vacío con icono y mensaje en lugar de la rejilla.
            if (librosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Nota: Por alguna razón, el tamaño del texto en la busqueda afecta al posicionamiento del mismo y del icono, desplazándolo a la izquierda.
                    // Nota: El parametro del Column impide que el icono se desplace, pero no impide que el texto lo haga.
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.SearchOff,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Sin resultados para \"$busqueda\"",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                // R6 - LazyVerticalGrid de dos columnas; cada celda es un ItemLibro que muestra portada (con AsyncImage de Coil), título, autor y año.
                LazyVerticalGrid(
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(librosFiltrados, key = { it.id }) { libro ->
                        // R7 - En cada ItemLibro, dos IconButton (favorito y leído) cuyo icono cambia según el estado del libro.
                        ItemLibro(
                            libro = libro,
                            onToggleLeido = { id ->
                                libros = libros.map {
                                    if (it.id == id) it.copy(leido = !it.leido) else it
                                }
                            },
                            onToggleFavorito = { id ->
                                libros = libros.map {
                                    if (it.id == id) it.copy(esFavorito = !it.esFavorito) else it
                                }
                            },
                            // R16 - Acción Compartir en cada libro:
                            // intent implícito ACTION_SEND con el título y el autor en EXTRA_TEXT.
                            onShare = {
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "${libro.titulo} - ${libro.autor}"
                                    )
                                }
                                // R16 - lanzado con Intent.createChooser()
                                context.startActivity(Intent.createChooser(shareIntent, "Compartir libro"))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemLibro(
    libro: LibroUI,
    onToggleLeido: (Int) -> Unit,
    onToggleFavorito: (Int) -> Unit,
    onShare: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = libro.titulo,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (Patterns.WEB_URL.matcher(libro.cover).matches())
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(libro.cover)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Portada de ${libro.titulo}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.width(200.dp)
                )
            else // Si no es una URL válida, se muestra una imagen por defecto
                AsyncImage(
                    model = R.drawable.nocover,
                    contentDescription = "Portada de ${libro.titulo}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.width(200.dp)
                )

            Text(
                text = "${libro.autor} • ${libro.year}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.5f),
                    onClick = { onToggleLeido(libro.id) }) {
                    Icon(
                        imageVector = if (libro.leido) Icons.Default.BookmarkAdded
                        else Icons.Default.BookmarkBorder,
                        contentDescription = if (libro.leido) "Quitar leído" else "Marcar como leído",
                        tint = if (libro.leido) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.5f),
                    onClick = { onToggleFavorito(libro.id) }) {
                    Icon(
                        imageVector = if (libro.esFavorito) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = if (libro.esFavorito) "Quitar favorito" else "Añadir favorito",
                        tint = if (libro.esFavorito) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.5f),
                    onClick = onShare
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Compartir libro",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaListadoPreview() {
    MaterialTheme {
        PantallaListado()
    }
}