package com.example.ebookapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.ebookapplication.databinding.ActivityViewResultsBinding

import com.example.example.Ebook

class ViewResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setViewItems()
    }

    private fun setViewItems() {
        setTitle(R.string.results)
        displayEbookList(MainActivity.searchResults)
    }


    fun displayEbookList(ebooks: MutableList<Ebook>) {
        val adapter = EbookListViewAdapter(ebooks, object : EbookItemCallback {
            override fun onCellClick(ebook: Ebook) {
                gotoEbookDetailActivity(ebook)
            }
            override fun onSaveEbook(ebook: Ebook) {
                saveEbook(ebook)
            }
        })
        binding.ebookRv.adapter = adapter
        binding.ebookRv.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun saveEbook(ebook: Ebook) {
        if (SharedPreferencesManager().saveEbook(ebook, this)){
            Toast.makeText(this,"Enregistrement bien effectué", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this,"Cet ebook est déjà dans votre bibliothèque", Toast.LENGTH_LONG).show()
        }
    }

    private fun gotoEbookDetailActivity(ebook: Ebook) {
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