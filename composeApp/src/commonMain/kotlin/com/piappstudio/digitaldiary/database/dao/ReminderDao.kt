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
import com.piappstudio.digitaldiary.database.entity.ReminderEvent
import com.piappstudio.digitaldiary.database.entity.ReminderInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Transaction
    @Query("SELECT * FROM reminder_table WHERE title LIKE '%' ||:search ||'%' OR description LIKE '%'|| :search ||'%' ORDER BY title ASC")
    fun getRemindersByAZ(search: String): Flow<List<ReminderEvent>>

    @Transaction
    @Query("SELECT * FROM reminder_table WHERE title LIKE '%' ||:search ||'%' OR description LIKE '%'|| :search ||'%' ORDER BY title DESC")
    fun getRemindersByZA(search: String): Flow<List<ReminderEvent>>

    @Transaction
    @Query("SELECT * FROM reminder_table WHERE title LIKE '%' ||:search ||'%' OR description LIKE '%'|| :search ||'%' ORDER BY strftime('%Y-%m-%d %H:%M', endDate) DESC")
    fun getRemindersByEndDateDES(search: String): Flow<List<ReminderEvent>>

    @Transaction
    @Query("SELECT * FROM reminder_table WHERE title LIKE '%' ||:search ||'%' OR description LIKE '%'|| :search ||'%' ORDER BY strftime('%Y-%m-%d %H:%M', endDate) ASC")
    fun getRemindersByEndDateASC(search: String): Flow<List<ReminderEvent>>


    @Query("SELECT * FROM reminder_table")
    suspend fun getAllReminderInfosSync(): List<ReminderInfo>

    @Insert
    suspend fun insertReminderInfo(reminderInfo: ReminderInfo): Long

    @Insert
    suspend fun insertReminderInfoSync(reminders: List<ReminderInfo>)

    @Delete
    suspend fun delete(reminderInfo: ReminderInfo)

    @Update(entity = ReminderInfo::class)
    suspend fun update(vararg reminderInfo: ReminderInfo)

    @Transaction
    @Query("SELECT * FROM reminder_table WHERE reminderId =:reminderKey")
    fun getReminderEvent(reminderKey: Long): Flow<ReminderEvent>


    @Transaction
    @Query("SELECT * FROM reminder_table WHERE reminderId =:reminderKey")
    suspend fun getReminderEventSync(reminderKey: Long): ReminderEvent

    @Query("SELECT * FROM reminder_table")
    fun getReminders(): Flow<List<ReminderInfo>>

    @Query("DELETE from reminder_table")
    suspend fun clear()
}