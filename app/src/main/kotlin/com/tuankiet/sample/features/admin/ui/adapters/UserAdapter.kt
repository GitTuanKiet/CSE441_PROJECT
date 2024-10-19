package com.tuankiet.sample.features.admin.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tuankiet.sample.R
import com.tuankiet.sample.features.admin.data.UserModel


class UserAdapter(var context: Context, userList: List<UserModel>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    var userList: List<UserModel> = userList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: UserModel = userList[position]
        holder.txtEmailPhone.setText(user.Email)
        holder.txtName.setText(user.Name)
        holder.txtUID.setText(user.UID)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtUID: TextView = itemView.findViewById<TextView>(R.id.UID)
        var txtName: TextView = itemView.findViewById<TextView>(R.id.NAME)
        var txtEmailPhone: TextView = itemView.findViewById<TextView>(R.id.EMAIL_PHONE)
    }
}
