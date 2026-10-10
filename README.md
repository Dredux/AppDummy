AppDummy v1 — G-APPDUMMY-V1
David Rodriguez Diaz · 10115581
 Qué hace
AppDummy es una aplicación Android de ejemplo para gestionar un catálogo de libros con una pantalla de bienvenida inicial. Permite buscar, filtrar y marcar elementos como favoritos o leídos, además de compartir información básica de cada libro. También incluye una vista para comprobar y solicitar permisos del sistema, especialmente de cámara. La app está pensada como prototipo de biblioteca personal con interfaz Material 3.
 Estructura
•
app/src/main/java/edu/rodriguezdavid/appdummy/MainActivity.kt — punto de entrada de la aplicación y actividad principal.
•
app/src/main/java/edu/rodriguezdavid/appdummy/PantallaBienvenida.kt — pantalla inicial con validación del nombre del usuario y acceso al catálogo.
•
app/src/main/java/edu/rodriguezdavid/appdummy/PantallaListado.kt — listado de libros, búsqueda, filtros por autor, acciones de favorito/leído y compartir.
•
app/src/main/java/edu/rodriguezdavid/appdummy/PantallaGenerador.kt — pantalla relacionada con permisos/cámara; requiere revisión y limpieza.
•
app/src/main/AndroidManifest.xml — declaración de permisos, configuración de la activity principal y requisitos de hardware.
•
app/build.gradle.kts — configuración de Gradle, Compose y dependencias de la app.
 Dependencias añadidas
 Librería 
 Para qué 
 AndroidX Compose BOM 
 Centraliza versiones y compatibilidad de componentes Jetpack Compose. 
 androidx.activity:activity-compose 
 Permite integrar la Activity con Compose y manejar la UI declarativa. 
 androidx.compose.material3 
 Proporciona los componentes Material 3 de la interfaz (cards, chips, botones, top bar, etc.). 
 androidx.compose.material:material-icons-extended 
 Añade iconos adicionales para búsqueda, cámara, perfil, permisos y acciones de la app. 
 io.coil-kt.coil3:coil-compose 
 Carga y muestra imágenes de portada desde URLs en la lista de libros. 
 io.coil-kt.coil3:coil-network-okhttp 
 Integra Coil con OkHttp para manejar la descarga de imágenes. 
 androidx.core:core-ktx 
 Extensiones Kotlin para Android y acceso a utilidades del SDK. 
 androidx.lifecycle:lifecycle-runtime-ktx 
 Gestión del ciclo de vida y compatibilidad con coroutines en Android. 
 Permisos declarados
 Permiso 
 Por qué es necesario 
 android.permission.INTERNET 
 Permite cargar imágenes de portadas o recursos remotos desde servicios externos. 
 android.permission.CAMERA 
 Necesario para acceder a la cámara y capturar imágenes relacionadas con libros o portadas. 
 android.permission.READ_MEDIA_IMAGES 
 Permite leer imágenes del almacenamiento del dispositivo en Android 13 y superiores. 
 android.permission.READ_EXTERNAL_STORAGE 
 Compatibilidad con Android 12 y versiones anteriores para acceder a imágenes del almacenamiento local. 
 Limitaciones conocidas
•
La navegación completa entre pantallas no está conectada todavía; la app está parcialmente montada y MainActivity no gestiona el flujo de uso real.
•
PantallaGenerador.kt parece duplicar lógica de permisos y necesita refactorización para evitar código redundante.
•
Las portadas dependen de internet; si la red falla o la URL no está disponible, la carga puede quedar incompleta.
•
No hay persistencia real de datos ni backend: la información de libros es local y de ejemplo, por lo que no es una aplicación lista para producción.
