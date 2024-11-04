package com.example.contacts_api.repositories

import android.util.Log
import com.example.contacts_api.api.JSONPlaceHolderService
import com.example.contacts_api.models.Email
import com.example.contacts_api.models.Persona
import com.example.contacts_api.models.Personas
import com.example.contacts_api.models.Phone

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




object PersonasRepository {

    private val retrofit = RetrofitRepository.getRetrofitInstance()
    private val service = retrofit.create(JSONPlaceHolderService::class.java)



    fun getPersonasList(
        onSuccess: (Personas) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        service.getPersonas().enqueue(object : Callback<Personas> {
            override fun onResponse(call: Call<Personas>, response: Response<Personas>) {
                if (response.isSuccessful) {
                    val personas = response.body()
                    Log.d("PersonasRepository", "Datos recibidos: ${personas?.size}")
                    onSuccess(personas!!)
                }
                else{
                    Log.e("PersonasRepository", "Error: ${response.errorBody()}")
                    onError(Throwable("Error en la respuesta del servidor"))
                }
            }
            override fun onFailure(call: Call<Personas>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })

    }
    fun deletePersona(
        id: Int,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        service.deletePersona(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError(Throwable("Error en la respuesta al eliminar Persona"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onError(t)
            }
        })
    }


    fun searchPersonas(
        query: String,
        onSuccess: (List<Persona>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        service.searchPersonas(query).enqueue(object : Callback<List<Persona>> {
            override fun onResponse(call: Call<List<Persona>>, response: Response<List<Persona>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it)
                    } ?: onError(Throwable("Error: Respuesta vacía"))
                } else {
                    onError(Throwable("Error: Respuesta no exitosa"))
                }
            }
            override fun onFailure(call: Call<List<Persona>>, t: Throwable) {
                onError(t)
            }
        })
    }



    fun updatePersona(
        id: Int,
        persona: Persona,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        service.updatePersona(id, persona).enqueue(object : Callback<Persona> {
            override fun onResponse(call: Call<Persona>, response: Response<Persona>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    Log.e("UpdatePersona", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                    onError(Throwable("Error en la respuesta al actualizar la persona"))
                }
            }

            override fun onFailure(call: Call<Persona>, t: Throwable) {
                Log.e("UpdatePersona", "Error en la llamada: ${t.message}")
                onError(t)
            }
        })
    }


    // Crear Persona
    fun createPersona(
        persona: Persona,
        onSuccess: (Persona) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        service.createPersona(persona).enqueue(object : Callback<Persona> {
            override fun onResponse(call: Call<Persona>, response: Response<Persona>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(Throwable("Error en la respuesta al crear Persona"))
                }
            }

            override fun onFailure(call: Call<Persona>, t: Throwable) {
                onError(t)
            }
        })
    }


    fun createPhone(
        phone: Phone,
        onSuccess: (Phone) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        service.createPhone(phone).enqueue(object : Callback<Phone> {
            override fun onResponse(call: Call<Phone>, response: Response<Phone>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(Throwable("Error en la respuesta al crear Phone"))
                }
            }

            override fun onFailure(call: Call<Phone>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun createEmail(
        email: Email,
        onSuccess: (Email) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        service.createEmail(email).enqueue(object : Callback<Email> {
            override fun onResponse(call: Call<Email>, response: Response<Email>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(Throwable("Error en la respuesta al crear Email"))
                }
            }

            override fun onFailure(call: Call<Email>, t: Throwable) {
                onError(t)
            }
        })
    }













}