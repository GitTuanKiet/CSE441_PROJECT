package com.cse_411_project.aigy.features.chat.ui

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cse_411_project.aigy.BuildConfig
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import com.cse_411_project.aigy.features.chat.data.ChatRequest
import com.cse_411_project.aigy.features.chat.data.ChatResponse
import com.cse_411_project.aigy.features.chat.data.Result
import com.cse_411_project.aigy.features.chat.data.ChatApiRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers

//class ChatViewModel(private val webApiRepository: ChatApiRepository = ChatApiRepository()): ViewModel() {
//    private var _botMessages = MutableLiveData<ChatResponse>()
//    val botMessages: LiveData<ChatResponse> = _botMessages
//    private var _error =  MutableLiveData<Exception>()
//    val error: LiveData<Exception> = _error
//
//    private val generativeModel = GenerativeModel(
//        modelName = "google/gemini-1.5-flash",
//        apiKey = BuildConfig.apiKey
//    )
//
//    fun getMessages(accessToken: String, apiKey: String, chatRequest: ChatRequest) {
//        viewModelScope.launch {
//            try {
//                withTimeout(30000) {
//                    val result = webApiRepository.chatWithBot(accessToken, apiKey, chatRequest)
//                    if (result is Result.Success) {
//                        _botMessages.value = result.data
//                    } else if (result is Result.Error) {
//                        _error.value = result.exception
//                    }
//                }
//            } catch (e: TimeoutCancellationException) {
//                val data = ChatResponse.Data(
//                    answer = "Please ask another time, we are busy at the moment.",
//                    suggestedQuestions = emptyList()
//                )
//                _botMessages.value = ChatResponse(status = "error", data = data)
//            }
//        }
//    }
//
//    fun sendPrompt(
////        bitmap: Bitmap,
//        prompt: String
//    ) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = generativeModel.generateContent(
//                    content {
////                        image(bitmap)
//                        text(prompt)
//                    }
//                )
//                response.text?.let { outputContent ->
////                    _uiState.value = UiState.Success(outputContent)
//                    _botMessages.value = ChatResponse(
//                        status = "success",
//                        data = ChatResponse.Data(
//                            answer = outputContent,
//                            suggestedQuestions = emptyList()
//                        )
//                    )
//                }
//            } catch (e: Exception) {
////                _uiState.value = UiState.Error(e.localizedMessage ?: "")
//                _error.value = e
//                _botMessages.value = ChatResponse(
//                    status = "error",
//                    data = ChatResponse.Data(
//                        answer = "Please ask another time, we are busy at the moment.",
//                        suggestedQuestions = emptyList()
//                    )
//                )
//            }
//        }
//    }
//}

//class ChatViewModel(private val webApiRepository: ChatApiRepository = ChatApiRepository()): ViewModel() {
//    private var _botMessages = MutableLiveData<ChatResponse>()
//    val botMessages: LiveData<ChatResponse> = _botMessages
//    private var _error =  MutableLiveData<Exception>()
//    val error: LiveData<Exception> = _error
//
//    private val generativeModel = GenerativeModel(
//        modelName = "google/gemini-1.5-flash",
//        apiKey = BuildConfig.apiKey
//    )
//
//    fun getMessages(accessToken: String, apiKey: String, chatRequest: ChatRequest) {
//        viewModelScope.launch {
//            try {
//                withTimeout(30000) {
//                    val result = webApiRepository.chatWithBot(accessToken, apiKey, chatRequest)
//                    if (result is Result.Success) {
//                        _botMessages.postValue(result.data)
//                    } else if (result is Result.Error) {
//                        _error.postValue(result.exception)
//                    }
//                }
//            } catch (e: TimeoutCancellationException) {
//                val data = ChatResponse.Data(
//                    answer = "Please ask another time, we are busy at the moment.",
//                    suggestedQuestions = emptyList()
//                )
//                _botMessages.postValue(ChatResponse(status = "error", data = data))
//            }
//        }
//    }
//
//    fun sendPrompt(
////        bitmap: Bitmap,
//        prompt: String
//    ) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = generativeModel.generateContent(
//                    content {
////                        image(bitmap)
//                        text(prompt)
//                    }
//                )
//                response.text?.let { outputContent ->
////                    _uiState.value = UiState.Success(outputContent)
//                    _botMessages.postValue(ChatResponse(
//                        status = "success",
//                        data = ChatResponse.Data(
//                            answer = outputContent,
//                            suggestedQuestions = emptyList()
//                        )
//                    ))
//                }
//            } catch (e: Exception) {
////                _uiState.value = UiState.Error(e.localizedMessage ?: "")
//                _error.postValue(e)
//                _botMessages.postValue(ChatResponse(
//                    status = "error",
//                    data = ChatResponse.Data(
//                        answer = "Please ask another time, we are busy at the moment.",
//                        suggestedQuestions = emptyList()
//                    )
//                ))
//            }
//        }
//    }
//}

class ChatViewModel(private val webApiRepository: ChatApiRepository = ChatApiRepository()): ViewModel() {
    private var _botMessages = MutableLiveData<ChatResponse>()
    val botMessages: LiveData<ChatResponse> = _botMessages
    private var _error =  MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    fun getMessages(accessToken: String, apiKey: String, chatRequest: ChatRequest) {
        viewModelScope.launch {
            try {
                withTimeout(30000) {
                    val result = webApiRepository.chatWithBot(accessToken, apiKey, chatRequest)
                    if (result is Result.Success) {
                        _botMessages.postValue(result.data)
                    } else if (result is Result.Error) {
                        _error.postValue(result.exception)
                    }
                }
            } catch (e: TimeoutCancellationException) {
                val data = ChatResponse.Data(
                    answer = "Please ask another time, we are busy at the moment.",
                    suggestedQuestions = emptyList()
                )
                _botMessages.postValue(ChatResponse(status = "error", data = data))
            }
        }
    }

    fun sendPrompt(
        prompt: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(prompt)

                response.text?.let { outputContent ->
                    _botMessages.postValue(ChatResponse(
                        status = "success",
                        data = ChatResponse.Data(
                            answer = outputContent,
                            suggestedQuestions = emptyList()
                        )
                    ))
                } ?: run {
                    Log.e("ChatViewModel", "Response text is null")
                    _botMessages.postValue(ChatResponse(
                        status = "error",
                        data = ChatResponse.Data(
                            answer = "No response received.",
                            suggestedQuestions = emptyList()
                        )
                    ))
                }
            } catch (e: Exception) {
                Log.e("ChatViewModel", "sendPrompt error: ${e.localizedMessage}", e)
                _error.postValue(e)
                _botMessages.postValue(ChatResponse(
                    status = "error",
                    data = ChatResponse.Data(
                        answer = "Please ask another time, we are busy at the moment.",
                        suggestedQuestions = emptyList()
                    )
                ))
            }
        }
    }
}