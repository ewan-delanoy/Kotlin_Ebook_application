package com.example.ebookapplication

import com.example.example.Ebook

interface EbookItemCallback {
    fun onCellClick(ebook: Ebook)
    fun onSaveEbook(ebook: Ebook)
}