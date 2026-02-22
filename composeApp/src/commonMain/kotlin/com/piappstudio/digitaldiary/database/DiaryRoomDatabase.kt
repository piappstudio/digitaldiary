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

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.piappstudio.digitaldiary.database.dao.ReminderDao
import com.piappstudio.digitaldiary.database.dao.UserEventDao
import com.piappstudio.digitaldiary.database.entity.EventInfo
import com.piappstudio.digitaldiary.database.entity.MediaInfo
import com.piappstudio.digitaldiary.database.entity.ReminderInfo
import com.piappstudio.digitaldiary.database.entity.TagInfo

@Database(
    entities = [EventInfo::class, MediaInfo::class, TagInfo::class, ReminderInfo::class],
    version = 4,
    exportSchema = false
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class DiaryRoomDatabase : RoomDatabase() {
    abstract fun userEventDao(): UserEventDao
    abstract fun reminderDao(): ReminderDao

    companion object {

        const val DB_NAME = "diary_database.db"

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(connection: SQLiteConnection) {
                connection.execSQL("CREATE TABLE media_info_new (mediaId INTEGER, mediaPath TEXT NOT NULL, eventKey INTEGER NOT NULL, PRIMARY KEY(mediaId))")
                connection.execSQL("insert into media_info_new  select * from media_info")
                connection.execSQL("DROP TABLE media_info")
                connection.execSQL("ALTER TABLE media_info_new RENAME TO media_info")
            }
        }

        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(connection: SQLiteConnection) {
                // Create temp table and mke eventKey is optional
                connection.execSQL("CREATE TABLE IF NOT EXISTS `reminder_table` (`reminderId` INTEGER, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `startDate` TEXT, `endDate` TEXT, `isReminderRequired` INTEGER NOT NULL, `remindBefore` INTEGER, PRIMARY KEY(`reminderId`))")
            }
        }

        val MIGRATION_3_4: Migration = object : Migration(3, 4) {
            override fun migrate(connection: SQLiteConnection) {
                // Migrate taginfo to support auto-generate
                connection.execSQL("CREATE TABLE IF NOT EXISTS `taginfo_new` (`tagId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `tagName` TEXT NOT NULL, `eventKey` INTEGER NOT NULL)")
                connection.execSQL("INSERT INTO `taginfo_new` (`tagId`, `tagName`, `eventKey`) SELECT `tagId`, `tagName`, `eventKey` FROM `taginfo`")
                connection.execSQL("DROP TABLE `taginfo`")
                connection.execSQL("ALTER TABLE `taginfo_new` RENAME TO `taginfo`")
                
                // Also migrating media_info just in case, to ensure it also supports auto-generate if needed later
                connection.execSQL("CREATE TABLE IF NOT EXISTS `media_info_new` (`mediaId` INTEGER PRIMARY KEY AUTOINCREMENT, `mediaPath` TEXT NOT NULL, `eventKey` INTEGER NOT NULL)")
                connection.execSQL("INSERT INTO `media_info_new` (`mediaId`, `mediaPath`, `eventKey`) SELECT `mediaId`, `mediaPath`, `eventKey` FROM `media_info`")
                connection.execSQL("DROP TABLE `media_info`")
                connection.execSQL("ALTER TABLE `media_info_new` RENAME TO `media_info`")
            }
        }
    }
}
