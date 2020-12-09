package com.ismin.android

import java.io.Serializable

data class Film(val title: String,
                val url: String,
                val summary: String,
                val aggregated_rating: Float,
                val authors: Array<String>,
                val casting: Array<String>,
                val genres: Array<String>,
                val keywords: Array<String>,
                val first_release_date: String,
                val runtime: Int,
                val cover: String,
                val members: Int,
                val size: Int,
                val date_added: String
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Film

        if (title != other.title) return false
        if (url != other.url) return false
        if (summary != other.summary) return false
        if (aggregated_rating != other.aggregated_rating) return false
        if (!authors.contentEquals(other.authors)) return false
        if (!casting.contentEquals(other.casting)) return false
        if (!genres.contentEquals(other.genres)) return false
        if (!keywords.contentEquals(other.keywords)) return false
        if (first_release_date != other.first_release_date) return false
        if (runtime != other.runtime) return false
        if (cover != other.cover) return false
        if (members != other.members) return false
        if (size != other.size) return false
        if (date_added != other.date_added) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + summary.hashCode()
        result = 31 * result + aggregated_rating.hashCode()
        result = 31 * result + authors.contentHashCode()
        result = 31 * result + casting.contentHashCode()
        result = 31 * result + genres.contentHashCode()
        result = 31 * result + keywords.contentHashCode()
        result = 31 * result + first_release_date.hashCode()
        result = 31 * result + runtime
        result = 31 * result + cover.hashCode()
        result = 31 * result + members
        result = 31 * result + size
        result = 31 * result + date_added.hashCode()
        return result
    }
}
