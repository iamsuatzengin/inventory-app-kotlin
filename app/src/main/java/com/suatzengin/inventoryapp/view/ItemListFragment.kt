package com.suatzengin.inventoryapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.suatzengin.inventoryapp.R
import com.suatzengin.inventoryapp.adapter.RvAdapter
import com.suatzengin.inventoryapp.databinding.FragmentItemListBinding
import com.suatzengin.inventoryapp.model.Item
import com.suatzengin.inventoryapp.viewmodel.InventoryViewModel


class ItemListFragment : Fragment() {

    private lateinit var binding: FragmentItemListBinding
    private lateinit var adapter: RvAdapter
    private lateinit var viewModel: InventoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[InventoryViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            val action = ItemListFragmentDirections.toAddFragment(
                Item(0, "", 0.0, 0)
            )
            view.findNavController().navigate(action)
        }
        adapter = RvAdapter {
            val action = ItemListFragmentDirections.toDetail(it)
            view.findNavController().navigate(action)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allItem.observe(viewLifecycleOwner, Observer { list ->
            adapter.itemList = list
        })

    }



}