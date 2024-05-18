package com.example.movieapptask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Movie
import com.example.movieapptask.databinding.TestItemBinding

class MovieAdapter(private val interaction: Interaction? = null) :
    ListAdapter<Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MovieViewHolder.from(parent, interaction = interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                val item = getItem(position)
                holder.onBind(item)
            }
        }
    }


    class MovieViewHolder
    constructor(
        private val binding: TestItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Movie) {
            binding.textView.text = item.title
            binding.root.setOnClickListener {
                interaction?.onItemSelected(bindingAdapterPosition, item)
            }
        }


        companion object {
            fun from(viewGroup: ViewGroup, interaction: Interaction?): MovieViewHolder {
                val bind = TestItemBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return MovieViewHolder(bind, interaction = interaction)
            }
        }


    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Movie)
    }
}
