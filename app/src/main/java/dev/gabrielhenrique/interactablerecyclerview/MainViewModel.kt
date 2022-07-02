package dev.gabrielhenrique.interactablerecyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val originItemsList = mutableListOf<Item>()
    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    var filtering: Boolean = false

    init {
        originItemsList.addAll((0..100).map {
            Item(id = it.toString(), title = "Title $it")
        })
        _items.postValue(originItemsList)
    }

    fun itemClicked(item: Item) {
        _items.postValue(
            _items.value?.map {
                if(it.id == item.id) {
                    it.copy(checked = it.checked.toggle())
                } else it
            }
        )
    }

    fun filterClicked() {
        if(filtering) {
            removeFilters()
        } else {
            filterSelectedItems()
        }
        filtering = filtering.toggle()
    }

    private fun Boolean.toggle() = !this

    private fun filterSelectedItems() {
        _items.postValue(
            _items.value?.filter { it.checked }
        )
    }

    private fun removeFilters() {
        val list = _items.value.orEmpty()
        _items.postValue(originItemsList.map { originalItem ->
            if(list.find { it.id == originalItem.id }?.checked == true)
                originalItem.copy(checked = true)
            else originalItem
        })
    }
}
