package com.david.catChat.viewmodels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.david.catChat.data.Chat
import com.david.catChat.data.RequestState
import com.david.catChat.database.FirestoreService
import kotlinx.coroutines.launch

class HomeViewModel(private val db: FirestoreService) : ViewModel() {

    private var _chats by mutableStateOf<List<Chat?>>(listOf())
    private var _requestState by mutableStateOf(RequestState.NONE)

    init {
        fetchChats()
    }

    val chats: List<Chat?>
        get() = _chats

    val requestState: RequestState
        get() = _requestState


    private fun fetchChats() {
        _requestState = RequestState.LOADING
        viewModelScope.launch {
            val fetchedChats = db.getChats()
            _chats = if (fetchedChats.isNullOrEmpty()) {
                listOf()
            } else {
                fetchedChats
            }
            _requestState = RequestState.SUCCESS
        }
    }

}


class HomeViewModelFactory(
    private val db: FirestoreService
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(db) as T
    }
}
