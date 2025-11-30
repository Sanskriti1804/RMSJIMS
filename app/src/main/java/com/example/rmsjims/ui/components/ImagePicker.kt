package com.example.rmsjims.ui.components

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.rmsjims.util.ImagePicker
import com.example.rmsjims.util.PermissionManager

data class ImagePickerState(
    val imageUri: Uri? = null,
    val hasPermission: Boolean = false,
    val showPermissionRationale: Boolean = false
)

@Composable
fun rememberImagePicker(
    onImageSelected: (Uri?) -> Unit
): ImagePickerHandler {
    val context = LocalContext.current
    
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var pendingAction by remember { mutableStateOf<(() -> Unit)?>(null) }
    
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            pendingAction?.invoke()
            pendingAction = null
        }
    }
    
    val galleryPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            pendingAction?.invoke()
            pendingAction = null
        }
    }
    
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = imageUri.takeIf { result.resultCode == android.app.Activity.RESULT_OK }
        onImageSelected(uri)
        imageUri = null
    }
    
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = ImagePicker.getImageUriFromIntent(result.data)
        onImageSelected(uri)
    }
    
    return ImagePickerHandler(
        onPickFromCamera = {
            if (PermissionManager.hasCameraPermission(context)) {
                val (intent, uri) = ImagePicker.createCameraIntent(context)
                imageUri = uri
                cameraLauncher.launch(intent)
            } else {
                pendingAction = {
                    val (intent, uri) = ImagePicker.createCameraIntent(context)
                    imageUri = uri
                    cameraLauncher.launch(intent)
                }
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        },
        onPickFromGallery = {
            if (PermissionManager.hasGalleryPermission(context)) {
                galleryLauncher.launch(ImagePicker.createGalleryIntent())
            } else {
                pendingAction = {
                    galleryLauncher.launch(ImagePicker.createGalleryIntent())
                }
                galleryPermissionLauncher.launch(PermissionManager.getGalleryPermission())
            }
        }
    )
}

class ImagePickerHandler(
    val onPickFromCamera: () -> Unit,
    val onPickFromGallery: () -> Unit
)

