package com.example.ebookapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.ebookapplication.databinding.ActivityEbookDetailBinding

class EbookDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEbookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEbookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setViewItems()
    }

    private fun setViewItems() {
        val ebookTitle = intent.extras?.getString(Key.titleKey)
        val image = intent.extras?.getString(Key.imageKey)
        val ebookAuthor = intent.extras?.getString(Key.authorKey)
        val ebookDescription = intent.extras?.getString(Key.descriptionKey)
        setTitle(ebookTitle)
        binding.ebookTitleTv.text = ebookTitle
        binding.authorTv.text = ebookAuthor
        binding.descriptionTv.text = ebookDescription
        Glide.with(binding.ebookIv.context).load(image)
            .into(binding.ebookIv)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}