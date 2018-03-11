package com.herokuapp.trytov.jarvis.data.model

import com.google.gson.annotations.SerializedName

class AuthenticateResponse(
    @SerializedName("token")
    var token: String
)