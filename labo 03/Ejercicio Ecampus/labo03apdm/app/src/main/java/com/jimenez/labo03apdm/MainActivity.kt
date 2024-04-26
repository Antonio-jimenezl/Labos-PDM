package com.jimenez.labo03apdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Libro(val titulo: String, val autor: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiAplicacion()
        }
    }
}

@Composable
fun MiAplicacion() {
    var cargando by remember { mutableStateOf(false) }
    var libros by remember { mutableStateOf(mutableListOf<Libro>()) }
    val corrutinaScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if (!cargando) {
                    cargando = true
                    val nuevoTitulo = "Luna de pluton"
                    val nuevoAutor = "Dross"

                    corrutinaScope.launch {
                        delay(3000)
                        libros.add(Libro(nuevoTitulo, nuevoAutor))
                        cargando = false
                    }
                }
            }
        ) {
            Text("Agregar Libro")
        }

        if (cargando) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 5.dp))
        }

        if (libros.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                reverseLayout = true
            ) {
                items(libros) { libro ->
                    TarjetaLibro(libro)
                }
            }
        } else if (!cargando) {
            Text("Sin datos que mostrar")
        }
    }
}


@Composable
fun TarjetaLibro(libro: Libro) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),

        ) {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = "Título del Libro: ${libro.titulo}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Nombre del Autor: ${libro.autor}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VistaPreviaPredeterminada() { MiAplicacion()
}
