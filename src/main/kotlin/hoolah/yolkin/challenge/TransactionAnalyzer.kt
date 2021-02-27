package hoolah.yolkin.challenge

import mu.KotlinLogging
import java.io.File
import java.lang.IllegalArgumentException

private val logger = KotlinLogging.logger {}

class TransactionAnalyzer(
    private val file: File,
    private val transactions: List<Transaction> = TransactionLoader().load(file),

    // These two variables are used to speed up filtration process on big amount of data
    private val merchant2Transactions: MutableMap<String, MutableList<Transaction>> = hashMapOf(),
    private val reversalTransactions: MutableSet<String> = hashSetOf()
) {

    // init block
    init {
        for (transaction in transactions) {
            merchant2Transactions.computeIfAbsent(transaction.merchant) { mutableListOf() }.add(transaction)
            if (transaction.type == TransactionType.REVERSAL && !transaction.relatedTransaction.isNullOrEmpty()) {
                reversalTransactions.add(transaction.relatedTransaction)
            }
        }
    }

    fun getAllTransactions(): List<Transaction> = transactions.toList()

    /**
     * @return Pair<Int, Double> where first element is the total number of transactions
     * second element is and the average transaction value for a specific merchant in a specific date range
     */
    fun analyze(
        fromDate: String,
        toDate: String,
        merchant: String
    ): Pair<Int, Double> {
        val parsedFromDate = TransactionUtils.parseDateTime(fromDate)
        val parsedToDate = TransactionUtils.parseDateTime(toDate)

        val result = mutableListOf<Transaction>()
        val merchantTrans: List<Transaction> = merchant2Transactions[merchant] ?: listOf()
        for (transaction in merchantTrans) {
            if (transaction.date.isAfter(parsedToDate)) {
                // we stop iterating to improve performance on big data sets because
                // according to the task - for the sake of simplicity,
                // we can assume that Transaction records are listed in correct time order.
                // It means there will no be suitable transactions after toDate and cycle can be stopped
                break
            }

            if (transaction.type == TransactionType.PAYMENT &&
                !reversalTransactions.contains(transaction.id) &&
                !transaction.date.isBefore(parsedFromDate)
            ) {
                result.add(transaction)
            }
        }

        return Pair(
            // total amount
            result.size,

            // average transactions value
            if (result.isNotEmpty()) {
                result.map { it.amount }.average()
            } else {
                0.0
            }
        )
    }
}

/**
 * This main class can be used to run TransactionAnalyzer in production (from Docker container or simply java -jar ...)
 * List of arguments:
 *      0 - pathname to transactions file
 *      1 - fromDate
 *      2 - toDate
 *      3 - merchant
 * Expected result will be printed in console with prefixes:
 *      Number of transactions
 *      Average Transaction Value
 */
fun main(args: Array<String>) {
    if (args.size != 4) {
        throw IllegalArgumentException("Your args size ${args.size} is different from expected size = 4")
    }
    val result = TransactionAnalyzer(File(args[0])).analyze(args[1], args[2], args[3])
    logger.info { "Number of transactions = ${result.first}" }
    logger.info { "Average Transaction Value = ${result.second}" }
}
