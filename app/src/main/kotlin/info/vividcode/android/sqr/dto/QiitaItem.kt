package info.vividcode.android.sqr.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.ArrayList

data class QiitaItem(
        @SerializedName("id")
        var id: String,

        @SerializedName("title")
        var title: String,

        @SerializedName("body")
        var body: String,

        /** HTML でマークアップされた文字列。 */
        @SerializedName("rendered_body")
        var renderedBody: String,

        @SerializedName("coediting")
        var coediting: Boolean,

        /**
         * 『2000-01-01T00:00:00+00:00』 のような時刻を表す文字列。
         * TODO : [com.google.gson.annotations.JsonAdapter] で変換する。
         */
        @SerializedName("created_at")
        var createdAt: String,

        /**
         * 『2000-01-01T00:00:00+00:00』 のような時刻を表す文字列。
         * TODO : [com.google.gson.annotations.JsonAdapter] で変換する。
         */
        @SerializedName("updated_at")
        var updatedAt: String,

        @SerializedName("private")
        var isPrivate: Boolean,

        @SerializedName("url")
        var url: String,

        @SerializedName("tags")
        var tags: ArrayList<Tag>,

        @SerializedName("user")
        var user: User
) : Serializable
