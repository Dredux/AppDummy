package edu.rodriguezdavid.appdummy
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
// ─── PantallaBienvenida.kt ───────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBienvenida(onEntrar: () -> Unit) {
    // R3 - El nombre introducido se conserva al rotar el dispositivo.
    var nombreUsuario by rememberSaveable { mutableStateOf("") }
    val botonHabilitado = nombreUsuario.trim().length >= 3

    // R4 - La pantalla se estructura con Scaffold y TopAppBar.
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AppDummy") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // R1 - Icono decorativo, título, subtítulo y un OutlinedTextField para el nombre del usuario.
            Icon(
                imageVector = Icons.Default.Book,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "AppDummy",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Tu catálogo de libros",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Campo de nombre
            OutlinedTextField(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it },
                label = { Text("¿Cómo te llamas?") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                // R2 - El campo muestra un supportingText y el botón "Entrar" sólo se habilita si el nombre tiene 3 o más caracteres.
                supportingText = {
                    Text("Mínimo 3 caracteres (${nombreUsuario.length}/3)")
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de acceso
            Button(
                onClick = onEntrar,
                enabled = botonHabilitado,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = if (botonHabilitado) "Entrar como ${nombreUsuario.trim()}" else "Entrar",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaBienvenidaPreview() {
    MaterialTheme {
        PantallaBienvenida(onEntrar = { })
    }
}