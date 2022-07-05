package github.luthfipun.composemodularize

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import github.luthfipun.domain.entity.Fact
import github.luthfipun.domain.usecase.FactUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val factUseCase: FactUseCase
): ViewModel() {
    private val _facts = MutableSharedFlow<PagingData<Fact>>()
    val facts = _facts.asSharedFlow()

    init {
        getFacts()
    }

    private fun getFacts() {
        viewModelScope.launch(Dispatchers.IO) {
            factUseCase.invoke()
                .onEach { _facts.emit(it) }
                .launchIn(viewModelScope)
        }
    }
}