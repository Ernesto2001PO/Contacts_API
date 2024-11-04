package com.example.contacts_api.api

import com.example.contacts_api.models.Email
import com.example.contacts_api.models.Persona
import com.example.contacts_api.models.Personas
import com.example.contacts_api.models.Phone
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface JSONPlaceHolderService {

    @GET("api/personas")
    fun getPersonas(): Call<Personas>

    @DELETE("/api/personas/{id}")
    fun deletePersona(@Path("id") id: Int): Call<Void>

    @PUT("/api/personas/{id}")
    fun updatePersona(
        @Path("id") id: Int,
        @Body persona: Persona
    ): Call<Persona>

    @GET("/api/search")
    fun searchPersonas(@Query("q") query: String): Call<List<Persona>>


    @POST("/api/personas")
    fun createPersona(@Body persona: Persona): Call<Persona>

    @POST("/api/phones")
    fun createPhone(@Body phone: Phone): Call<Phone>

    @POST("/api/emails")
    fun createEmail(@Body email: Email): Call<Email>


}

