package com.example.ebookapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebookapplication.databinding.ActivityMainBinding
import com.example.example.Ebook
import com.example.example.EbookApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        var searchResults : MutableList<Ebook> = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewItems()
    }

    private fun setViewItems() {

        val storedSearch = SharedPreferencesManager().getSearchCriteria(this)
        if (storedSearch != null) {
            binding.searchEt.setText(storedSearch)
        }

        binding.searchBt.setOnClickListener {
            checkUserInput()
            callService()
            binding.searchBt.visibility = View.INVISIBLE
            binding.progress.visibility = View.VISIBLE
        }
        binding.favoritesBt.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }
    }

    private fun checkUserInput() {
        if (binding.searchEt.text.toString().isEmpty()) {
            Toast.makeText(this, "Veuillez effectuer une saisie", Toast.LENGTH_LONG).show()
            return
        }
    }

    private fun callService() {
        val service: EbookApi.EbookService =
            EbookApi().getClient().create(EbookApi.EbookService::class.java)
        val call: Call<EbookApiResponse> =
            service.getEbooks(
                /*"IlQOgCrui5R9g9aHW8r3Frhk78vqlbwWeaYmaMG25eJtzJtCOZyUdNEw", */
                binding.searchEt.text.toString()
            )
        call.enqueue(object : Callback<EbookApiResponse> {
            override fun onResponse(
                call: Call<EbookApiResponse>,
                response: Response<EbookApiResponse>
            ) {
                processResponse(response)
                searchEnded()
            }

            override fun onFailure(call: Call<EbookApiResponse>, t: Throwable) {
                processFailure(t)
                searchEnded()
            }
        })
    }

    private fun searchEnded() {
        binding.searchBt.visibility = View.VISIBLE
        binding.progress.visibility = View.INVISIBLE
        SharedPreferencesManager().saveSearchCriteria(binding.searchEt.text.toString(), this)
    }

    private fun processFailure(t: Throwable) {
        Toast.makeText(this, "Erreur", Toast.LENGTH_LONG).show()
    }

    private fun processResponse(response: Response<EbookApiResponse>) {
        if (response.body() != null) {            val body = response.body()
            if (body?.items?.isNotEmpty() == true) {
                val intent = Intent(this, ViewResultsActivity::class.java)
                searchResults = body.items
                startActivity(intent)
            }
        }
    }

    private fun saveEbook(ebook: Ebook) {
        if (SharedPreferencesManager().saveEbook(ebook, this)){
            Toast.makeText(this,"Enregistrement bien effectué", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this,"Ce ebook est déjà dans vos favoris", Toast.LENGTH_LONG).show()
        }
    }

}