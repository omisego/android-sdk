package co.omisego.omisego.model.transaction

/*
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 29/6/2018 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */

import co.omisego.omisego.extension.bd
import co.omisego.omisego.helpers.delegation.ResourceFile
import co.omisego.omisego.testUtils.DateConverter
import co.omisego.omisego.utils.GsonProvider
import com.google.gson.Gson
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Test

class TransactionExchangeTest {
    private val gson: Gson by lazy { GsonProvider.create() }
    private val dateConverter by lazy { DateConverter() }
    private val transactionExchangeFile by ResourceFile("transaction_exchange.json", "object")

    @Test
    fun `test transaction_exchange parsing`() {
        val exchange = gson.fromJson(transactionExchangeFile.readText(), TransactionExchange::class.java)
        with(exchange) {
            calculatedAt shouldEqual dateConverter.fromString("2018-01-01T00:00:00Z")
            rate shouldEqual 0.017.bd
            exchangePairId shouldEqual "exg_01cgvppyrz2pprj6s0zmc26p2p"
            exchangePair shouldBeInstanceOf ExchangePair::class.java
        }
    }
}
