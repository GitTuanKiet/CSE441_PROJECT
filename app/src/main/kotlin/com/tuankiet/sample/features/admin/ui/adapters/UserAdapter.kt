//package com.tuankiet.sample.features.admin.ui.adapters
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//
//class UserAdapter(var context: Context, userList: List<User>) :
//    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
//    var userList: List<User> = userList
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
//        val view: View =
//            LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
//        return UserViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
//        val user: User = userList[position]
//        holder.txtEmailPhone.setText(user.getEmailPhone())
//        holder.txtName.setText(user.getName())
//        holder.txtUID.setText(user.getUid())
//    }
//
//    override fun getItemCount(): Int {
//        return userList.size
//    }
//
//    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var txtUID: TextView = itemView.findViewById<TextView>(R.id.UID)
//        var txtName: TextView = itemView.findViewById<TextView>(R.id.NAME)
//        var txtEmailPhone: TextView = itemView.findViewById<TextView>(R.id.EMAIL_PHONE)
//    }
//}
