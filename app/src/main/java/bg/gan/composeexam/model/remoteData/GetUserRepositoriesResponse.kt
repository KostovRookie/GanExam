package bg.gan.composeexam.model.remoteData

import kotlinx.serialization.Serializable


@Serializable
class GetUserRepositoriesResponse : ArrayList<GetUserRepositoriesResponseItem>()