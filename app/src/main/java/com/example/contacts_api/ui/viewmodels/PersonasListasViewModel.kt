import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contacts_api.models.Email
import com.example.contacts_api.models.Persona
import com.example.contacts_api.models.Personas
import com.example.contacts_api.models.Phone
import com.example.contacts_api.repositories.PersonasRepository

class PersonasListasViewModel : ViewModel() {

    private val _personasList = MutableLiveData<Personas>().apply {
        value = arrayListOf()
    }
    val personasList: LiveData<Personas> = _personasList


    private val _deleteStatus = MutableLiveData<Boolean>()
    val  deleteStatus: LiveData<Boolean> get() = _deleteStatus

    private val _editStatus = MutableLiveData<Boolean>()
    val editStatus: LiveData<Boolean> get() = _editStatus

    private val _searchResults = MutableLiveData<List<Persona>>()
    val searchResults: LiveData<List<Persona>> = _searchResults

    fun getPersonasList() {
        PersonasRepository.getPersonasList(
            onSuccess = { _personasList.value = it },
            onError = { it.printStackTrace() }
        )
    }

    fun addPersona(persona: Persona) {
        PersonasRepository.createPersona(
            persona,
            onSuccess = { createdPersona ->
                val personaId = createdPersona.id

                // Actualiza el `persona_id` en cada `Phone` y `Email`
                persona.phones.forEach { it.persona_id = personaId }
                persona.emails.forEach { it.persona_id = personaId }

                // Enviar teléfonos y emails
                submitPhonesAndEmails(persona.phones, persona.emails)

            },
            onError = { error ->
                Log.e("SubmitPerson", "Error al crear persona", error)
            }
        )
    }

    fun deletePersonaById(id: Int) {
        PersonasRepository.deletePersona(
            id,
            onSuccess = {
                _deleteStatus.postValue(true)
            },
            onError = {
                _deleteStatus.postValue(false)
            }
        )
    }

    fun editPersonaById(id: Int, persona: Persona) {
        PersonasRepository.updatePersona(
            id,
            persona,
            onSuccess = {
                Log.d("EditPerson", "Persona actualizada correctamente")
                _editStatus.postValue(true)
            },
            onError = { error ->
                Log.e("EditPerson", "Error al actualizar persona", error)
                _editStatus.postValue(false)
            }
        )
    }

    fun searchPersonas(query: String) {
        PersonasRepository.searchPersonas(
            query = query,
            onSuccess = { personas ->
                _searchResults.value = personas
            },
            onError = { error ->
                error.printStackTrace()
                _searchResults.value = emptyList() // Manejo de error: devuelve una lista vacía
            }
        )
    }



    private fun submitPhonesAndEmails(phones: List<Phone>, emails: List<Email>) {
        phones.forEach { phone ->
            PersonasRepository.createPhone(
                phone,
                onSuccess = { Log.d("SubmitPerson", "Teléfono agregado: $it") },
                onError = { Log.e("SubmitPerson", "Error al agregar teléfono", it) }
            )
        }

        emails.forEach { email ->
            PersonasRepository.createEmail(
                email,
                onSuccess = { Log.d("SubmitPerson", "Email agregado: $it") },
                onError = { Log.e("SubmitPerson", "Error al agregar email", it) }
            )
        }
    }


}
