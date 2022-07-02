package dev.gabrielhenrique.interactablerecyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dev.gabrielhenrique.interactablerecyclerview.databinding.FragmentMainBinding

class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val mainViewModel: MainViewModel by viewModels()
    private val adapter = ItemsAdapter(::itemClicked)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myList.layoutManager = LinearLayoutManager(context)
        binding.myList.adapter = adapter

        binding.filterBtn.setOnClickListener { mainViewModel.filterClicked() }

        mainViewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun itemClicked(item: Item) {
        mainViewModel.itemClicked(item)
    }
}