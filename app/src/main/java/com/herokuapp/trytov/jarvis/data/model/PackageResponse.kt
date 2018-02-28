package com.herokuapp.trytov.jarvis.data.model

import com.google.gson.annotations.SerializedName

class PackageResponse(
        @SerializedName("textRespone")
        var textResponse: String
)