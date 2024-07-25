package com.dmm.bootcamp.yatter2024.ui.profile

import android.provider.ContactsContract.Profile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.infra.pref.TokenPreferences
import com.dmm.bootcamp.yatter2024.ui.login.LoginDestination
import com.dmm.bootcamp.yatter2024.ui.post.PostDestination
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.StatusBindingModel
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.converter.StatusConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val accountRepository: AccountRepository,
    private val statusRepository: StatusRepository,
    private val tokenPreferences: TokenPreferences,
    val username: String = "",
) : ViewModel() {
    private val _uiState: MutableStateFlow<ProfileUiState> =
        MutableStateFlow(ProfileUiState.empty())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()
    init{
        viewModelScope.launch {
            val account = accountRepository.findByUsername(Username(username))
            _uiState.update {
                it.copy(
                    profileBindingModel = ProfileBindingModel(
                        username = username,
                        numFollow = account?.followingCount,
                        numFollower = account?.followerCount,
                        numPost = statusRepository.findStatusByUsername(username).size,
                    )
                )
            }
        }
    }
    private suspend fun fetchProfile(){
        val statusList = statusRepository.findStatusByUsername(uiState.value.profileBindingModel.username)
        _uiState.update{
            it.copy(
                statusList = StatusConverter.convertToBindingModel(statusList),
            )
        }
    }
    fun onResume() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true)}
            fetchProfile()
            _uiState.update { it.copy(isLoading = false) }
        }
    }
    fun onRefresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            fetchProfile()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }
    fun onClickPost(){
        _destination.value = PostDestination()
    }
    fun onClickLogout(){
        tokenPreferences.clear()
        _destination.value = LoginDestination()
    }
    fun onClickFollow(){}
    fun onClickFollower(){}
    fun onCompleteNavigation() {
        _destination.value = null
    }
}