package br.com.noke.twogether.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.noke.twogether.model.Category
import br.com.noke.twogether.model.User
import br.com.noke.twogether.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
//class UserViewModel(private val userRepository: UserRepository) : ViewModel()
class UserViewModel(val userRepository: UserRepository) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users
    var userId: String? = null

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            userRepository.getUsers().collect { userList ->
                _users.value = userList
            }
        }
    }

//    fun addUser(user: User, onComplete: (Boolean) -> Unit = {}) {
//        viewModelScope.launch {
//            val result = userRepository.addUser(user)
//            onComplete(result)
//        }
//    }

    fun addUser(user: User, onComplete: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            userId = userRepository.addUserAndGetId(user)
            onComplete(userId != null)
        }
    }

    fun updateUserCategories(categories: List<Category>, onComplete: (Boolean) -> Unit) {
        userId?.let { id ->
            viewModelScope.launch {
                val success = userRepository.updateUserCategories(id, categories.map { it.name })
                onComplete(success)
            }
        } ?: onComplete(false)  // Se userId for null, retorna falso
    }








    // Adicionar métodos para buscar, atualizar e deletar
}



