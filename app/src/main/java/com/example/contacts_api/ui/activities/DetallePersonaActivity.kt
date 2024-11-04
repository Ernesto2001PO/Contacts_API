package com.example.contacts_api.ui.activities

import PersonasListasViewModel
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.contacts_api.databinding.DetallePersonaBinding
import com.example.contacts_api.models.Email
import com.example.contacts_api.models.Persona
import com.example.contacts_api.models.Phone
import com.example.contacts_api.ui.adapters.EmailAdapter
import com.example.contacts_api.ui.adapters.PhoneAdapter
import com.example.contacts_api.ui.interfaces.OnPersonaClickListener

class DetallePersonaActivity : AppCompatActivity(), OnPersonaClickListener {

    private lateinit var binding: DetallePersonaBinding
    private val viewModel: PersonasListasViewModel by viewModels()
    private lateinit var phoneAdapter: PhoneAdapter
    private lateinit var emailAdapter: EmailAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetallePersonaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)
        val name = intent.getStringExtra("name") ?: ""
        val lastName = intent.getStringExtra("last_name") ?: ""
        val address = intent.getStringExtra("address") ?: ""
        val city = intent.getStringExtra("city") ?: ""
        val state = intent.getStringExtra("state") ?: ""
        val company = intent.getStringExtra("company") ?: ""
        val profilePicture = intent.getStringExtra("profile_picture")
        val phones = intent.getParcelableArrayListExtra<Phone>("phones") ?: arrayListOf()
        val emails = intent.getParcelableArrayListExtra<Email>("emails") ?: arrayListOf()



        Log.d("DetallePersonaActivity", "ID: $id")
        binding.etName.setText(name)
        binding.etLastName.setText(lastName)
        binding.etAddress.setText(address)
        binding.etCity.setText(city)
        binding.etState.setText(state)
        binding.etCompany.setText(company)

        // Configura el RecyclerView para teléfonos
        phoneAdapter = PhoneAdapter(phones, this)
        binding.rvPhones.layoutManager = LinearLayoutManager(this)
        binding.rvPhones.adapter = phoneAdapter

        // Configura el RecyclerView para emails
        emailAdapter = EmailAdapter(emails, this)
        binding.rvEmails.layoutManager = LinearLayoutManager(this)
        binding.rvEmails.adapter = emailAdapter


        profilePicture?.let { url ->
            Glide.with(this).load(url).into(binding.imgProfilePicture)
        }

        binding.btnEdit.setOnClickListener {
            val id = intent.getIntExtra("id", 0)
            val updatedPersona = Persona(
                id = id,
                name = binding.etName.text.toString(),
                last_name = binding.etLastName.text.toString(),
                address = binding.etAddress.text.toString(),
                city = binding.etCity.text.toString(),
                state = binding.etState.text.toString(),
                company = binding.etCompany.text.toString(),
                profile_picture = null,
                phones = phoneAdapter.phones,
                emails = emailAdapter.emails
            )

            editPersona(id, updatedPersona)
        }

        binding.btnDelete.setOnClickListener {
            val personaId = intent.getIntExtra("id", 0)
            deletebyId(personaId)
        }
    }

    private fun editPersona(id: Int, persona: Persona) {
        viewModel.editPersonaById(persona.id, persona)

        viewModel.editStatus.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Persona actualizada exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar persona", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun deletebyId(id: Int) {
        viewModel.deletePersonaById(id)

        viewModel.deleteStatus.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Persona eliminada exitosamente", Toast.LENGTH_SHORT).show()
                finish() // Finaliza la actividad actual después de la eliminación
            } else {
                Toast.makeText(this, "Error al eliminar persona", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPersonaClick(persona: Persona) {

    }

    override fun OnPhoneClick(phone: Phone) {
        Toast.makeText(this, "Phone Clicked: ${phone.number}", Toast.LENGTH_SHORT).show()

    }

    override fun OnEmailClick(email: Email) {
        Toast.makeText(this, "Email Clicked: ${email.email}", Toast.LENGTH_SHORT).show()

    }

}