package com.example.androidflowexample.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.androidflowexample.R
import com.example.androidflowexample.core.data.network.model.common.MovieOverview
import com.example.androidflowexample.databinding.ItemMovieOverviewBinding


class HomeAdapter(
    private val onItemClicked: (MovieOverview) -> Unit,
) : ListAdapter<MovieOverview, HomeAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding) {
            onItemClicked(this.getItem(it))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class ViewHolder(private val binding: ItemMovieOverviewBinding, onItemClicked: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { onItemClicked(absoluteAdapterPosition) }
        }

        fun bind(model: MovieOverview) {
            with(binding){
                imageView.loadFromUrl("${HomeConstants.IMAGE_BASE_URL}${model.posterPath}", R.drawable.holder)
                textView.text = model.title
                textView2.text = model.overview
                textView3.text = model.releaseDate
            }

        }

    }
}

class DiffCallback : DiffUtil.ItemCallback<MovieOverview>() {
    override fun areItemsTheSame(
        oldItem: MovieOverview,
        newItem: MovieOverview
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MovieOverview,
        newItem: MovieOverview
    ) =
        oldItem == newItem
}

@SuppressLint("CheckResult")
fun ImageView.loadFromUrl(
    url: String? = "",
    defaultHolder: Int,
    shouldCircleCrop: Boolean = false
) {
    val requestBuilder = Glide.with(this.context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(defaultHolder)
            .error(defaultHolder)
    ).load(url)

    if (shouldCircleCrop) {
        requestBuilder.circleCrop()
    }
    requestBuilder.into(this@loadFromUrl)
}