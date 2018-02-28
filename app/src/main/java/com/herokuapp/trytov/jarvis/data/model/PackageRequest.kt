package com.herokuapp.trytov.jarvis.data.model

import com.google.gson.annotations.SerializedName

class PackageRequest(
        @SerializedName("textVoice")
        var textVoice: String
)