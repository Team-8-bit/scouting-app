package org.team9432.scoutingapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BlankInput(modifier: Modifier = Modifier) {
    Box(modifier.background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.small)) {}
}

@Composable
fun SubmitButton(modifier: Modifier = Modifier, onPressed: () -> Unit, enabled: Boolean = true) {
    Box(modifier.background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.small).clickable(enabled = enabled, onClick = onPressed)) {
        Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Submit Data",
                textAlign = TextAlign.Center
            )
            Icon(Icons.Filled.Save, contentDescription = "Save", modifier = Modifier.fillMaxSize(0.3F), tint = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primaryContainer))
        }
    }
}

@Composable
fun PageChanger(modifier: Modifier = Modifier, onNext: () -> Unit, onBack: () -> Unit, nextEnabled: Boolean = true, backEnabled: Boolean = true) {
    val enabledBackground = MaterialTheme.colorScheme.tertiaryContainer
    val disabledBackground = enabledBackground.copy(0.7F)
    val icon = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.tertiaryContainer)

    Box(modifier.background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.small)) {
        Row(Modifier.align(Alignment.Center), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5F)
                    .padding(top = 5.dp, end = 2.5.dp, bottom = 5.dp, start = 5.dp)
                    .background(if (backEnabled) enabledBackground else disabledBackground, MaterialTheme.shapes.small)
                    .clickable(onClick = onBack, enabled = backEnabled),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.NavigateBefore, contentDescription = "Back", tint = icon)
            }
            Box(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(top = 5.dp, end = 2.5.dp, bottom = 5.dp, start = 5.dp)
                    .background(if (nextEnabled) enabledBackground else disabledBackground, MaterialTheme.shapes.small)
                    .clickable(onClick = onNext, enabled = nextEnabled),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.NavigateNext, contentDescription = "Next", tint = icon)
            }
        }
    }
}

@Composable
fun GenericButtonInput(modifier: Modifier = Modifier, title: String, onPressed: () -> Unit, enabled: Boolean = true) {
    Box(modifier.background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.small)) {
        OutlinedButton(
            modifier = Modifier.padding(10.dp).align(Alignment.Center),
            onClick = onPressed,
            enabled = enabled
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InlineTextInput(modifier: Modifier = Modifier, title: String, initialValue: String = "", onChange: (String) -> Unit, enabled: Boolean = true, predicate: (String) -> Boolean = { true }) {
    var currentText by remember { mutableStateOf(initialValue) }
    var isError by remember { mutableStateOf(false) }

    Box(modifier.background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.small)) {
        Row(Modifier.align(Alignment.Center), verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = title,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = currentText,
                enabled = enabled,
                singleLine = true,
                isError = isError,
                onValueChange = {
                    currentText = it
                    if (predicate(it)) {
                        isError = false
                        onChange(it)
                    } else {
                        isError = true
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownInput(modifier: Modifier = Modifier, title: String, initialValue: String = "", onChange: (String) -> Unit, enabled: Boolean = true, options: List<String>) {
    var currentText by remember { mutableStateOf(initialValue) }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier.background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.small)) {
        Row(Modifier.align(Alignment.Center), verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = title,
                textAlign = TextAlign.Center
            )
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    readOnly = true,
                    value = currentText,
                    enabled = enabled,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    singleLine = true,
                    onValueChange = {
                        currentText = it
                        onChange(it)
                    }
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    options.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                currentText = it
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(modifier: Modifier = Modifier, title: String, initialValue: String = "", onChange: (String) -> Unit, enabled: Boolean = true, predicate: (String) -> Boolean = { true }) {
    var currentText by remember { mutableStateOf(initialValue) }
    var isError by remember { mutableStateOf(false) }

    Box(modifier.background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.small)) {
        Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = title,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxSize(),
                value = currentText,
                enabled = enabled,
                onValueChange = {
                    currentText = it
                    if (predicate(it)) {
                        isError = false
                        onChange(it)
                    } else {
                        isError = true
                    }
                }
            )
        }
    }
}

@Composable
fun NumberInput(modifier: Modifier = Modifier, title: String, onChange: (Int) -> Unit, max: Int = Int.MAX_VALUE, min: Int = 0, initialValue: Int = 0, enabled: Boolean = true) {
    assert(initialValue in min..max)
    var currentNumber by remember { mutableIntStateOf(initialValue) }

    val enabledBackground = MaterialTheme.colorScheme.primaryContainer
    val disabledBackground = enabledBackground.copy(0.7F)

    Box(modifier.background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.small)) {
        Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = title,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                text = currentNumber.toString(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Row(Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.5F)
                        .padding(top = 5.dp, end = 2.5.dp, bottom = 5.dp, start = 5.dp)
                        .background(if (enabled) enabledBackground else disabledBackground, MaterialTheme.shapes.small)
                        .clickable(onClick = {
                            if (currentNumber != min) {
                                currentNumber -= 1
                                onChange(currentNumber)
                            }
                        }, enabled = enabled),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Remove, contentDescription = "Remove")
                }
                Box(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(top = 5.dp, end = 2.5.dp, bottom = 5.dp, start = 5.dp)
                        .background(if (enabled) enabledBackground else disabledBackground, MaterialTheme.shapes.small)
                        .clickable(onClick = {
                            if (currentNumber != max) {
                                currentNumber += 1
                                onChange(currentNumber)
                            }
                        }, enabled = enabled),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            }
//            Row(Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)) {
//                OutlinedIconButton(
//                    modifier = Modifier.fillMaxWidth(0.5F).fillMaxHeight().padding(start = 7.dp, end = 7.dp),
//                    enabled = enabled,
//                    onClick = {
//                        if (currentNumber != min) {
//                            currentNumber -= 1
//                            onChange(currentNumber)
//                        }
//                    }) {
//                    Icon(Icons.Filled.Remove, contentDescription = "Remove")
//                }
//                OutlinedIconButton(
//                    modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(start = 7.dp, end = 7.dp),
//                    enabled = enabled,
//                    onClick = {
//                        if (currentNumber != max) {
//                            currentNumber += 1
//                            onChange(currentNumber)
//                        }
//                    }) {
//                    Icon(Icons.Filled.Add, contentDescription = "Add")
//                }
//            }
        }
    }
}

@Composable
fun CycleInput(modifier: Modifier = Modifier, title: String, onChange: (String) -> Unit, options: List<String>, initialOption: String = options[0], enabled: Boolean = true) {
    var currentOption by remember { mutableStateOf(initialOption) }
    Box(modifier
        .background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.small)
        .clickable(enabled = enabled) {
            val nextIndex = options.indexOf(currentOption) + 1
            currentOption = if (nextIndex == options.size) {
                options[0]
            } else {
                options[nextIndex]
            }
            onChange(currentOption)
        }) {
        Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = title,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                text = currentOption,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SwitchInput(modifier: Modifier = Modifier, title: String, onChange: (Boolean) -> Unit, initialState: Boolean = false, enabled: Boolean = true) {
    var checked by remember { mutableStateOf(initialState) }
    Box(modifier.background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.small)) {
        Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = title,
                textAlign = TextAlign.Center
            )
            Switch(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                checked = checked,
                onCheckedChange = { checked = it; onChange(it) },
                enabled = enabled,
                thumbContent = {
                    if (checked) {
                        Icon(Icons.Filled.Check, null)
                    } else {
                        Icon(Icons.Filled.Close, null)
                    }
                }
            )
        }
    }
}