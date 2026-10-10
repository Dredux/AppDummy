# AppDummy v1 — G-APPDUMMY-V1

**Autor:** David Rodriguez Diaz  
**Matrícula:** 10115581

## ¿Qué hace?

AppDummy es una aplicación Android de ejemplo para gestionar un catálogo de libros con una pantalla de bienvenida inicial.

Permite buscar, filtrar y marcar elementos como favoritos o leídos, además de compartir información básica de cada libro. También incluye una vista para comprobar y solicitar permisos del sistema, especialmente el permiso de cámara.

La aplicación está pensada como un prototipo de biblioteca personal con una interfaz basada en **Material 3**.

## Estructura

- `app/src/main/java/edu/rodriguezdavid/appdummy/MainActivity.kt` — Punto de entrada de la aplicación y actividad principal.
- `app/src/main/java/edu/rodriguezdavid/appdummy/PantallaBienvenida.kt` — Pantalla inicial con validación del nombre del usuario y acceso al catálogo.
- `app/src/main/java/edu/rodriguezdavid/appdummy/PantallaListado.kt` — Listado de libros, búsqueda, filtros por autor, acciones para marcar favoritos o libros leídos y opción para compartir.
- `app/src/main/java/edu/rodriguezdavid/appdummy/PantallaGenerador.kt` — Pantalla relacionada con los permisos y la cámara; requiere revisión y limpieza.
- `app/src/main/AndroidManifest.xml` — Declaración de permisos, configuración de la actividad principal y requisitos de hardware.
- `app/build.gradle.kts` — Configuración de Gradle, Jetpack Compose y dependencias de la aplicación.

## Dependencias añadidas

| Librería | Para qué sirve |
|---|---|
| AndroidX Compose BOM | Centraliza las versiones y la compatibilidad de los componentes de Jetpack Compose. |
| `androidx.activity:activity-compose` | Permite integrar la actividad con Compose y gestionar la interfaz declarativa. |
| `androidx.compose.material3` | Proporciona los componentes Material 3 de la interfaz, como tarjetas, chips, botones y barras superiores. |
| `androidx.compose.material:material-icons-extended` | Añade iconos adicionales para la búsqueda, la cámara, el perfil, los permisos y las acciones de la aplicación. |
| `io.coil-kt.coil3:coil-compose` | Carga y muestra imágenes de portadas desde URL en la lista de libros. |
| `io.coil-kt.coil3:coil-network-okhttp` | Integra Coil con OkHttp para gestionar la descarga de imágenes. |
| `androidx.core:core-ktx` | Proporciona extensiones de Kotlin para Android y acceso a utilidades del SDK. |
| `androidx.lifecycle:lifecycle-runtime-ktx` | Facilita la gestión del ciclo de vida y la compatibilidad con coroutines en Android. |

## Permisos declarados

| Permiso | Por qué es necesario |
|---|---|
| `android.permission.INTERNET` | Permite cargar imágenes de portadas y otros recursos remotos desde servicios externos. |
| `android.permission.CAMERA` | Permite acceder a la cámara y capturar imágenes relacionadas con libros o portadas. |
| `android.permission.READ_MEDIA_IMAGES` | Permite leer imágenes del almacenamiento del dispositivo en Android 13 y versiones posteriores. |
| `android.permission.READ_EXTERNAL_STORAGE` | Proporciona compatibilidad con Android 12 y versiones anteriores para acceder a imágenes del almacenamiento local. |

## Limitaciones conocidas

- **Navegación incompleta:** La navegación entre pantallas todavía no está completamente conectada. La aplicación está parcialmente implementada y `MainActivity` no gestiona el flujo de uso real.
- **Código redundante:** `PantallaGenerador.kt` parece duplicar la lógica de gestión de permisos y necesita una refactorización para evitar código redundante.
- **Dependencia de internet:** Las portadas dependen de una conexión a internet. Si la red falla o la URL no está disponible, la carga de imágenes puede quedar incompleta.
- **Sin persistencia ni backend:** No existe persistencia real de datos ni conexión con un backend. La información de los libros es local y de ejemplo, por lo que la aplicación todavía no está preparada para producción.
