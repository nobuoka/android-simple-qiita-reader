package info.vividcode.android.sqr.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.ArrayList

data class Tag(
        @field:SerializedName("name")
        var name: String,

        @field:SerializedName("versions")
        var versions: ArrayList<String>
) : Serializable
