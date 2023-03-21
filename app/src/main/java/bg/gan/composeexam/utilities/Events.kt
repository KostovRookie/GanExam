package bg.gan.composeexam.utilities

import bg.gan.composeexam.model.local.Bookmarks
import bg.gan.composeexam.model.remoteData.GetUserResponse

sealed class UserEvent {
    data class SearchUser(val search: String) : UserEvent()
    data class SetSearchText(val search: String) : UserEvent()
    data class SetValidationError(val message: String) : UserEvent()
    object ClearSearchText : UserEvent()
}

fun GetUserResponse.toBookmarks(): Bookmarks {  //transfer data class to database
    return Bookmarks().apply {
        this.avatar_url = this@toBookmarks.avatar_url
        this.bio = this@toBookmarks.bio
        this.blog = this@toBookmarks.blog
        this.company = this@toBookmarks.company
        this.created_at = this@toBookmarks.created_at
        this.email = this@toBookmarks.email
        this.events_url = this@toBookmarks.events_url
        this.gists_url = this@toBookmarks.gists_url
        this.gravatar_id = this@toBookmarks.gravatar_id
        this.hireable = this@toBookmarks.hireable
        this.html_url = this@toBookmarks.html_url
        this.id = this@toBookmarks.id
        this.location = this@toBookmarks.location
        this.login = this@toBookmarks.login
        this.name = this@toBookmarks.name
        this.node_id = this@toBookmarks.node_id
        this.organizations_url = this@toBookmarks.organizations_url
        this.public_gists = this@toBookmarks.public_gists
        this.public_repos = this@toBookmarks.public_repos
        this.repos_url = this@toBookmarks.repos_url
        this.starred_url = this@toBookmarks.starred_url
        this.twitter_username = this@toBookmarks.twitter_username
        this.type = this@toBookmarks.type
        this.updated_at = this@toBookmarks.updated_at
        this.url = this@toBookmarks.url

    }
}