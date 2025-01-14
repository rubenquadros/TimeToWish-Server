package io.github.rubenquadros.timetowish.db

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.Query
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal suspend fun <T>ApiFuture<T>.await(): T {
    val result = suspendCancellableCoroutine { cancellableContinuation ->
        val executor = Executors.newSingleThreadExecutor()

        cancellableContinuation.invokeOnCancellation {
            this.cancel(true)
            executor.shutdown()
        }

        this.addListener({
            try {
                val writeResult = this.get()
                cancellableContinuation.resume(writeResult)
            } catch (e: Exception) {
                cancellableContinuation.resumeWithException(e)
            } finally {
                executor.shutdown()
            }
        }, executor)
    }

    return result
}