package com.herokuapp.trytov.jarvis.data.model

import com.google.gson.annotations.SerializedName

class Profile (
        @SerializedName("id")
        var id: Long,

        @SerializedName("email")
        var email: String
)