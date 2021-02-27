package hoolah.yolkin.challenge

import com.opencsv.CSVReaderBuilder
import hoolah.yolkin.challenge.TransactionUtils.Companion.parseDateTime
import mu.KotlinLogging
import java.io.File
import java.io.FileReader

private val logger = KotlinLogging.logger {}

class TransactionLoader {

    fun load(file: File): List<Transaction> {
        logger.debug { "[Start] Loading file: $file" }
        val reader = CSVReaderBuilder(FileReader(file))
            .withSkipLines(1) // skip header
            .build()

        val result: ArrayList<Transaction> = arrayListOf()
        while (true) {
            val nextLine = reader.readNext() ?: break

            try {
                result.add(
                    Transaction(
                        id = nextLine[0].trim(),
                        date = parseDateTime(nextLine[1].trim()),
                        amount = nextLine[2].trim().toDouble(),
                        merchant = nextLine[3].trim(),
                        type = TransactionType.valueOf(nextLine[4].trim()),
                        relatedTransaction = nextLine[5].trim()
                    )
                )
            } catch (e: Exception) {
                logger.error { "Can't parse transaction $nextLine" }
            }
        }
        logger.debug { "[Finish] Loading file: $file" }
        return result
    }
}
