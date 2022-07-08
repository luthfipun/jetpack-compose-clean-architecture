package github.luthfipun.composemodularize

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import github.luthfipun.domain.usecase.FactUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    factUseCase: FactUseCase
): ViewModel() {
    val factFlow = factUseCase.getFactUseCase()
}