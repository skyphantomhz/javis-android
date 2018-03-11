package com.herokuapp.trytov.jarvis.data.model

import com.google.gson.annotations.SerializedName

class PackageRequest(
        @SerializedName("token")
        var token: String,

        @SerializedName("textVoice")
        var textVoice: String
)