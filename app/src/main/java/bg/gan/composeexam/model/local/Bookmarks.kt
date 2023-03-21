package bg.gan.composeexam.model.local

import androidx.room.Entity
import bg.gan.composeexam.model.remoteData.GetUserResponse


//Inherit from open class
@Entity(tableName = "bookmarks_table", inheritSuperIndices = true)
class Bookmarks: GetUserResponse()