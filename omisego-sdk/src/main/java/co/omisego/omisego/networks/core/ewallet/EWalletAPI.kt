package co.omisego.omisego.networks.core.ewallet

import co.omisego.omisego.models.Address
import co.omisego.omisego.models.Setting
import co.omisego.omisego.models.User
import co.omisego.omisego.constants.Endpoints.GET_CURRENT_USER
import co.omisego.omisego.constants.Endpoints.GET_SETTINGS
import co.omisego.omisego.constants.Endpoints.LIST_BALANCE
import co.omisego.omisego.constants.Endpoints.LOGOUT
import co.omisego.omisego.models.Response
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.POST


/**
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 5/3/2018 AD.
 * Copyright © 2017 OmiseGO. All rights reserved.
 */

interface EWalletAPI {
    @POST(GET_CURRENT_USER)
    fun getCurrentUser(): Call<JsonElement>

    @POST(LOGOUT)
    fun logout(): Call<JsonElement>

    @POST(LIST_BALANCE)
    fun listBalance(): Call<JsonElement>

    @POST(GET_SETTINGS)
    fun getSettings(): Call<JsonElement>
}