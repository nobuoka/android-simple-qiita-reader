package info.vividcode.android.sqr.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
        @field:SerializedName("id")
        var id: String,

        @field:SerializedName("name")
        var name: String,

        @field:SerializedName("profile_image_url")
        var profileImageUrl: String,

        @field:SerializedName("description")
        var description: String,

        @field:SerializedName("facebook_id")
        var facebookId: String

        /* TODO : 残りの項目のプロパティも作る。 (とりあえずあとで)
                "followees_count": 100,
                "followers_count": 200,
                "github_login_name": "yaotti",
                "items_count": 300,
                "linkedin_id": "yaotti",
                "location": "Tokyo, Japan",
                "organization": "Increments Inc",
                "permanent_id": 1,
                "twitter_screen_name": "yaotti",
                "website_url": "http://yaotti.hatenablog.com"
        */
) : Serializable
