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

package com.piappstudio.digitaldiary.database.pojo

enum class ReminderSortOrder {
    AZ, ZA, EVENT_ASC, EVENT_DES
}

data class ReminderFilterOption(val sortOrder: ReminderSortOrder, var query: String?)