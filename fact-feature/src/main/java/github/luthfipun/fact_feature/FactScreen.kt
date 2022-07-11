package github.luthfipun.fact_feature

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import github.luthfipun.fact_feature.component.FactItem
import kotlinx.coroutines.launch

@Composable
fun FactScreen(factViewModel: FactViewModel = viewModel()){

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val lazyFactFlow = factViewModel.factFlow.collectAsLazyPagingItems()

    Scaffold(scaffoldState = scaffoldState) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(lazyFactFlow){ item ->
                FactItem(factString = item?.fact.orEmpty())
            }

            lazyFactFlow.apply {
                when{
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Column(
                                modifier = Modifier.fillParentMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) { CircularProgressIndicator() }
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val e = lazyFactFlow.loadState.refresh as LoadState.Error
                        coroutineScope.launch {
                            when(scaffoldState.snackbarHostState.showSnackbar(e.error.localizedMessage ?: "Unknown Error", "Retry")){
                                SnackbarResult.Dismissed -> {}
                                SnackbarResult.ActionPerformed -> retry()
                            }
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val e = lazyFactFlow.loadState.append as LoadState.Error
                        coroutineScope.launch {
                            when(scaffoldState.snackbarHostState.showSnackbar(e.error.localizedMessage ?: "Unknown Error", "Retry")){
                                SnackbarResult.Dismissed -> {}
                                SnackbarResult.ActionPerformed -> retry()
                            }
                        }
                    }
                }
            }
        }
    }
}