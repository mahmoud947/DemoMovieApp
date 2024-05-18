package com.example.movieapptask.ui.fragments.movie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Movie
import com.example.movieapptask.databinding.ItemMovieBinding
import com.example.movieapptask.databinding.ItemMovieShimmerBinding

private const val SHIMMER_TYPE = 0
private const val MOVIE_TYPE = 1

class MovieAdapter(private val interaction: Interaction? = null) :
    ListAdapter<Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    var isLoading = true

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == MOVIE_TYPE) {
            MovieViewHolder.from(parent, interaction = interaction)

        } else {
            MovieShimmerViewHolder.from(parent)
        }
    }

    override fun getItemCount(): Int {
        return if (isLoading) 7 else super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading) {
            SHIMMER_TYPE
        } else {
            MOVIE_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                val item = getItem(position)
                holder.onBind(item)
            }

            is MovieShimmerViewHolder -> {
                holder.onBind(isLoading)
            }
        }
    }


    class MovieViewHolder
    constructor(
        private val binding: ItemMovieBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Movie) {
            binding.movie = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                interaction?.onItemSelected(bindingAdapterPosition, item)
            }
        }


        companion object {
            fun from(viewGroup: ViewGroup, interaction: Interaction?): MovieViewHolder {
                val bind = ItemMovieBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return MovieViewHolder(bind, interaction = interaction)
            }
        }


    }


    class MovieShimmerViewHolder constructor(
        private val binding: ItemMovieShimmerBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(startShimmer: Boolean) {
            if (startShimmer) {
                binding.shimmer.startShimmer()
            } else {
                binding.shimmer.stopShimmer()
                binding.shimmer.setShimmer(null)
            }
        }

        companion object {
            fun from(viewGroup: ViewGroup): MovieShimmerViewHolder {
                val bind = ItemMovieShimmerBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return MovieShimmerViewHolder(bind)
            }
        }

    }


    interface Interaction {
        fun onItemSelected(position: Int, item: Movie)
    }
}
