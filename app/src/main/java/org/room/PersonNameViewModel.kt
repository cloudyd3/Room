package org.room

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class PersonNameViewModel(private val repository: PersonNameRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allPersonNames: LiveData<List<PersonName>> = repository.allPersonNames.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(personName: PersonName) = viewModelScope.launch {
        repository.insert(personName)
    }
}

class PersonNameViewModelFactory(private val repository: PersonNameRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonNameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonNameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
