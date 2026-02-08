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

import com.piappstudio.digitaldiary.database.dao.ReminderDao
import com.piappstudio.digitaldiary.database.entity.ReminderEvent
import com.piappstudio.digitaldiary.database.entity.ReminderInfo
import com.piappstudio.digitaldiary.database.pojo.ReminderFilterOption
import com.piappstudio.digitaldiary.database.pojo.ReminderSortOrder
import kotlinx.coroutines.flow.Flow

class ReminderRepository(val reminderDao: ReminderDao) {
    fun getAllReminders(filterOption: ReminderFilterOption): Flow<ReminderEvent> {

        val query = filterOption.query ?: ""
        return when (filterOption.sortOrder) {
            ReminderSortOrder.ZA -> {
                reminderDao.getRemindersByZA(query)
            }
            ReminderSortOrder.AZ -> {
                reminderDao.getRemindersByAZ(query)
            }
            ReminderSortOrder.EVENT_ASC -> {
                reminderDao.getRemindersByEndDateASC(query)
            }
            ReminderSortOrder.EVENT_DES -> {
                reminderDao.getRemindersByEndDateDES(query)
            }
        }
    }

    fun getReminderEvent(reminderKey: Long): Flow<ReminderEvent> {
        return reminderDao.getReminderEvent(reminderKey)
    }

    fun getReminders(): Flow<List<ReminderInfo>> {
        return reminderDao.getReminders()
    }

    suspend fun insert(reminderInfo: ReminderInfo): Long {
        return reminderDao.insertReminderInfo(reminderInfo)
    }

    suspend fun delete(reminderInfo: ReminderInfo) {
        reminderDao.delete(reminderInfo)
    }

    suspend fun update(reminderInfo: ReminderInfo) {
        reminderDao.update(reminderInfo)
    }
}