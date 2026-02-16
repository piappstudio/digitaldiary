/*
 * Copyright (c) 2020 .All rights are reserved by PriyangaInfotech
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */

package com.piappstudio.digitaldiary.database.dao

import androidx.room.*
import com.piappstudio.digitaldiary.database.entity.EventInfo
import com.piappstudio.digitaldiary.database.entity.MediaInfo
import com.piappstudio.digitaldiary.database.entity.TagInfo
import com.piappstudio.digitaldiary.database.entity.UserEvent
import kotlinx.coroutines.flow.Flow


@Dao
interface UserEventDao {

    @Transaction
    @Query("SELECT * FROM event_table WHERE title LIKE '%' ||:search ||'%' OR description LIKE '%'|| :search ||'%' ORDER BY strftime('%Y-%m-%d %H:%M', dateInfo) DESC")
    fun getUserEventsByDateDesc(search: String): Flow<List<UserEvent>>

    @Transaction
    @Query("SELECT * FROM event_table")
    fun getUserEventsByDateRange(): Flow<List<UserEvent>>

    @Transaction
    @Query("SELECT * FROM event_table ORDER BY strftime('%Y-%m-%d %H:%M', dateInfo) DESC")
    suspend fun getAllUserEventByDescSync(): List<UserEvent>

    @Query("SELECT * FROM event_table ORDER BY strftime('%Y-%m-%d %H:%M', dateInfo) DESC")
    suspend fun getAllEventInfo(): List<EventInfo>

    @Query("SELECT * FROM media_info")
    suspend fun getAllMedia(): List<MediaInfo>

    @Transaction
    @Query("SELECT * FROM event_table WHERE dateInfo BETWEEN :startDate AND :endDate ORDER BY strftime('%Y-%m-%d %H:%M', dateInfo) DESC")
    fun getUserEventsByDateRange(startDate: String, endDate: String): Flow<List<UserEvent>>

    @Transaction
    @Query("SELECT * FROM event_table WHERE title LIKE '%' ||:search ||'%' OR description LIKE '%'|| :search ||'%' ORDER BY title DESC")
    fun getUserEventsByDesc(search: String): Flow<List<UserEvent>>

    @Transaction
    @Query("SELECT * FROM event_table WHERE title LIKE '%' ||:search ||'%' OR description LIKE '%'|| :search ||'%' ORDER BY title ASC")
    fun getUserEventsByAsc(search: String): Flow<List<UserEvent>>

    @Transaction
    @Query("SELECT * FROM event_table WHERE title LIKE '%' ||:search ||'%' OR description LIKE '%'|| :search ||'%' ORDER BY strftime('%Y-%m-%d %H:%M', dateInfo) ASC")
    fun getUserEventsByDateAsc(search: String): Flow<List<UserEvent>>

    @Transaction
    @Query("SELECT * FROM event_table WHERE eventId=:eventInfo")
    fun getUserEvent(eventInfo: Long): Flow<UserEvent>

    @Insert
    suspend fun insert(event: EventInfo, tags: List<TagInfo>, medias: List<MediaInfo>)

    @Insert
    suspend fun insertEventInfo(event: EventInfo)

    @Insert
    suspend fun insert(mediaInfo: MediaInfo)

    @Insert
    suspend fun insertSync(mediaInfo: MediaInfo)

    @Insert
    suspend fun insertSync(mediaInfo: List<MediaInfo>)

    @Insert
    suspend fun insertTags(tags: List<TagInfo>)

    @Insert
    suspend fun insertMedias(medias: List<MediaInfo>)

    @Insert
    suspend fun insertMediasSync(medias: List<MediaInfo>)

    @Insert
    suspend fun insert(event: EventInfo): Long

    @Delete
    suspend fun delete(mediaInfo: MediaInfo)

    @Insert
    suspend fun insertSync(event: EventInfo): Long
    @Insert
    suspend fun insertEventsSync(events: List<EventInfo>) : List<Long>



    @Update(entity = EventInfo::class)
    suspend fun update(vararg event: EventInfo)

    @Query("DELETE FROM event_table WHERE eventId =:eventId")
    suspend fun deleteEventInfo(eventId: Long)

    @Query("DELETE FROM media_info WHERE eventKey =:eventId")
    suspend fun deleteMediaInfo(eventId: Long)

    @Query("DELETE FROM taginfo WHERE eventKey =:eventId")
    suspend fun deleteTagInfo(eventId: Long)

    @Query("DELETE FROM event_table")
    suspend fun deleteAllEvents()

    @Query("DELETE FROM media_info")
    suspend fun deleteAllMedia()

    @Query("DELETE FROM taginfo")
    suspend fun deleteAllTags()

    @Query("SELECT * FROM media_info WHERE eventKey=:eventId")
    fun getMedias(eventId: Long): Flow<List<MediaInfo>>


    @Query("SELECT * FROM media_info WHERE mediaPath=:mediaPath")
    suspend fun getMediasByName(mediaPath: String): List<MediaInfo>

    @Update(entity = MediaInfo::class)
    suspend fun updateMediaSync(vararg media: MediaInfo)

}