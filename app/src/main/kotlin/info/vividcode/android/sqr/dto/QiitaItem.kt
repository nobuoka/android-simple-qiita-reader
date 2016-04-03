package info.vividcode.android.sqr.dto

import com.google.gson.annotations.SerializedName

data class QiitaItem(
        @SerializedName("id")
        var id: String,

        @SerializedName("title")
        var title: String,

        @SerializedName("body")
        var body: String,

        @SerializedName("rendered_body")
        var renderedBody: String,

        @SerializedName("coediting")
        var coediting: Boolean,

        @SerializedName("created_at")
        var createdAt: String,

        @SerializedName("updated_at")
        var updatedAt: String,

        @SerializedName("private")
        var isPrivate: Boolean,

        @SerializedName("url")
        var url: String,

        @SerializedName("tags")
        var tags: List<Tag>,

        @SerializedName("user")
        var user: User
)
