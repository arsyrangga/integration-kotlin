package com.rangga.submission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rangga.submission.data.Account

class ListAccountAdapter(private val listAccount: List<Account>) : RecyclerView.Adapter<ListAccountAdapter.ListViewHolder>() {
    interface OnItemClickCallback {
        fun onItemClicked(data: Account)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int)  {
        val (name, avatar) = listAccount[position]
        Glide.with(holder.itemView.context)
            .load(avatar)
            .into(holder.imgPhoto)
        holder.tvName.text = name
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listAccount[position]) }
    }

    override fun getItemCount(): Int = listAccount.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
    }


}