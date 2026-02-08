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

package com.piappstudio.digitaldiary.database.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "reminder_table")
data class ReminderInfo(
    @PrimaryKey var reminderId: Long?,
    var title: String,
    var description: String,
    var startDate: String?,
    var endDate: String?,
    var isReminderRequired: Boolean,
    var remindBefore: Int?
)