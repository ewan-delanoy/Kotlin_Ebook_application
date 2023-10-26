package com.example.ebookapplication

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.example.Ebook

class EbookViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var ebookIV: ImageView
    private var authorTV: TextView
    private var titleTV : TextView
    private var containerCL: ConstraintLayout
    private var bookmark: ImageView

    init {
        ebookIV = itemView.findViewById(R.id.ebook_iv)
        titleTV = itemView.findViewById(R.id.ebook_title_tv)
        authorTV = itemView.findViewById(R.id.author_tv)
        containerCL = itemView.findViewById(R.id.container)
        bookmark = itemView.findViewById(R.id.bookmark)
    }

    fun bind(ebook: Ebook, ebookItemCallback: EbookItemCallback
    ) {
        authorTV.text = ebook.volumeInfo?.authors!![0]
        titleTV.text = ebook.volumeInfo?.title
        Glide.with(ebookIV.context).load(ebook.volumeInfo?.imageLinks?.thumbnail)
            .into(ebookIV)
        containerCL.setOnClickListener {
            ebookItemCallback.onCellClick(ebook)
        }
        bookmark.setOnClickListener {
            ebookItemCallback.onSaveEbook(ebook)
        }
    }
}


