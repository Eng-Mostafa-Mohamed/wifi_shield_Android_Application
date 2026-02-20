package com.example.practice.presentation.screens.Test

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.practice.R
import com.example.practice.SetStatusBarColor
import kotlinx.coroutines.delay

val DeepBlue = Color(0xFF0F172A)
val ElectricBlue = Color(0xFF3B82F6)
val SoftPurple = Color(0xFF8B5CF6)
val CardBg = Color(0xFFF8FAFC)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullWifiSettingsScreen(navController: NavController) {

    var ssid24 by remember { mutableStateOf("Home_Wifi") }
    var pass24 by remember { mutableStateOf("Mostafa11100@") }
    var hidden24 by remember { mutableStateOf(false) }

    var ssid5G by remember { mutableStateOf("Home_Wifi_5G") }
    var pass5G by remember { mutableStateOf("Mostafa111000#") }
    var hidden5G by remember { mutableStateOf(false) }

    var firewallEnabled by remember { mutableStateOf(true) }

    var isSyncing by remember { mutableStateOf(false) }

    SetStatusBarColor(color = Color.White, darkIcons = true)


    LaunchedEffect(isSyncing) {
        if (isSyncing) {
            delay(2000)
            isSyncing = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("NETWORK CORE", fontWeight = FontWeight.Black, letterSpacing = 2.sp, fontSize = 18.sp) },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate("Main") }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color.White),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item { FirewallCard(enabled = firewallEnabled, onToggle = { firewallEnabled = it }) }

                item {
                    NetworkCard(
                        frequency = "2.4",
                        gradient = Brush.linearGradient(listOf(ElectricBlue, SoftPurple)),
                        ssid = ssid24,
                        onSsidChange = { ssid24 = it },
                        pass = pass24,
                        onPassChange = { pass24 = it },
                        isHidden = hidden24,
                        onHiddenToggle = { hidden24 = it },
                        tintColor = ElectricBlue
                    )
                }

                item {
                    NetworkCard(
                        frequency = "5.0",
                        gradient = Brush.linearGradient(listOf(SoftPurple, Color(0xFFEC4899))),
                        ssid = ssid5G,
                        onSsidChange = { ssid5G = it },
                        pass = pass5G,
                        onPassChange = { pass5G = it },
                        isHidden = hidden5G,
                        onHiddenToggle = { hidden5G = it },
                        tintColor = SoftPurple
                    )
                }

                item {
                    Button(
                        onClick = { isSyncing = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(65.dp)
                            .padding(top = 8.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DeepBlue),
                        enabled = !isSyncing
                    ) {
                        if (isSyncing) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                        } else {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                            Spacer(Modifier.width(12.dp))
                            Text("SYNC CONFIGURATION", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            }
        }


        if (isSyncing) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = ElectricBlue)
                        Spacer(Modifier.height(16.dp))
                        Text("Applying Settings...", fontWeight = FontWeight.Bold, color = DeepBlue)
                    }
                }
            }
        }
    }
}


@Composable
fun FirewallCard(enabled: Boolean, onToggle: (Boolean) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = if (enabled) Color(0xFFECFDF5) else Color(0xFFFEF2F2))
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(48.dp).background(if (enabled) Color(0xFF10B981) else Color(0xFFEF4444), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(if (enabled) Icons.Default.Lock else Icons.Default.Warning, contentDescription = null, tint = Color.White)
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("System Firewall", fontWeight = FontWeight.Bold, color = DeepBlue)
                Text(if (enabled) "Shield Active" else "Shield Disabled", fontSize = 12.sp, color = Color.Gray)
            }
            Switch(checked = enabled, onCheckedChange = onToggle)
        }
    }
}



@Composable
fun NetworkCard(
    frequency: String,
    gradient: Brush,
    ssid: String,
    onSsidChange: (String) -> Unit,
    pass: String,
    onPassChange: (String) -> Unit,
    isHidden: Boolean,
    onHiddenToggle: (Boolean) -> Unit,
    tintColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBg, RoundedCornerShape(32.dp))
            .border(1.dp, Color.LightGray.copy(0.3f), RoundedCornerShape(32.dp))
            .padding(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.background(gradient, RoundedCornerShape(12.dp)).padding(horizontal = 12.dp, vertical = 6.dp)) {
                Text("${frequency}GHz", color = Color.White, fontWeight = FontWeight.Black, fontSize = 12.sp)
            }
            Spacer(Modifier.width(12.dp))
            Text("Band Configuration", fontWeight = FontWeight.Bold, color = DeepBlue)
        }

        Spacer(Modifier.height(24.dp))

        SettingsTextField(label = "Network Name (SSID)", value = ssid, onValueChange = onSsidChange, icon = R.drawable.wifi_off, tintColor = tintColor)

        Spacer(Modifier.height(16.dp))

        SettingsTextField(label = "Security Password", value = pass, onValueChange = onPassChange, icon = R.drawable.lock_ic, tintColor = tintColor)

        Spacer(Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Security Type", color = Color.Gray, fontSize = 14.sp)
            Text("WPA2-PSK (Personal)", fontWeight = FontWeight.Bold, color = DeepBlue, fontSize = 14.sp)
        }

        HorizontalDivider(Modifier.padding(vertical = 16.dp), color = Color.LightGray.copy(0.4f))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Checkbox(checked = isHidden, onCheckedChange = onHiddenToggle)
            Text("Hide network from public scan", fontSize = 14.sp, color = DeepBlue)
        }
    }
}






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTextField(label: String, value: String, onValueChange: (String) -> Unit, icon: Int, tintColor: Color) {
    Column {
        Text(label, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(tintColor)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tintColor,
                unfocusedBorderColor = Color.LightGray.copy(0.5f),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            singleLine = true
        )
    }
}