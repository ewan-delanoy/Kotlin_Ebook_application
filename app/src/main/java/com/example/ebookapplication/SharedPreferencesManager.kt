package com.example.ebookapplication

import android.content.Context
import android.util.Log
import com.google.gson.Gson

import com.example.example.Ebook

class SharedPreferencesManager {
    companion object {
        const val ebookListKey = "ebookListKey"
        const val searchKey = "searchKey"
        const val preferencesFile = "preferencesFile"
    }

    fun saveSearchCriteria(search:String, context: Context) {
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        sharedPreferences
            .edit()
            .putString(searchKey, search)
            .apply()
    }

    fun getSearchCriteria(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        return sharedPreferences.getString(searchKey,"")
    }

    fun getLocalEbookStorage(context: Context): LocalEbookStorage {
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedPreferences.getString(ebookListKey, "")
        if (json.isNullOrEmpty()) {
            return LocalEbookStorage(mutableListOf())
        }
        return gson.fromJson(json, LocalEbookStorage::class.java)
    }

    fun saveEbook(ebook: Ebook, context: Context): Boolean {
        var localEbookStorage = getLocalEbookStorage(context)
        if (localEbookStorage.localEbooks.contains(ebook)) {
            return false
        }
        localEbookStorage.localEbooks.add(ebook)
        Log.d("saveEbook", " size "+localEbookStorage.localEbooks.size)
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(localEbookStorage)
        sharedPreferences.edit().putString(ebookListKey, json).apply()
        return true
    }

    fun deleteEbook(ebook: Ebook, context: Context) {
        var localEbookStorage = getLocalEbookStorage(context)
        localEbookStorage.localEbooks.remove(ebook)
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(localEbookStorage)
        sharedPreferences.edit().putString(ebookListKey, json).apply()
    }
}