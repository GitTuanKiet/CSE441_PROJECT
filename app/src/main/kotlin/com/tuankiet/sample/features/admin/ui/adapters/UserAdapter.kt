package com.tuankiet.sample.features.admin.ui.adapters

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.tuankiet.sample.R
import com.tuankiet.sample.features.admin.data.models.UserModel
import com.tuankiet.sample.features.admin.ui.fragments.DetailUserFragment
import com.tuankiet.sample.features.admin.ui.viewmodel.UserViewModel

class UserAdapter(
    private var userList: List<UserModel>,
    private val userViewModel: UserViewModel,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: UserModel = userList[position]
        if (user.email.isNotEmpty()) {
            holder.txtEmailPhone.text = user.email
        } else {
            holder.txtEmailPhone.text = user.phone
        }
        holder.txtName.text = user.name
        holder.txtUID.text = user.uid

        holder.imgDetail.setOnClickListener {

            try{
                // Tạo DetailUserFragment và truyền dữ liệu
                val detailFragment = DetailUserFragment()
                val bundle = Bundle()
                bundle.putString("uid", user.uid) // Truyền UID người dùng
                detailFragment.arguments = bundle

                // Thay thế UseManagementFragment bằng DetailUserFragment
                fragmentManager.beginTransaction()
                    .replace(R.id.displayData, detailFragment) // R.id.displayData là ID của container chứa fragment
                    .addToBackStack(null) // Để quay lại UseManagementFragment nếu cần
                    .commit()
            }
            catch (e : Exception){
                Log.e("loi" , e.message.toString())
            }
        }

        holder.imgDelete.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Thông báo")
                .setMessage("Bạn có chắc chắn muốn xóa?")
                .setPositiveButton("OK") { dialog, _ ->
                    userViewModel.deleteUser(user.uid)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }

            // Show the AlertDialog
            builder.create().show()
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtUID: TextView = itemView.findViewById(R.id.UID)
        var txtName: TextView = itemView.findViewById(R.id.NAME)
        var txtEmailPhone: TextView = itemView.findViewById(R.id.EMAIL_PHONE)
        var imgDetail: ImageView = itemView.findViewById(R.id.detail)
        var imgDelete: ImageView = itemView.findViewById(R.id.delete)
    }

    // Update the user list and notify the adapter
    fun updateUserList(newUserList: List<UserModel>) {
        userList = newUserList
        notifyDataSetChanged()
    }
}
