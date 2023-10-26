package com.example.ebookapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.ebookapplication.databinding.ActivityFavoritesBinding

import com.example.example.Ebook

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setViewItems()
    }

    private fun setViewItems() {
        setTitle(R.string.favorites)
        displayEbookList(SharedPreferencesManager().getLocalEbookStorage(this).localEbooks)
    }


    fun displayEbookList(ebooks: MutableList<Ebook>) {
        val adapter = EbookListViewAdapter(ebooks, object : EbookItemCallback {
            override fun onCellClick(ebook: Ebook) {
                gotoEbookDetailActivity(ebook)
            }
            override fun onSaveEbook(ebook: Ebook) {
                // here in favorites list we should rather delete
                deleteEbook(ebook)
            }
        })
        binding.ebookRv.adapter = adapter
        binding.ebookRv.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun deleteEbook(ebook: Ebook) {
        SharedPreferencesManager().deleteEbook(ebook, this)
        displayEbookList(SharedPreferencesManager().getLocalEbookStorage(this).localEbooks)
    }

    fun gotoEbookDetailActivity(ebook: Ebook) {
        val intent = Intent(this, EbookDetailActivity::class.java)
        intent.putExtra(Key.titleKey, ebook.volumeInfo?.title)
        intent.putExtra(Key.authorKey, ebook.volumeInfo?.authors!![0])
        intent.putExtra(Key.descriptionKey, ebook.volumeInfo?.description)
        intent.putExtra(Key.imageKey, ebook.volumeInfo?.imageLinks?.thumbnail)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}