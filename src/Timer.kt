import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.schedule
//timer class from chatgpt
class AsyncTimer {
    private var timer: Timer? = null

    /**
     * Starts the timer for the specified duration in milliseconds.
     *
     * @param durationMillis The duration for which the timer should run.
     * @param onTick A lambda that is called every second with the remaining time.
     * @param onFinish A lambda that is called when the timer finishes.
     */
    fun start(
        durationMillis: Long,
        onTick: (Long) -> Unit = {},
        onFinish: () -> Unit = {}
    ) {
        // Cancel any existing timer before starting a new one
        cancel()

        timer = Timer()
        val endTime = System.currentTimeMillis() + durationMillis

        // Schedule the onFinish task
        timer?.schedule(durationMillis) {
            onFinish()
            cancel()
        }

        // Schedule the onTick task every second
        val tickInterval = 1000L
        Timer().schedule(tickInterval, tickInterval) {
            val remainingTime = endTime - System.currentTimeMillis()
            if (remainingTime > 0) {
                onTick(remainingTime)
            } else {
                cancel()
            }
        }
    }

    /**
     * Cancels the running timer if it exists.
     */
    fun cancel() {
        timer?.cancel()
        timer = null
    }
}