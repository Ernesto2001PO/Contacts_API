package com.example.contacts_api.ui.activities

import PersonasListasViewModel
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.contacts_api.R
import com.example.contacts_api.databinding.AgregarContactoBinding
import com.example.contacts_api.models.Email
import com.example.contacts_api.models.Persona
import com.example.contacts_api.models.Phone

class AgregarContactoActivity : AppCompatActivity() {

    private lateinit var binding: AgregarContactoBinding
    private val viewModel: PersonasListasViewModel by viewModels()

    // Listas para almacenar los teléfonos y correos electrónicos
    private val phoneList = mutableListOf<Phone>()
    private val emailList = mutableListOf<Email>()


    private var selectedBitmap: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AgregarContactoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddPhone.setOnClickListener {
            addPhoneField()
        }

        binding.btnAddEmail.setOnClickListener {
            addEmailField()
        }

        binding.btnSelectedPicture.setOnClickListener {
            selectPicture()

        }

        binding.btnAceptar.setOnClickListener {
            submitPerson()
        }


    }

    private fun submitPerson() {
        val name = binding.etName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val address = binding.etAddress.text.toString()
        val city = binding.etCity.text.toString()
        val state = binding.etState.text.toString()
        val company = binding.etCompany.text.toString()

        phoneList.clear()
        for (i in 0 until binding.containerPhones.childCount) {
            val phoneLayout = binding.containerPhones.getChildAt(i) as? LinearLayout
            if (phoneLayout != null) {
                val phoneEditText = phoneLayout.findViewById<EditText>(R.id.editTextValue)
                val phoneSpinner = phoneLayout.findViewById<Spinner>(R.id.spinnerLabel)

                val number = phoneEditText?.text.toString()
                val label = phoneSpinner?.selectedItem.toString()
                if (number.isNotBlank()) {
                    phoneList.add(Phone(id = 0, number = number, persona_id = 0, label = label))
                }
                Log.d("SubmitPerson", "Teléfono recolectado: número=$number, etiqueta=$label")
            }
        }

        emailList.clear()
        for (i in 0 until binding.containerEmails.childCount) {
            val emailLayout = binding.containerEmails.getChildAt(i) as? LinearLayout
            if (emailLayout != null) {
                val emailEditText = emailLayout.findViewById<EditText>(R.id.editTextValue)
                val emailSpinner = emailLayout.findViewById<Spinner>(R.id.spinnerLabel)

                val emailAddress = emailEditText?.text.toString()
                val label = emailSpinner?.selectedItem.toString()
                if (emailAddress.isNotBlank()) {
                    emailList.add(
                        Email(
                            id = 0,
                            email = emailAddress,
                            persona_id = 0,
                            label = label
                        )
                    )
                }
                Log.d("SubmitPerson", "Email recolectado: email=$emailAddress, etiqueta=$label")
            }
        }

        val persona = Persona(
            id = 0,
            name = name,
            last_name = lastName,
            address = address,
            city = city,
            state = state,
            company = company,
            profile_picture = null,
            phones = phoneList,
            emails = emailList
        )

        Log.d("SubmitPerson", "Datos de persona a enviar: $persona")


        viewModel.addPersona(persona)
    }


    private fun addPhoneField() {
        val phoneLayout = layoutInflater.inflate(
            R.layout.item_phone_email,
            binding.containerPhones,
            false
        ) as LinearLayout
        binding.containerPhones.addView(phoneLayout)

        val phoneSpinner = phoneLayout.findViewById<Spinner>(R.id.spinnerLabel)
        ArrayAdapter.createFromResource(
            this,
            R.array.phone_labels,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            phoneSpinner.adapter = adapter
        }
    }

    private fun addEmailField() {
        val emailLayout = layoutInflater.inflate(
            R.layout.item_phone_email,
            binding.containerEmails,
            false
        ) as LinearLayout
        binding.containerEmails.addView(emailLayout)

        val emailSpinner = emailLayout.findViewById<Spinner>(R.id.spinnerLabel)
        ArrayAdapter.createFromResource(
            this,
            R.array.email_labels,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            emailSpinner.adapter = adapter
        }
    }



    private val fileChooserResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                selectedBitmap =
                    MediaStore.Images.Media.getBitmap(this.contentResolver, data?.data)
                binding.ImgPictureSelected.setImageBitmap(selectedBitmap)
            }
        }



    private fun selectPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        fileChooserResultLauncher.launch(intent)
    }
}

