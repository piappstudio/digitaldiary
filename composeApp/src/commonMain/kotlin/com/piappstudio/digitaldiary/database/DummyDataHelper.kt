package com.piappstudio.digitaldiary.database

import com.piappstudio.digitaldiary.database.entity.EventInfo
import com.piappstudio.digitaldiary.database.entity.MediaInfo
import com.piappstudio.digitaldiary.database.entity.ReminderInfo
import com.piappstudio.digitaldiary.database.entity.TagInfo
import com.piappstudio.digitaldiary.database.entity.UserEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Helper class to insert comprehensive dummy data into the database
 * This is useful for testing and development purposes
 */
class DummyDataHelper(
    private val diaryRepository: DiaryRepository,
    private val reminderRepository: ReminderRepository
) {
    
    suspend fun insertAllDummyData() = withContext(Dispatchers.Default) {
        // Clear existing data before inserting new dummy data
        diaryRepository.deleteAll()
        reminderRepository.deleteAll()
        
        insertDummyDiaryEntries()
        insertDummyReminders()
    }

    private suspend fun insertDummyDiaryEntries() {
        val diaryEntries = listOf(
            // Entry 11 - Mixed: Happy + Work + Family
            UserEvent(
                eventInfo = EventInfo(
                    eventId = null,
                    dateInfo = "2026-02-07 12:30:00.123456Z",
                    title = "Work Family Day Celebration",
                    description = "Our company organized a family day event. Brought my family to the office, showed them my workplace, and we had fun team activities together. Best of both worlds!",
                    emotion = "Happy"
                ),
                mediaPaths = listOf(
                    MediaInfo(25L, "event_11.png", 11L),
                    MediaInfo(26L, "event_11.png", 11L),
                    MediaInfo(27L, "event_11.png", 11L),
                    MediaInfo(28L, "event_11.mp3", 11L)
                ),
                tags = listOf(
                    TagInfo(38L, "Work", 11L),
                    TagInfo(39L, "Family", 11L),
                    TagInfo(40L, "Company", 11L),
                    TagInfo(41L, "Fun", 11L),
                    TagInfo(42L, "Celebration", 11L)
                )
            ),
            // Entry 12 - Mixed: Excited + Travel
            UserEvent(
                eventInfo = EventInfo(
                    eventId = null,
                    dateInfo = "2026-02-06 18:15:30.654321Z",
                    title = "Booked Dream Vacation",
                    description = "Finally booked the tickets for our dream vacation to Europe! We'll be visiting Paris, Rome, and Barcelona. Can't wait for this adventure!",
                    emotion = "Excited"
                ),
                mediaPaths = listOf(
                    MediaInfo(29L, "event_12.png", 12L),
                    MediaInfo(30L, "event_12.png", 12L)
                ),
                tags = listOf(
                    TagInfo(43L, "Travel", 12L),
                    TagInfo(44L, "Vacation", 12L),
                    TagInfo(45L, "Europe", 12L),
                    TagInfo(46L, "Adventure", 12L)
                )
            ),
            // Entry 13 - Mixed: Inspired + Learning
            UserEvent(
                eventInfo = EventInfo(
                    eventId = null,
                    dateInfo = "2026-02-05 14:45:00.789012Z",
                    title = "Started New Online Course",
                    description = "Enrolled in an advanced course on machine learning. First module was fascinating and has opened up new possibilities for my career.",
                    emotion = "Inspired"
                ),
                mediaPaths = listOf(
                    MediaInfo(31L, "event_13.mp3", 13L)
                ),
                tags = listOf(
                    TagInfo(47L, "Learning", 13L),
                    TagInfo(48L, "Education", 13L),
                    TagInfo(49L, "Career", 13L),
                    TagInfo(50L, "Technology", 13L)
                )
            ),
            // Entry 14 - Mixed: Grateful + Achievement
            UserEvent(
                eventInfo = EventInfo(
                    eventId = null,
                    dateInfo = "2026-02-04 09:20:15.345678Z",
                    title = "Promoted at Work!",
                    description = "Got promoted to Senior Developer today! It's the result of hard work, dedication, and amazing support from my team and family. Feeling incredibly grateful and proud!",
                    emotion = "Grateful"
                ),
                mediaPaths = listOf(
                    MediaInfo(32L, "event_14.png", 14L),
                    MediaInfo(33L, "event_14.mp3", 14L)
                ),
                tags = listOf(
                    TagInfo(51L, "Work", 14L),
                    TagInfo(52L, "Promotion", 14L),
                    TagInfo(53L, "Achievement", 14L),
                    TagInfo(54L, "Success", 14L),
                    TagInfo(55L, "Grateful", 14L)
                )
            ),
            // Entry 15 - Mixed: Calm + Self-Care
            UserEvent(
                eventInfo = EventInfo(
                    eventId = null,
                    dateInfo = "2026-02-03 20:00:00.987654Z",
                    title = "Spa Day at Home",
                    description = "Spent the day pampering myself. Took a long bath with essential oils, did a face mask, and relaxed with soft music. Self-care at its finest!",
                    emotion = "Calm"
                ),
                mediaPaths = listOf(
                    MediaInfo(34L, "event_15.png", 15L)
                ),
                tags = listOf(
                    TagInfo(56L, "Self-Care", 15L),
                    TagInfo(57L, "Wellness", 15L),
                    TagInfo(58L, "Relaxation", 15L),
                    TagInfo(59L, "Spa", 15L)
                )
            ),
            // Entry 16 - Mixed: Adventurous + Sports
            UserEvent(
                eventInfo = EventInfo(
                    eventId = null,
                    dateInfo = "2026-02-02 15:30:45.456789Z",
                    title = "Tried Skydiving",
                    description = "Completed my first skydiving experience today! The adrenaline rush was unreal. Jumping out of a plane at 15,000 feet was the most thrilling moment of my life!",
                    emotion = "Adventurous"
                ),
                mediaPaths = listOf(
                    MediaInfo(35L, "event_16.png", 16L),
                    MediaInfo(36L, "event_16.png", 16L),
                    MediaInfo(37L, "event_16.mp3", 16L)
                ),
                tags = listOf(
                    TagInfo(60L, "Adventure", 16L),
                    TagInfo(61L, "Sports", 16L),
                    TagInfo(62L, "Extreme", 16L),
                    TagInfo(63L, "Thrilling", 16L),
                    TagInfo(64L, "Bucket List", 16L)
                )
            ),
            // Entry 17 - Mixed: Anxious + Challenge
            UserEvent(
                eventInfo = EventInfo(
                    eventId = null,
                    dateInfo = "2026-02-01 10:15:00.111222Z",
                    title = "Difficult Client Meeting",
                    description = "Had a challenging meeting with a difficult client. Managed to handle their concerns professionally and turned a negative situation around. Proved my abilities!",
                    emotion = "Anxious"
                ),
                mediaPaths = listOf(
                    MediaInfo(38L, "event_17.mp3", 17L)
                ),
                tags = listOf(
                    TagInfo(65L, "Work", 17L),
                    TagInfo(66L, "Challenge", 17L),
                    TagInfo(67L, "Client", 17L),
                    TagInfo(68L, "Professional", 17L)
                )
            ),
            // Entry 18 - Mixed: Romantic + Anniversary
            UserEvent(
                eventInfo = EventInfo(
                    eventId = null,
                    dateInfo = "2026-01-31 19:45:30.333444Z",
                    title = "5 Year Anniversary",
                    description = "Celebrated our 5 year anniversary today! We renewed our vows in a private ceremony surrounded by close friends and family. A day filled with love and joy!",
                    emotion = "Romantic"
                ),
                mediaPaths = listOf(
                    MediaInfo(39L, "event_18.png", 18L),
                    MediaInfo(40L, "event_18.png", 18L),
                    MediaInfo(41L, "event_18.png", 18L),
                    MediaInfo(42L, "event_18.png", 18L),
                    MediaInfo(43L, "event_18.mp3", 18L)
                ),
                tags = listOf(
                    TagInfo(69L, "Love", 18L),
                    TagInfo(70L, "Anniversary", 18L),
                    TagInfo(71L, "Marriage", 18L),
                    TagInfo(72L, "Vows", 18L),
                    TagInfo(73L, "Celebration", 18L)
                )
            ),
            // Entry 19 - Mixed: Sad + Loss
            UserEvent(
                eventInfo = EventInfo(
                    eventId = null,
                    dateInfo = "2026-01-30 16:20:00.555666Z",
                    title = "Loss of a Dear Friend",
                    description = "Said goodbye to my dear friend today. We had incredible memories together. While I'm sad, I'm grateful for the time we spent. They will be missed dearly.",
                    emotion = "Sad"
                ),
                mediaPaths = listOf(
                    MediaInfo(44L, "event_19.mp3", 19L)
                ),
                tags = listOf(
                    TagInfo(74L, "Loss", 19L),
                    TagInfo(75L, "Memory", 19L),
                    TagInfo(76L, "Friendship", 19L),
                    TagInfo(77L, "Emotional", 19L)
                )
            ),
            // Entry 20 - Mixed: Peaceful + Nature
            UserEvent(
                eventInfo = EventInfo(
                    eventId = null,
                    dateInfo = "2026-01-29 06:00:00.777888Z",
                    title = "Sunrise Watching at Beach",
                    description = "Woke up early to watch the sunrise at the beach. The golden colors reflecting on the water were magical. A perfect way to start a peaceful day.",
                    emotion = "Peaceful"
                ),
                mediaPaths = listOf(
                    MediaInfo(45L, "event_20.png", 20L),
                    MediaInfo(46L, "event_20.png", 20L)
                ),
                tags = listOf(
                    TagInfo(78L, "Nature", 20L),
                    TagInfo(79L, "Beach", 20L),
                    TagInfo(80L, "Sunrise", 20L),
                    TagInfo(81L, "Peace", 20L),
                    TagInfo(82L, "Morning", 20L)
                )
            ),
            // Entry 21 - Mixed: Happy + Creative
            UserEvent(
                eventInfo = EventInfo(
                    eventId = null,
                    dateInfo = "2026-01-28 14:30:00.999000Z",
                    title = "Completed Art Project",
                    description = "Finally finished my painting! It took months of work but seeing it completed is so rewarding. The colors and composition turned out even better than I imagined!",
                    emotion = "Happy"
                ),
                mediaPaths = listOf(
                    MediaInfo(47L, "event_21.png", 21L),
                    MediaInfo(48L, "event_21.png", 21L)
                ),
                tags = listOf(
                    TagInfo(83L, "Art", 21L),
                    TagInfo(84L, "Creative", 21L),
                    TagInfo(85L, "Project", 21L),
                    TagInfo(86L, "Accomplishment", 21L)
                )
            ),
            // Entry 22 - Mixed: Excited + New Beginning
            UserEvent(
                eventInfo = EventInfo(
                    eventId = null,
                    dateInfo = "2026-01-27 10:00:00.111222Z",
                    title = "Started New Job",
                    description = "First day at my new job! The team is welcoming, the office is modern, and I'm excited about the projects we'll be working on. Fresh start, new opportunities!",
                    emotion = "Excited"
                ),
                mediaPaths = listOf(
                    MediaInfo(49L, "event_22.png", 22L),
                    MediaInfo(50L, "event_22.png", 22L)
                ),
                tags = listOf(
                    TagInfo(87L, "Work", 22L),
                    TagInfo(88L, "New Job", 22L),
                    TagInfo(89L, "Career", 22L),
                    TagInfo(90L, "Fresh Start", 22L),
                    TagInfo(91L, "Opportunity", 22L)
                )
            ),
            // Entry 23 - Mixed: Inspired + Social Change
            UserEvent(
                eventInfo = EventInfo(
                    eventId = null,
                    dateInfo = "2026-01-26 17:45:00.333444Z",
                    title = "Volunteered at Local Shelter",
                    description = "Spent the afternoon volunteering at the local animal shelter. Helped rescue dogs find new homes and felt inspired to make a difference in the community.",
                    emotion = "Inspired"
                ),
                mediaPaths = listOf(
                    MediaInfo(51L, "event_23.png", 23L),
                    MediaInfo(52L, "event_23.png", 23L),
                    MediaInfo(53L, "event_23.png", 23L)
                ),
                tags = listOf(
                    TagInfo(92L, "Volunteer", 23L),
                    TagInfo(93L, "Community", 23L),
                    TagInfo(94L, "Animals", 23L),
                    TagInfo(95L, "Helping", 23L),
                    TagInfo(96L, "Inspiration", 23L)
                )
            ),
            // Entry 24 - Mixed: Grateful + Health
            UserEvent(
                eventInfo = EventInfo(
                    eventId = null,
                    dateInfo = "2026-01-25 08:30:00.555666Z",
                    title = "Completed 30-Day Fitness Challenge",
                    description = "Completed my 30-day fitness challenge! Lost weight, gained strength, and feel healthier than ever. Grateful for my body and the discipline to stick with it.",
                    emotion = "Grateful"
                ),
                mediaPaths = listOf(
                    MediaInfo(54L, "event_24.png", 24L),
                    MediaInfo(55L, "event_24.mp3", 24L)
                ),
                tags = listOf(
                    TagInfo(97L, "Fitness", 24L),
                    TagInfo(98L, "Health", 24L),
                    TagInfo(99L, "Wellness", 24L),
                    TagInfo(100L, "Challenge", 24L),
                    TagInfo(101L, "Achievement", 24L)
                )
            ),
            // Entry 25 - Mixed: Calm + Reflection
            UserEvent(
                eventInfo = EventInfo(
                    eventId = null,
                    dateInfo = "2026-01-24 21:00:00.777888Z",
                    title = "Journaling and Reflection",
                    description = "Spent the evening journaling about my goals for the year. Reflected on my growth, mistakes, and lessons learned. Writing is therapeutic and clarifying.",
                    emotion = "Calm"
                ),
                mediaPaths = listOf(
                    MediaInfo(56L, "event_25.mp3", 25L)
                ),
                tags = listOf(
                    TagInfo(102L, "Journaling", 25L),
                    TagInfo(103L, "Reflection", 25L),
                    TagInfo(104L, "Growth", 25L),
                    TagInfo(105L, "Goals", 25L)
                )
            )
        )

        diaryEntries.forEach { entry ->
            diaryRepository.insert(entry)
        }
    }

    private suspend fun insertDummyReminders() {
        val reminders = listOf(
            // Urgent reminder - Today
            ReminderInfo(
                reminderId = null,
                title = "Team Standup Meeting",
                description = "Daily team sync-up to discuss progress and blockers. Conference room B at 10:00 AM.",
                startDate = "2026-02-16",
                endDate = "2026-02-16",
                isReminderRequired = true,
                remindBefore = 30
            ),
            // High priority - Near future
            ReminderInfo(
                reminderId = null,
                title = "Project Deadline",
                description = "Final submission for the mobile app development project. All features must be completed and tested.",
                startDate = "2026-02-18",
                endDate = "2026-02-20",
                isReminderRequired = true,
                remindBefore = 60
            ),
            // High priority - Doctor appointment
            ReminderInfo(
                reminderId = null,
                title = "Doctor's Appointment",
                description = "Annual health checkup with Dr. Sarah Smith at 2:00 PM. Bring insurance card and list of medications.",
                startDate = "2026-02-22",
                endDate = "2026-02-22",
                isReminderRequired = true,
                remindBefore = 120
            ),
            // Medium priority - Vacation planning
            ReminderInfo(
                reminderId = null,
                title = "Vacation Planning",
                description = "Start planning summer vacation. Research destinations, compare flights, and book accommodations.",
                startDate = "2026-02-25",
                endDate = "2026-03-05",
                isReminderRequired = false,
                remindBefore = null
            ),
            // Medium priority - Car maintenance
            ReminderInfo(
                reminderId = null,
                title = "Car Service",
                description = "Schedule regular maintenance for the car. Oil change, tire rotation, and general inspection due.",
                startDate = "2026-03-01",
                endDate = "2026-03-10",
                isReminderRequired = true,
                remindBefore = 1440
            ),
            // Low priority - Gym membership
            ReminderInfo(
                reminderId = null,
                title = "Renew Gym Membership",
                description = "Gym membership expires at the end of March. Decide if you want to renew or switch to a new gym.",
                startDate = "2026-03-15",
                endDate = "2026-03-31",
                isReminderRequired = false,
                remindBefore = null
            ),
            // Medium priority - Birthday gift
            ReminderInfo(
                reminderId = null,
                title = "Buy Birthday Gift",
                description = "Sister's birthday is coming up. Find and purchase a nice gift before the deadline.",
                startDate = "2026-03-05",
                endDate = "2026-03-20",
                isReminderRequired = true,
                remindBefore = 2880
            ),
            // Low priority - House cleaning
            ReminderInfo(
                reminderId = null,
                title = "Spring Cleaning",
                description = "Time for deep cleaning. Organize rooms, clean windows, and declutter unnecessary items.",
                startDate = "2026-03-10",
                endDate = "2026-03-25",
                isReminderRequired = false,
                remindBefore = null
            ),
            // High priority - Payment due
            ReminderInfo(
                reminderId = null,
                title = "Credit Card Payment Due",
                description = "Pay monthly credit card bill to avoid late charges and maintain good credit score.",
                startDate = "2026-02-25",
                endDate = "2026-02-28",
                isReminderRequired = true,
                remindBefore = 120
            ),
            // Medium priority - Course deadline
            ReminderInfo(
                reminderId = null,
                title = "Online Course Assignment",
                description = "Complete and submit the final assignment for the online certification course before the deadline.",
                startDate = "2026-03-01",
                endDate = "2026-03-15",
                isReminderRequired = true,
                remindBefore = 180
            )
        )

        reminders.forEach { reminder ->
            reminderRepository.insert(reminder)
        }
    }
}