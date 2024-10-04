package com.lopez.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {
    private var tvName: TextView? = null
    private var tvCourse: TextView? = null
    private var tvContactNumber: TextView? = null
    private var tvAge: TextView? = null
    private var tvEmail: TextView? = null
    private var tvGender: TextView? = null
    private var tvTalent: TextView? = null

    private var sharedPreferences: SharedPreferences? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize SharedPreferences
        sharedPreferences =
            requireContext().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)

        // Initialize TextViews
        tvName = view.findViewById<TextView>(R.id.tv_name)
        tvCourse = view.findViewById<TextView>(R.id.tv_course)
        tvContactNumber = view.findViewById<TextView>(R.id.tv_contactNumber)
        tvAge = view.findViewById<TextView>(R.id.tv_age)
        tvEmail = view.findViewById<TextView>(R.id.tv_email)
        tvGender = view.findViewById<TextView>(R.id.tv_gender)
        tvTalent = view.findViewById<TextView>(R.id.tv_talent)

        // Load saved data from SharedPreferences
        loadProfileData()

        // Find the button and set an OnClickListener
        val btnEditProfile = view.findViewById<Button>(R.id.btn_editProfile)
        btnEditProfile.setOnClickListener { v: View? -> showEditProfileDialog() }

        return view
    }

    private fun loadProfileData() {
        // Load data from SharedPreferences
        val name = sharedPreferences!!.getString("name", "")
        val course = sharedPreferences!!.getString("course", "")
        val contactNumber = sharedPreferences!!.getString("contactNumber", "")
        val age = sharedPreferences!!.getString("age", "")
        val email = sharedPreferences!!.getString("email", "")
        val gender = sharedPreferences!!.getString("gender", "")
        val talent = sharedPreferences!!.getString("talent", "")

        // Set the TextViews with the saved data
        tvName!!.text = name
        tvCourse!!.text = course
        tvContactNumber!!.text = contactNumber
        tvAge!!.text = age
        tvEmail!!.text = email
        tvGender!!.text = gender
        tvTalent!!.text = talent
    }

    private fun showEditProfileDialog() {
        // Inflate the custom layout for the dialog
        val dialogView: View =
            LayoutInflater.from(context).inflate(R.layout.dialog_edit_profile, null)

        val editTextUsername = dialogView.findViewById<EditText>(R.id.editTextUsername)
        val editTextCourse = dialogView.findViewById<EditText>(R.id.editTextCourse)
        val editTextContactNumber = dialogView.findViewById<EditText>(R.id.editTextContactNumber)
        val editTextAge = dialogView.findViewById<EditText>(R.id.editTextAge)
        val editTextEmail = dialogView.findViewById<EditText>(R.id.editTextEmail)
        val radioGroupGender = dialogView.findViewById<RadioGroup>(R.id.radioGroupGender)
        val checkBoxSinging = dialogView.findViewById<CheckBox>(R.id.checkBoxSinging)
        val checkBoxDancing = dialogView.findViewById<CheckBox>(R.id.checkBoxDancing)
        val checkBoxDrawing = dialogView.findViewById<CheckBox>(R.id.checkBoxDrawing)
        val checkBoxWriting = dialogView.findViewById<CheckBox>(R.id.checkBoxWriting)

        // Load existing data into dialog fields
        editTextUsername.setText(sharedPreferences!!.getString("name", ""))
        editTextCourse.setText(sharedPreferences!!.getString("course", ""))
        editTextContactNumber.setText(sharedPreferences!!.getString("contactNumber", ""))
        editTextAge.setText(sharedPreferences!!.getString("age", ""))
        editTextEmail.setText(sharedPreferences!!.getString("email", ""))

        val savedGender = sharedPreferences!!.getString("gender", "")
        if (savedGender == "Male") {
            radioGroupGender.check(R.id.radioMale)
        } else if (savedGender == "Female") {
            radioGroupGender.check(R.id.radioFemale)
        }

        val savedTalent = sharedPreferences!!.getString("talent", "")
        checkBoxSinging.isChecked = savedTalent!!.contains("Bini")
        checkBoxDancing.isChecked = savedTalent.contains("Twice")
        checkBoxDrawing.isChecked = savedTalent.contains("Blackpink")
        checkBoxWriting.isChecked = savedTalent.contains("BTS")

        // Create and show the AlertDialog
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(dialogView)
            .setCancelable(false)

        val dialog = dialogBuilder.create()

        // Handle the Save button click
        dialogView.findViewById<View>(R.id.btn_save).setOnClickListener { v: View? ->
            // Get data from input fields
            val newName = editTextUsername.text.toString().trim { it <= ' ' }
            val newCourse = editTextCourse.text.toString().trim { it <= ' ' }
            val newContactNumber =
                editTextContactNumber.text.toString().trim { it <= ' ' }
            val newAge = editTextAge.text.toString().trim { it <= ' ' }
            val newEmail = editTextEmail.text.toString().trim { it <= ' ' }

            // Get gender selection
            val selectedGenderId = radioGroupGender.checkedRadioButtonId
            var newGender = ""
            if (selectedGenderId != -1) {
                val selectedGender =
                    dialogView.findViewById<RadioButton>(selectedGenderId)
                newGender = selectedGender.text.toString()
            }

            // Get talent selection
            val newTalentList: MutableList<String?> =
                ArrayList()
            if (checkBoxSinging.isChecked) newTalentList.add("Bini")
            if (checkBoxDancing.isChecked) newTalentList.add("Twice")
            if (checkBoxDrawing.isChecked) newTalentList.add("Blackpink")
            if (checkBoxWriting.isChecked) newTalentList.add("BTS")

            val newTalent = TextUtils.join(", ", newTalentList)

            // Validate that no field is empty
            if (newName.isEmpty() || newCourse.isEmpty() || newContactNumber.isEmpty() ||
                newAge.isEmpty() || newEmail.isEmpty() || newGender.isEmpty() || newTalent.isEmpty()
            ) {
                // Show an error message if any field is empty
                Toast.makeText(context, "Please input all the fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Save data to SharedPreferences
                val editor = sharedPreferences!!.edit()
                editor.putString("name", newName)
                editor.putString("course", newCourse)
                editor.putString("contactNumber", newContactNumber)
                editor.putString("age", newAge)
                editor.putString("email", newEmail)
                editor.putString("gender", newGender)
                editor.putString("talent", newTalent)
                editor.apply()

                // Update TextViews in FragmentProfile with the input data
                tvName!!.text = newName
                tvCourse!!.text = newCourse
                tvContactNumber!!.text = newContactNumber
                tvAge!!.text = newAge
                tvEmail!!.text = newEmail
                tvGender!!.text = newGender
                tvTalent!!.text = newTalent

                dialog.dismiss() // Close the dialog
            }
        }

        // Handle the Cancel button click
        dialogView.findViewById<View>(R.id.btn_cancel)
            .setOnClickListener { v: View? -> dialog.dismiss() }

        dialog.show()
    }
}