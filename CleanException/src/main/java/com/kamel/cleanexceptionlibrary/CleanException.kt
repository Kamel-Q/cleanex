package com.kamel.cleanexceptionlibrary

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import android.util.Log
import android.widget.Toast
import com.kamel.cleanexceptionlibrary.api.RetrofitClient

object CleanException {

    private var apiKey=""
    private var apiSecret=""
    private var projectKey=""

    private var exceptionTitle=""
    private var exceptionDescription=""


    fun init(api_key: String, api_secret: String, project_key: String){
        apiKey=api_key
        apiSecret=api_secret
        projectKey=project_key
    }

    fun setTitle(title: String){
        exceptionTitle=title
    }

    fun setDescription(description: String){
        exceptionDescription=description
    }

    fun sendExceptionError(exception: Exception, context: Context){

        var mPackage = context.packageName
        var path=Utilities.getDataDir(context, mPackage)

        val stackTraceElements = exception.stackTrace

        var cleanStackTrace=stackTraceElements.filter { Utilities.isNotFactoryPackage(it.className,mPackage) }


        RetrofitClient.retrofitInstance.Errors(
            apiKey,
            apiSecret,
            projectKey,
            0,
            cleanStackTrace[cleanStackTrace.size-1].lineNumber,
            cleanStackTrace[cleanStackTrace.size-1].fileName,
            path,
            exceptionDescription,
            exceptionTitle
        )
            .enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(
                        context.applicationContext,
                        "error: " + t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context.applicationContext, "Exception Sent Successfully", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(context.applicationContext, "Error occurred", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            })
    }


}