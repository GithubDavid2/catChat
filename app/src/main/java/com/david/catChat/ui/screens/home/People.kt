package com.david.catChat.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.david.catChat.R
import com.david.catChat.data.RequestState
import com.david.catChat.database.FirestoreService
import com.david.catChat.ui.components.LoadingList
import com.david.catChat.ui.components.PeopleCard
import com.david.catChat.ui.components.Screen
import com.david.catChat.viewmodels.UserViewModel
import com.david.catChat.viewmodels.UserViewModelFactory


@Composable
fun PeopleScreen(
    onSelectPeople: (String?, String) -> Unit,
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(db = FirestoreService))
) {

    Screen(arrangement = Arrangement.Top, modifier = modifier) {
        LoadingList(
            loading = userViewModel.requestState == RequestState.LOADING,
            data = userViewModel.users,
            emptyPlaceholder = R.string.empty_people
        ) {
            PeopleCard(name = it.name, onClick = { onSelectPeople(null, it.id) })
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PeopleScreenPreview() {
    PeopleScreen(onSelectPeople = {_, _ ->})
}