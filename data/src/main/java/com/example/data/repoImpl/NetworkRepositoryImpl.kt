import com.example.domain.dataClasses.PingResult
import com.example.domain.dataClasses.PingStatus
import com.example.domain.repo.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetAddress

class NetworkRepositoryImpl : NetworkRepository {

    override suspend fun pingTest(): PingResult = withContext(Dispatchers.IO) {
        val target = "8.8.8.8"
        val attempts = 3
        val latencies = mutableListOf<Long>()

        repeat(attempts) {
            val start = System.currentTimeMillis()
            val reachable = InetAddress.getByName(target).isReachable(3000)
            val end = System.currentTimeMillis()
            val latency = if (reachable) end - start else -1
            if (latency != -1L) latencies.add(latency)
        }

        if (latencies.isEmpty()) {
            return@withContext PingResult(
                averageLatency = -1,
                minLatency = -1,
                maxLatency = -1,
                status = PingStatus.FAILED
            )
        }

        val avg = latencies.average().toLong()
        val min = latencies.minOrNull() ?: 0
        val max = latencies.maxOrNull() ?: 0

        val status = when {
            avg < 50 -> PingStatus.EXCELLENT
            avg < 100 -> PingStatus.GOOD
            avg < 200 -> PingStatus.SLOW
            else -> PingStatus.BAD
        }

        return@withContext PingResult(
            averageLatency = avg,
            minLatency = min,
            maxLatency = max,
            status = status
        )
    }
}
