package com.lcyer.permission

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lcyer.permission.ui.theme.PermissionTheme
import com.lcyer.simplepermission.SimplePermission
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            PermissionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    var isPermissionResult by remember { mutableStateOf(false) }

    if (isPermissionResult) {
        AlertDialog(
            text = { Text("Allow Camera Permission!") },
            onDismissRequest = { },
            confirmButton = {
                Button(
                    onClick = {
                        isPermissionResult = false
                    }
                ) {
                    Text(text = "ok")
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        val context = LocalContext.current

        Button(
            onClick = { context.startActivity(Intent(context, PermissionTestForJava::class.java)) }
        ) {
            Text(text = "자바 페이지 이동")
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    val isGranted = SimplePermission.builder()
                        .denyTitle("Permission Denied")
                        .denyMessage("please go to Permissions in the app settings and allow it.")
                        .permissions(Manifest.permission.CAMERA)
                        .create()
                        .isGranted()

                    isPermissionResult = isGranted
                }
            }
        ) {
            Text(text = "Open Camera")
        }
    }
}
