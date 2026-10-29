package com.example.ui.components

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object OfflineVideoCache {

    /**
     * Extracts the raw MP4 video resource into internal cache storage once.
     * Playing from a local file path is the most reliable, high-performance method
     * on Android, bypassing ContentResolver URI parsing quirks and codec issues.
     */
    fun getVideoPath(context: Context, rawResId: Int): String {
        return try {
            val cacheFolder = File(context.cacheDir, "offline_exercise_videos").apply { mkdirs() }
            val targetFile = File(cacheFolder, "exercise_vid_v2_${rawResId}.mp4")
            
            // Check if file exists and has non-zero size
            if (!targetFile.exists() || targetFile.length() < 1024L) {
                // Delete previous versions if any
                cacheFolder.listFiles()?.forEach { file ->
                    if (file.name.startsWith("exercise_vid_") && !file.name.contains("v2")) {
                        try { file.delete() } catch (_: Exception) {}
                    }
                }
                context.resources.openRawResource(rawResId).use { input ->
                    FileOutputStream(targetFile).use { output ->
                        input.copyTo(output)
                    }
                }
            }
            targetFile.absolutePath
        } catch (_: Exception) {
            // Safe fallback
            ""
        }
    }

    fun getVideoUri(context: Context, rawResId: Int): Uri {
        val path = getVideoPath(context, rawResId)
        return if (path.isNotEmpty() && File(path).exists()) {
            Uri.fromFile(File(path))
        } else {
            Uri.parse("android.resource://${context.packageName}/$rawResId")
        }
    }
}
