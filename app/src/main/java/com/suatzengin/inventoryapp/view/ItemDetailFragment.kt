package com.suatzengin.inventoryapp.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.suatzengin.inventoryapp.R
import com.suatzengin.inventoryapp.databinding.FragmentItemDetailBinding
import com.suatzengin.inventoryapp.model.Item
import com.suatzengin.inventoryapp.viewmodel.InventoryViewModel


class ItemDetailFragment : Fragment() {

    val args: ItemDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentItemDetailBinding
    private lateinit var viewModel: InventoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[InventoryViewModel::class.java]

        viewModel.getItem(args.item.id).observe(viewLifecycleOwner, Observer { item ->
            sellItem(item)
        })
        return binding.root
    }

    private fun sellItem(item: Item){
        binding.apply {
            tvItemName.text = item.itemName
            tvItemPrice.text = item.itemPrice.toString()
            tvItemCount.text = item.itemCount.toString()
            btnSell.setOnClickListener {
                viewModel.sellItem(item)
            }
        }
    }

    private fun updateItem(){
        val action = ItemDetailFragmentDirections
            .toUpdate(args.item)
        findNavController().navigate(action)
    }
    private fun deleteItem(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.alert_message_for_delete)
            .setTitle("Delete")
            .setPositiveButton("Yes", DialogInterface.OnClickListener{ _, _ ->
                viewModel.delete(args.item)
                findNavController().navigate(R.id.action_itemDetailFragment_to_itemListFragment)
            })
            .setNegativeButton("No", null)
        builder.create().show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.updateItem -> {
                updateItem()
            } // update
            R.id.deleteItem -> {
                deleteItem()
            } // delete
        }
        return super.onOptionsItemSelected(item)
    }

}