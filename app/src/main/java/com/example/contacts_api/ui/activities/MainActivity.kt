package com.example.contacts_api.ui.activities

import PersonasListasViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts_api.databinding.MainBinding
import com.example.contacts_api.models.Email
import com.example.contacts_api.models.Persona
import com.example.contacts_api.models.Phone
import com.example.contacts_api.ui.interfaces.OnPersonaClickListener
import com.example.contacts_api.ui.adapters.PersonaListAdapter

class MainActivity : AppCompatActivity(), OnPersonaClickListener {

    private lateinit var binding: MainBinding
    private val viewModel: PersonasListasViewModel by viewModels()
    private lateinit var adapter: PersonaListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración de Edge-to-Edge para ajuste de sistema
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("MainActivity", "onCreate: La actividad ha iniciado correctamente")

        setupRecyclerView()
        setupViewModelObservers()
        viewModel.getPersonasList()


        // Observa los resultados de la búsqueda
        viewModel.searchResults.observe(this) { personas ->
            adapter.updateData(personas)
        }

        // Mostrar el botón de retroceso en la barra de acción
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.agregarContacto.setOnClickListener {
            val intent = Intent(this, AgregarContactoActivity::class.java)
            startActivity(intent)
        }

        binding.searchEditText.addTextChangedListener { text ->
            val query = text.toString()
            if (query.isNotBlank()) {
                viewModel.searchPersonas(query)
            }
        }


    }

    override fun onResume() {
        super.onResume()
        viewModel.getPersonasList()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onPersonaClick(persona: Persona) {
        Toast.makeText(this, "Clicked: ${persona.name}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetallePersonaActivity::class.java)
        intent.putExtra("id", persona.id)
        intent.putExtra("name", persona.name)
        intent.putExtra("last_name", persona.last_name)
        intent.putExtra("address", persona.address)
        intent.putExtra("city", persona.city)
        intent.putExtra("state", persona.state)
        intent.putExtra("company", persona.company)
        intent.putExtra("profile_picture", persona.profile_picture)
        intent.putParcelableArrayListExtra("phones", ArrayList(persona.phones))
        intent.putParcelableArrayListExtra("emails", ArrayList(persona.emails))

        startActivity(intent)

    }


    override fun OnPhoneClick(phone: Phone) {
    }

    override fun OnEmailClick(email: Email) {
    }

    private fun setupRecyclerView() {
        adapter = PersonaListAdapter(emptyList(), this)
        binding.recyclerViewPersonas.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPersonas.adapter = adapter
    }

    private fun setupViewModelObservers() {
        viewModel.personasList.observe(this) { personas ->
            Log.d("MainActivity", "Número de personas recibidas: ${personas.size}")
            adapter = PersonaListAdapter(personas, this)
            binding.recyclerViewPersonas.adapter = adapter
        }
    }


}
