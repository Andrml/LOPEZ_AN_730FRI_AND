package com.lopez.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.util.ArrayList

class List_Fragment : Fragment(), ListAdapter.OnItemActionListener {

    companion object {
        private const val PICK_IMAGE = 100
        private const val DOUBLE_CLICK_DELAY = 300L
    }

    private lateinit var items: ArrayList<ItemList>
    private lateinit var adapter: ListAdapter
    private lateinit var listView: ListView
    private lateinit var editText: EditText
    private lateinit var buttonAdd: Button
    private var lastClickTime: Long = 0
    private var selectedPosition: Int = -1
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_, container, false)

        listView = view.findViewById(R.id.listView)
        editText = view.findViewById(R.id.editText)
        buttonAdd = view.findViewById(R.id.buttonAdd)

        items = ArrayList()
        adapter = ListAdapter(requireContext(), items)
        adapter.setOnItemActionListener(this)  // Set the listener here
        listView.adapter = adapter

        buttonAdd.setOnClickListener {
            val text = editText.text.toString()
            if (text.isNotEmpty()) {
                val imageUri = selectedImageUri ?: Uri.parse("android.resource://com.lopez.myapplication/" + R.drawable.img)
                items.add(ItemList(false, text, imageUri))
                adapter.notifyDataSetChanged()
                editText.setText("")
                selectedImageUri = null
            }
        }

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime < DOUBLE_CLICK_DELAY) {
                showEditDeleteDialog(position)
            }
            lastClickTime = currentTime
        }

        return view
    }

    override fun onItemDoubleClicked(position: Int) {
        showEditDeleteDialog(position)
    }

    private fun showEditDeleteDialog(position: Int) {
        val item = items[position]
        selectedPosition = position

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Edit or Delete")

        builder.setPositiveButton("Edit") { _, _ ->
            editText.setText(item.text)
            selectedImageUri = item.imageUri
            items.removeAt(position)
            adapter.notifyDataSetChanged()
        }

        builder.setNeutralButton("Change Image") { _, _ -> openGallery() }

        builder.setNegativeButton("Delete") { _, _ ->
            items.removeAt(position)
            adapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Item Deleted", Toast.LENGTH_SHORT).show()
        }

        builder.show()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            if (selectedImageUri != null && selectedPosition != -1) {
                items[selectedPosition].imageUri = selectedImageUri
                adapter.notifyDataSetChanged()
            }
        }
    }
}
