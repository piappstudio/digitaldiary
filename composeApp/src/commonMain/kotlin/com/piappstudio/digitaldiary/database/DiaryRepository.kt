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

package com.piappstudio.digitaldiary.database


import com.piappstudio.digitaldiary.database.dao.UserEventDao
import com.piappstudio.digitaldiary.database.entity.EventInfo
import com.piappstudio.digitaldiary.database.entity.MediaInfo
import com.piappstudio.digitaldiary.database.entity.TagInfo
import com.piappstudio.digitaldiary.database.entity.UserEvent
import com.piappstudio.digitaldiary.database.pojo.FilterOption
import com.piappstudio.digitaldiary.database.pojo.SortingOrder
import kotlinx.coroutines.flow.Flow

class DiaryRepository(private val userEventDao: UserEventDao) {
    fun getAllUserEvents(filterOption: FilterOption): Flow<List<UserEvent>> {
        val search = filterOption.query ?: ""
        return when (filterOption.sortingOrder) {
            SortingOrder.ZA -> {
                userEventDao.getUserEventsByDesc(search)
            }
            SortingOrder.AZ -> {
                userEventDao.getUserEventsByAsc(search)
            }
            SortingOrder.OLD -> {
                userEventDao.getUserEventsByDateAsc(search)
            }
            else -> {
                userEventDao.getUserEventsByDateDesc(search)
            }
        }
    }

    fun getUserEventsByDateRange(startDate: String, endDate: String): Flow<List<UserEvent>> {
        return userEventDao.getUserEventsByDateRange(startDate, endDate)
    }

    fun getUserEventsByDateRange(): Flow<List<UserEvent>> {
        return userEventDao.getUserEventsByDateRange()
    }

    fun getUserEvent(eventId: Long): Flow<UserEvent> {
        return userEventDao.getUserEvent(eventId)
    }

    fun getMedias(eventId: Long): Flow<List<MediaInfo>> {
        return userEventDao.getMedias(eventId)
    }

    suspend fun insert(userEvent: UserEvent) {
        val userId = userEventDao.insert(userEvent.eventInfo)
        
        userEvent.tags?.let { tags ->
            val tagsToInsert = tags.map { it.copy(eventKey = userId) }
            userEventDao.insertTags(tagsToInsert)
        }
        
        userEvent.mediaPaths?.let { medias ->
            val mediasToInsert = medias.map { it.copy(eventKey = userId) }
            userEventDao.insertMedias(mediasToInsert)
        }
    }

    suspend fun insert(eventInfo: EventInfo): Long {
        return userEventDao.insert(eventInfo)
    }

    suspend fun updateEventInfo(eventInfo: EventInfo) {
        userEventDao.update(eventInfo)
    }

    suspend fun updateFullEvent(userEvent: UserEvent) {
        val eventId = userEvent.eventInfo.eventId ?: return
        
        // Update basic info
        userEventDao.update(userEvent.eventInfo)
        
        // Update tags: simple approach - delete all and re-insert
        userEventDao.deleteTagInfo(eventId)
        userEvent.tags?.let { tags ->
            val tagsToInsert = tags.map { it.copy(eventKey = eventId, tagId = 0) }
            userEventDao.insertTags(tagsToInsert)
        }
        
        // Update media: simple approach - delete all and re-insert
        userEventDao.deleteMediaInfo(eventId)
        userEvent.mediaPaths?.let { medias ->
            val mediasToInsert = medias.map { it.copy(eventKey = eventId) }
            userEventDao.insertMedias(mediasToInsert)
        }
    }

    suspend fun getMediasByName(mediaPath: String): List<MediaInfo> {
        return userEventDao.getMediasByName(mediaPath)
    }

    suspend fun delete(eventId: Long) {
        userEventDao.deleteEventInfo(eventId)
        userEventDao.deleteMediaInfo(eventId)
        userEventDao.deleteTagInfo(eventId)
    }

    suspend fun deleteAll() {
        userEventDao.deleteAllEvents()
        userEventDao.deleteAllMedia()
        userEventDao.deleteAllTags()
    }

    suspend fun insert(mediaInfo: MediaInfo) {
        userEventDao.insert(mediaInfo)
    }

    suspend fun delete(mediaInfo: MediaInfo) {
        userEventDao.delete(mediaInfo)
    }

    suspend fun deleteMediaInfo(eventId: Long) {
        userEventDao.deleteMediaInfo(eventId)
    }

    suspend fun insertTags(tags: List<TagInfo>) {
        userEventDao.insertTags(tags)
    }

    suspend fun insertMedias(medias: List<MediaInfo>) {
        userEventDao.insertMedias(medias)
    }
}
