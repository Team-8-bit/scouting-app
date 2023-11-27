package org.team9432.scoutingapp.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.team9432.scoutingapp.io.spreadsheetFromURI

@Composable
fun InitialScreen() {
    var pickerOpen by remember { mutableStateOf(false) }

    Surface {
        Box(Modifier.fillMaxSize()) {
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = {
                    pickerOpen = true
                }
            ) {
                Text(text = "Press")
            }
        }
    }

    FilePicker(visible = pickerOpen, onFileSelected = { println(spreadsheetFromURI(it)) }, onClose = { pickerOpen = false })
}

@Composable
private fun FilePicker(
    visible: Boolean,
    onFileSelected: (Uri) -> Unit,
    onClose: () -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { result ->

        if (result != null) {
            onFileSelected(result)
        }
        onClose()
    }

    LaunchedEffect(visible) {
        if (visible) {
            launcher.launch(arrayOf("text/comma-separated-values"))
        }
    }
}