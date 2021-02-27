package hoolah.yolkin.challenge

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TransactionUtils {
    companion object {
        private val DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

        fun parseDateTime(dt: String): LocalDateTime =
            LocalDateTime.parse(dt, DATE_TIME_FORMATTER)
    }
}
