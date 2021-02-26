package hoolah.yolkin.challenge

import java.time.LocalDateTime

/**
 * A transaction record will contain the following fields:
 *  ID - A string representing the transaction id.
 *  Date - The date and time when the transaction took place (format "DD/MM/YYYY hh:mm:ss").
 *  Amount - The value of the transaction (dollars and cents).
 *  Merchant - The name of the merchant this transaction belongs to.
 *  Type - The type of the transaction, which could be either PAYMENT or REVERSAL.
 *  Related Transaction - (Optional) - In the case of a REVERSAL transaction,
 *  this field will contain the ID of the transaction it is reversing.
 */
data class Transaction(
    val id: String,
    val date: LocalDateTime,
    val amount: Double,
    val merchant: String,
    val type: TransactionType,
    val relatedTransaction: String? = null
)
