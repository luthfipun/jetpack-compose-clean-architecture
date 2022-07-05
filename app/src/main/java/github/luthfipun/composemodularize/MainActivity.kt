package github.luthfipun.composemodularize

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import dagger.hilt.android.AndroidEntryPoint
import github.luthfipun.composemodularize.ui.theme.ComposeModularizeTheme
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeModularizeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Tests()
                }
            }
        }
    }
}

@Composable
fun Tests(mainViewModel: MainViewModel = viewModel()){

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val lazyFactItems = mainViewModel.facts.collectAsLazyPagingItems()

    Scaffold(scaffoldState = scaffoldState) {
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(lazyFactItems){ item ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    text = item?.fact ?: "Unknown",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            lazyFactItems.apply {
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
                        item {
                            Column(
                                modifier = Modifier.fillParentMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Couldn't connect to the Internet!")
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(onClick = { retry() }) {
                                    Text(text = "Retry")
                                }
                            }
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val e = lazyFactItems.loadState.append as LoadState.Error
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