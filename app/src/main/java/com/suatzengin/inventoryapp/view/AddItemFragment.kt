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
import androidx.navigation.fragment.navArgs
import com.suatzengin.inventoryapp.R
import com.suatzengin.inventoryapp.databinding.FragmentAddItemBinding
import com.suatzengin.inventoryapp.model.Item
import com.suatzengin.inventoryapp.viewmodel.InventoryViewModel

class AddItemFragment : Fragment() {

    private lateinit var binding: FragmentAddItemBinding
    private lateinit var viewModel: InventoryViewModel
    val args: AddItemFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddItemBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[InventoryViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.updatedItem.id
        if (id > 0) {
            viewModel.getItem(id)
                .observe(viewLifecycleOwner, Observer { selectedItem ->
                    val item: Item = selectedItem
                    bind(item)

                })
        } else {
            binding.btnSave.setOnClickListener {
                addItem()

            }
        }
    }

    private fun bind(item: Item) {
        binding.apply {
            itemName.setText(item.itemName)
            itemPrice.setText(item.itemPrice.toString())
            itemCount.setText(item.itemCount.toString())
            btnSave.setOnClickListener { updateItem(item.id) }
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.itemName.toString(),
            binding.itemPrice.toString(),
            binding.itemCount.toString()
        )
    }

    private fun addItem() {
        if (isEntryValid()) {
            viewModel.addNewItem(
                binding.itemName.text.toString(),
                binding.itemPrice.text.toString().toDouble(),
                binding.itemCount.text.toString().toInt()
            )
            findNavController().navigate(R.id.action_addItemFragment_to_itemListFragment)
        }
    }

    private fun updateItem(id: Int) {
        if (isEntryValid()) {
            val updatedItem = Item(
                id,
                this.binding.itemName.text.toString(),
                binding.itemPrice.text.toString().toDouble(),
                binding.itemCount.text.toString().toInt()
            )
            viewModel.update(updatedItem)
            findNavController().navigate(R.id.action_addItemFragment_to_itemListFragment)
        }
    }
}