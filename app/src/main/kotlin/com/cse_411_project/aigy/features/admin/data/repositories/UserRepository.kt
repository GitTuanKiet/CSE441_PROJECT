package com.cse_411_project.aigy.features.admin.data.repositories

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import kotlin.collections.mutableListOf as mutableListOf
import com.google.firebase.firestore.FirebaseFirestore
import com.cse_411_project.aigy.features.admin.data.models.AgentModel
import com.cse_411_project.aigy.features.admin.data.models.ConversationModel
import com.cse_411_project.aigy.features.admin.data.models.MessageModel
import com.cse_411_project.aigy.features.admin.data.models.UserModel

class UserRepository {
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")
    // Map lại dữ liệu
    fun getData(userSnapshot: DataSnapshot) : UserModel {
        val uid = userSnapshot.child("uid").getValue(String::class.java)
        val name = userSnapshot.child("name").getValue(String::class.java)
        val email = userSnapshot.child("email").getValue(String::class.java)
        val phone = userSnapshot.child("phone").getValue(String::class.java)
        val urlImg = userSnapshot.child("urlImg").getValue(String::class.java)
        val decentralization = userSnapshot.child("decentralization").getValue(String::class.java)
        val isOnline = userSnapshot.child("online").getValue(Boolean::class.java)
        val idListAgent = userSnapshot.child("idListAgent").getValue(object : GenericTypeIndicator<MutableList<String>>() {}) ?: mutableListOf()
        val listVisit = userSnapshot.child("listVist").getValue(object : GenericTypeIndicator<MutableList<Long>>() {}) ?: mutableListOf()
        val user = UserModel(uid ?: "", name ?: "", email ?: "", phone ?: "", urlImg ?: "", decentralization ?: "", isOnline ?: false, idListAgent , listVisit)
        return user
    }
    // Lấy tất cả danh sách người dùng từ fire base
    fun getUsers(onComplete: (List<UserModel>) -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<UserModel>()
                for (userSnapshot in snapshot.children) {
                    userList.add(getData(userSnapshot))
                }
                onComplete(userList)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }
    // Tìm người dùng theo tên
    fun getUserByName(name: String, onComplete: (List<UserModel>) -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val matchingUsers = mutableListOf<UserModel>()
                for (userSnapshot in snapshot.children) {
                    val user = getData(userSnapshot)
                    user?.let {
                        // Kiểm tra xem tên người dùng có chứa chuỗi tìm kiếm không (không phân biệt chữ hoa/thường)
                        if (it.name.contains(name, ignoreCase = true)) {
                            matchingUsers.add(it)
                        }
                    }
                }
                // Trả về danh sách người dùng khớp với tên tìm kiếm
                onComplete(matchingUsers) // Trả về một danh sách List<UserModel>
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }
    // Tìm người dùng theo UID
    fun getUserByUID(uid: String, onComplete: (UserModel?) -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var foundUser: UserModel? = null

                for (userSnapshot in snapshot.children) {
                    val user = getData(userSnapshot)
                    user.let{
                        if (user.uid == uid) {
                            foundUser = user
                        }
                    }
                }
                onComplete(foundUser)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }
    // Chỉnh sửa thông tin người dùng
    fun updateUser(user: UserModel, onComplete: () -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.child(user.uid).setValue(user)
            .addOnSuccessListener { onComplete() }
            .addOnFailureListener { e -> onError(DatabaseError.fromException(e)) }
    }
    // Xóa người dùng
    fun deleteUser(uid: String, onComplete: () -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.orderByChild("uid").equalTo(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userSnapshot = snapshot.children.first()
                    val userKey = userSnapshot.key
                    userKey?.let { key ->
                        usersRef.child(key).removeValue()
                            .addOnSuccessListener {
                                onComplete()
                            }
                            .addOnFailureListener { e ->
                                onError(DatabaseError.fromException(e))
                            }
                    }
                } else {
                    Log.d("loi", "Người dùng không tồn tại")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }
    // Tạo người dùng
    fun createUser(userModel: UserModel, onComplete: () -> Unit, onError: (DatabaseError) -> Unit) {
        val db = FirebaseDatabase.getInstance().getReference("users")
        db.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var isException = false
                for (data in snapshot.children){
                    val user = getData(data)
                    if (user.uid == userModel.uid){
                        isException = true
                        break
                    }
                }
                if(!isException){
                    db.child(userModel.uid).setValue(userModel)
                        .addOnSuccessListener { onComplete() }
                        .addOnFailureListener { e -> onError(DatabaseError.fromException(e)) }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }
    // Tạo trợ lý
    fun createAgent(userId: String , agentId : String, onComplete: () -> Unit, onError: (DatabaseError) -> Unit) {
        val db_agents = FirebaseDatabase.getInstance().getReference("agents")
        val db_users = FirebaseDatabase.getInstance().getReference("users")

        db_users.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val user = getData(data)
                    if (user.uid == userId) {
                        val agent = AgentModel(agentId, userId, emptyList())
                        val currentList = user.idListAgent.toMutableList()
                        if(!currentList.contains(agentId)){
                            currentList.add(agentId)
                            val updateList = mapOf("idListAgent" to currentList)
                            db_agents.child(agentId).setValue(agent).addOnSuccessListener {
                                db_users.child(userId).updateChildren(updateList).addOnSuccessListener {
                                    onComplete()
                                }
                                    .addOnFailureListener { e -> onError(DatabaseError.fromException(e)) }
                            }
                                .addOnFailureListener {
                                    onError(DatabaseError.fromException(it))
                                }
                        }else{
                            onError(DatabaseError.fromException(Exception("Agent ID already exists in the user's list.")))
                        }

                    }
                    return
                    }
                }
            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }

