package com.example.quiz

import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val rank: TextView = view.findViewById(R.id.tv_rank)
    val username: TextView = view.findViewById(R.id.tv_username)
    val score: TextView = view.findViewById(R.id.tv_score)
}

class RecyclerAdapter(var cursor: Cursor?) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.leaderboard, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        val cursor = cursor
        Log.d("Adapter", "${cursor?.count}")

        return cursor?.count ?: 0

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val cursor = cursor
        if (cursor != null) {
            if (cursor.moveToNext()) {
                val username = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_USERNAME))
                val score = cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_SCORE))

                holder.rank.text = (position + 1).toString()
                holder.score.text = score.toString()
                holder.username.text = username

            }
        }


    }

    fun swapCursor(newCursor: Cursor?): Cursor? {

        if (newCursor === cursor) {
            return null
        }
        val numItems = itemCount
        val oldCursor = cursor
        cursor = newCursor
        if (newCursor != null) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeRemoved(0, numItems)
        }
        return oldCursor
    }
}