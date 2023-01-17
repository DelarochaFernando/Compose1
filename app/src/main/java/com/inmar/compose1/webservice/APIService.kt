package com.inmar.compose1.webservice

import com.inmar.compose1.data.Book
import com.inmar.compose1.data.GetBoolListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface APIService {
    @GET
    fun getCharacterbyName(@Url url : String) : Call<DogResponse>

    @Headers("Accept:Application/json")
    @GET("assets/test.json")
    fun fetchBookList() :Call<GetBoolListResponse>?
}