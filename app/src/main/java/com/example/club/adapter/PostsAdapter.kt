package com.example.club.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.club.R
import com.example.club.databinding.ItemPostsBinding
import com.example.club.network.model.PostsModel

/** 帖子列表 Adapter */
class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    private val postsList = mutableListOf<PostsModel>()

    fun submitList(list: List<PostsModel>) {
        postsList.clear()
        postsList.addAll(list)
        notifyDataSetChanged()
    }

    fun addData(list: List<PostsModel>) {
        val oldSize = postsList.size
        postsList.addAll(list)
        notifyItemRangeInserted(oldSize, list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding = ItemPostsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bind(postsList[position])
    }

    override fun getItemCount(): Int = postsList.size

    inner class PostsViewHolder(
        private val binding: ItemPostsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: PostsModel) {
            // 用户信息
            binding.ivAvatar.load(post.avatar?.url ?: post.avatar?.thumbnailUrl) {
                crossfade(true)
                placeholder(R.drawable.circle_background)
                error(R.drawable.circle_background)
                transformations(RoundedCornersTransformation(8f))
            }
            binding.tvNickName.text = post.nickName.ifEmpty { "未知用户" }
            binding.tvVehicleModel.text = post.vehicleModelName.ifEmpty { "" }
            binding.tvTime.text = post.createTime

            // 帖子内容
            binding.tvContent.text = post.content
            binding.tvContent.visibility = if (post.content.isNotEmpty()) {
                ViewGroup.VISIBLE
            } else {
                ViewGroup.GONE
            }

            // 图片列表
            post.img?.let { images ->
                if (images.isNotEmpty()) {
                    binding.rvImages.visibility = ViewGroup.VISIBLE
                    binding.rvImages.layoutManager = GridLayoutManager(
                        itemView.context,
                        if (images.size > 1) 3 else 1
                    )
                    binding.rvImages.adapter = ImagesAdapter(images.map { it.url })
                } else {
                    binding.rvImages.visibility = ViewGroup.GONE
                }
            } ?: run {
                binding.rvImages.visibility = ViewGroup.GONE
            }

            // 互动数据 - 使用 likeNumber 和 commentNumber 等字段（如果存在）
            val likeCount = if (post.likeNumber > 0) post.likeNumber else post.giveLikeCount
            val commentCount = if (post.commentNumber > 0) post.commentNumber else post.commentCount
            val shareCount = if (post.shareNumber > 0) post.shareNumber else post.shareCount
            
            binding.tvLikeCount.text = formatNumber(likeCount)
            binding.tvCommentCount.text = formatNumber(commentCount)
            binding.tvShareCount.text = formatNumber(shareCount)

            // 点赞状态
            updateLikeStatus(post.isGiveLike)

            // 点赞点击事件
            binding.ivLike.setOnClickListener {
                val newPosition = adapterPosition
                if (newPosition != RecyclerView.NO_POSITION) {
                    val isLiked = !postsList[newPosition].isGiveLike
                    postsList[newPosition].isGiveLike = isLiked
                    postsList[newPosition].giveLikeCount += if (isLiked) 1 else -1
                    notifyItemChanged(newPosition)
                    onItemClickListener?.onLikeClick(postsList[newPosition], isLiked)
                }
            }
        }

        private fun updateLikeStatus(isLiked: Boolean) {
            binding.ivLike.setImageResource(
                if (isLiked) R.drawable.ic_add else R.drawable.ic_add
            )
            binding.ivLike.alpha = if (isLiked) 1.0f else 0.5f
        }

        private fun formatNumber(num: Int): String {
            return when {
                num >= 10000 -> String.format("%.1f 万", num / 10000.0)
                num >= 1000 -> String.format("%.1fk", num / 1000.0)
                else -> num.toString()
            }
        }
    }

    // 图片 Adapter
    inner class ImagesAdapter(
        private val images: List<String>
    ) : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

        inner class ImageViewHolder(
            private val imageView: android.widget.ImageView
        ) : RecyclerView.ViewHolder(imageView) {

            fun bind(url: String) {
                imageView.load(url) {
                    crossfade(true)
                    placeholder(R.drawable.circle_background)
                    error(R.drawable.circle_background)
                    transformations(RoundedCornersTransformation(4f))
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            val imageView = android.widget.ImageView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                scaleType = android.widget.ImageView.ScaleType.CENTER_CROP
                adjustViewBounds = true
            }
            return ImageViewHolder(imageView)
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            holder.bind(images[position])
        }

        override fun getItemCount(): Int = images.size
    }

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onLikeClick(post: PostsModel, isLiked: Boolean)
    }
}
