package com.kamel.cleanexceptionlibrary

import android.content.Context

class Utilities {

    companion object {
        fun getDataDir(context: Context, packageName: String): String {
            return context.packageManager.getPackageInfo(packageName, 0).applicationInfo.dataDir
        }


        fun isNotFactoryPackage(stackTraceElement: String, packageName: String): Boolean {
            return stackTraceElement.startsWith(packageName,true)
        }

    }




}