    // Tạo cuộc cho chuyện với Agents
    fun createConversation(
        id: String,
        userId: String,
        agentId: String,
        onComplete: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        val conversationRef = db.collection("conversations").document(id)
        conversationRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    onError(Exception("Conversation already exists"))
                } else {
                    val newConversation = ConversationModel(id, userId, agentId, emptyList())
                    conversationRef.set(newConversation)
                        .addOnSuccessListener {
                            onComplete()
                        }
                        .addOnFailureListener { e ->
                            onError(e)
                        }
                }
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    fun fetchVisit(onComplete: (List<List<Long>>) -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val userVisits = mutableListOf<List<Long>>()
                for (data in snapshot.children) {
                    val user = getData(data)
                    user.let {
                        userVisits.add(it.listVist)
                    }
                }
                onComplete(userVisits)
            } else {
                onComplete(emptyList())
            }
        }
            .addOnFailureListener { e ->
                onError(DatabaseError.fromException(e))
            }
    }
    // Tạo tin nhắn trong cuộc trò chuyện
    fun createMessage(
        id: String,
        conversationId : String,
        senderId: String,
        content: String,
        type: String,
        completionTime : Long,
        timestamp: Long,
        onComplete: () -> Unit,
        onError: (Exception) -> Unit){
        val db = FirebaseFirestore.getInstance()
        val messageRef = db.collection("messages").document(id)
        messageRef.get()
            .addOnSuccessListener { document ->
                if(document.exists()){
                    onError(Exception("Message already exists"))
                }else{
                    val newMessage = MessageModel(id, senderId, content, type, completionTime, timestamp)
                    messageRef.set(newMessage)
                        .addOnSuccessListener {
                            updateConversationWithMessageId(conversationId , id , onComplete , onError)
                            onComplete()
                        }
                        .addOnFailureListener {
                            e -> onError(e)
                        }
                }
            }
            .addOnFailureListener {

            }
    }

    fun updateConversationWithMessageId(conversationId: String, messageId: String, onComplete: () -> Unit, onError: (Exception) -> Unit){
        val db = FirebaseFirestore.getInstance()
        val conversationRef = db.collection("conversations").document(conversationId)
        conversationRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Lấy danh sách ID tin nhắn hiện tại
                    val existingMessageIds = document.get("messages") as? List<String> ?: emptyList()

                    // Cập nhật danh sách ID với ID mới
                    val updatedMessageIds = existingMessageIds + messageId

                    // Cập nhật tài liệu cuộc trò chuyện
                    conversationRef.update("messages", updatedMessageIds)
                        .addOnSuccessListener {
                            onComplete()
                        }
                        .addOnFailureListener { e ->
                            onError(e)
                        }
                } else {
                    onError(Exception("Conversation not found"))
                }
            }
            .addOnFailureListener { e ->
                onError(e) // Xử lý lỗi nếu có
            }
    }
}
