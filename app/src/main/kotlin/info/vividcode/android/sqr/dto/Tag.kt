package info.vividcode.android.sqr.dto

import com.google.gson.annotations.SerializedName

data class Tag(
        @field:SerializedName("name")
        var name: String,

        @field:SerializedName("versions")
        var versions: List<String>
)
