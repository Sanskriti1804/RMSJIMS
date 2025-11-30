package com.example.rmsjims.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ImagePicker {
    
    private const val AUTHORITY_SUFFIX = ".fileprovider"
    
    fun createCameraIntent(context: Context): Pair<Intent, Uri?> {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile = createImageFile(context)
        
        val photoUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val authority = "${context.packageName}$AUTHORITY_SUFFIX"
            FileProvider.getUriForFile(context, authority, photoFile)
        } else {
            Uri.fromFile(photoFile)
        }
        
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        
        return Pair(intent, photoUri)
    }
    
    fun createGalleryIntent(): Intent {
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        } else {
            Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
        }
        intent.type = "image/*"
        return intent
    }
    
    private fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = context.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }
    
    fun getImageUriFromIntent(intent: Intent?): Uri? {
        return intent?.data
    }
}

