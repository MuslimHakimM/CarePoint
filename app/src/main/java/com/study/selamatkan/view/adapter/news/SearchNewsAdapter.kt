package com.study.selamatkan.view.adapter.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.study.selamatkan.R
import com.study.selamatkan.data.domain.model.SearchNews
import com.study.selamatkan.databinding.ItemListNewsBinding
import com.study.selamatkan.utils.HelpUtil.newsFormatDate
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class SearchNewsAdapter : RecyclerView.Adapter<SearchNewsAdapter.SearchViewHolder>() {

    private var listNews = ArrayList<SearchNews>()
    var onItemClick: ((SearchNews) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setNews(data: List<SearchNews>) {
        listNews.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        ItemListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false).let {
            return SearchViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(listNews[position])
    }

    override fun getItemCount(): Int = listNews.size

    inner class SearchViewHolder(
        private val binding: ItemListNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: SearchNews) {
            with(binding) {
                val shimmer =
                    Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                        .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                        .setBaseAlpha(0.7f) //the alpha of the underlying children
                        .setHighlightAlpha(0.6f) // the shimmer alpha amount
                        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                        .setAutoStart(true)
                        .build()

                val shimmerDrawable = ShimmerDrawable().apply {
                    setShimmer(shimmer)
                }

                Glide.with(itemView.context)
                    .load(data.urlToImage)
                    .transform(RoundedCorners(20)).apply(
                        RequestOptions.placeholderOf(shimmerDrawable).error(R.drawable.ic_error)
                    )
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imgNews)

                tvTitleNews.text = data.title
                if (data.content.isEmpty() || data.content == "null") {
                    tvContentNews.text = "Klik untuk melihat lebih detil"
                } else {
                    tvContentNews.text = data.content
                }

                tvDateNews.text = newsFormatDate(data.publishedAt)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listNews[adapterPosition])
            }
        }
    }
}