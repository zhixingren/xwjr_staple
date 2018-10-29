package com.xwjr.staple.manager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider

import java.io.File

object MyFileProvider {

    /**
     * .getUriForFile(this, file);
     */
   private fun getUriForFile(context: Context, file: File): Uri? {
        return if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(context,
                    context.packageName + ".fileprovider",
                    file)
        } else {
            Uri.fromFile(file)
        }
    }

    /**
     * .setIntentDataAndType(this,
     * intent, "application/vnd.android.package-archive", file, true);
     */
    fun setIntentDataAndType(context: Context,
                             intent: Intent,
                             type: String,
                             file: File,
                             writeAble: Boolean) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(getUriForFile(context, file), type)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type)
        }
    }
}