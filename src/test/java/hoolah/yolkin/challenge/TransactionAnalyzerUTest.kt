package hoolah.yolkin.challenge

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File

private const val DEFAULT_TRANSACTIONS_FILE_NAME = "transactions.csv"

internal class TransactionAnalyzerUTest {

    @ParameterizedTest
    @MethodSource("provideData")
    fun testAnalyze(
        file: File,
        expectedTransactionsCount: Int,
        fromDate: String,
        toDate: String,
        merchant: String,

        // expected results
        expectedTransactions: Int,
        avgTransactionsValue: Double
    ) {
        val analyzer = TransactionAnalyzer(file = file)
        assertEquals(expectedTransactionsCount, analyzer.getAllTransactions().size)

        val result = analyzer.analyze(fromDate, toDate, merchant)
        assertEquals(expectedTransactions, result.first)
        assertEquals(avgTransactionsValue, result.second, 0.00001) // use delta to exclude potential rounding errors
    }

    companion object {
        private fun getResourceAsFile(fileName: String): File {
            return File(
                requireNotNull(ClassLoader.getSystemClassLoader().getResource(fileName)?.toURI()) {
                    "File $fileName can't be accessible"
                }
            )
        }

        @JvmStatic
        private fun provideData() = listOf(
            // default scenario from task definition
            Arguments.of(
                getResourceAsFile(DEFAULT_TRANSACTIONS_FILE_NAME), // csv filename
                6, // overall total amount
                "20/08/2020 12:00:00", // fromDate
                "20/08/2020 13:00:00", // toDate
                "Kwik-E-Mart", // merchant
                1, // Number of transactions
                59.99 // Average Transaction Value
            ),

            // scenario when illegal merchant
            Arguments.of(
                getResourceAsFile(DEFAULT_TRANSACTIONS_FILE_NAME), // csv filename
                6, // overall total amount
                "20/08/2020 12:00:00", // fromDate
                "20/08/2020 13:00:00", // toDate
                "Illegal merchant", // merchant
                0, // Number of transactions
                0 // Average Transaction Value
            ),

            // scenario when illegal fromDate/toDate
            Arguments.of(
                getResourceAsFile(DEFAULT_TRANSACTIONS_FILE_NAME), // csv filename
                6, // overall total amount
                "21/08/2020 12:00:00", // fromDate
                "21/08/2020 13:00:00", // toDate
                "Kwik-E-Mart", // merchant
                0, // Number of transactions
                0 // Average Transaction Value
            ),

            // all possible cases for Kwik-E-Mart
            Arguments.of(
                getResourceAsFile(DEFAULT_TRANSACTIONS_FILE_NAME), // csv filename
                6, // overall total amount
                "20/08/2020 12:00:00", // fromDate
                "20/08/2020 13:30:00", // toDate
                "Kwik-E-Mart", // merchant
                2, // Number of transactions
                32.495 // Average Transaction Value
            ),

            // default scenario from task definition + border dates
            Arguments.of(
                getResourceAsFile(DEFAULT_TRANSACTIONS_FILE_NAME), // csv filename
                6, // overall total amount
                "20/08/2020 12:45:33", // fromDate
                "20/08/2020 12:46:17", // toDate
                "Kwik-E-Mart", // merchant
                1, // Number of transactions
                59.99 // Average Transaction Value
            ),

            // all transactions of MacLaren
            Arguments.of(
                getResourceAsFile(DEFAULT_TRANSACTIONS_FILE_NAME), // csv filename
                6, // overall total amount
                "20/08/2020 12:00:00", // fromDate
                "20/08/2020 15:00:00", // toDate
                "MacLaren", // merchant
                2, // Number of transactions
                52.25 // Average Transaction Value
            )

        )
    }
}
