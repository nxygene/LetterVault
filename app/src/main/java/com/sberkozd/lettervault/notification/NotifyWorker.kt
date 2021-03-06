package com.sberkozd.lettervault.notification

import android.content.Context
import androidx.annotation.NonNull
import androidx.hilt.work.HiltWorker
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sberkozd.lettervault.ui.detail.DetailRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class NotifyWorker @AssistedInject constructor(
    @Assisted context: Context?,
    @Assisted params: WorkerParameters?,
    private val detailRepository: DetailRepository,
) :
    CoroutineWorker(context!!, params!!) {

    @NonNull
    override suspend fun doWork(): Result {

        // Method to trigger an instant notification

        withContext(Dispatchers.Main) {
            val noteId = inputData.getInt("noteId", 0)
            if (noteId != 0) {
                val note = detailRepository.getNoteByID(noteId)

                note?.let {

                    it.isLocked = 0

                    detailRepository.updateNote(it)

                    val isNotificationEnabled =
                        PreferenceManager.getDefaultSharedPreferences(applicationContext)
                            .getBoolean("notifications", true)
                    if (isNotificationEnabled) {
                        NotificationHelper().sendNoteUnlockedNotification(
                            applicationContext,
                            noteId = it.id,
                            1,
                            true,
                            name = it.noteTitle,
                            description = it.noteContext
                        )
                    }

                }

            }

        }

        return Result.success()
        // (Returning RETRY tells WorkManager to try this task again
        // later; FAILURE says not to try again.)
    }
}