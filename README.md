# OmiseGO Android SDK

The [OmiseGO](https://omisego.network) Android SDK allows developers to easily interact with a node of the OmiseGO eWallet.


# Table of Contents

- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
  - [Initialization](#initialization)
  - [Retrieving resources](#retrieving-resources)
    - [Get the current user](#get-the-current-user)
    - [Get the addresses of the current user](#get-the-addresses-of-the-current-user)
    - [Get the provider settings](#get-the-provider-settings)
- [Test](#test)
- [Contributing](#contributing)
- [License](#license)

## Requirements

- Minimum Android SDK version 19

## Installation

To use the OmiseGO SDK in your android project, simply add the following line in the module's build.gradle
 
```groovy
dependencies {
    implementation 'co.omisego:omisego-sdk:0.8.0'
}
```

## Usage

### Initialization

Before using the SDK to retrieve a resource, you need to initialize the client (`OMGApiClient`) with a builder (`OMGApiClient.builder`).
You should to this as soon as you obtain a valid authentication token corresponding to the current user from the Wallet API.

```kotlin
 val token = EncryptionHelper.encryptBase64(apiKey,authToken)
 
 val eWalletClient = EWalletClient.Builder {
     baseURL = baseURL
     authenticationToken = token
     debug = true // Print request and response log
 }.build()
 
 val omgAPIClient = OMGApiClient(eWalletClient)
```

Where:
`apiKey` is the api key generated from your OmiseGO admin panel.
`authenticationToken` is the token corresponding to an OmiseGO Wallet user retrievable using one of our server-side SDKs.
`baseURL` is the URL of the OmiseGO Wallet API.
> You can find more info on how to retrieve this token in the OmiseGO server SDK documentations.

### Retrieving resources

Once you have an initialized client, you can retrieve different resources.
Each call takes a `Callback` interface that returns a `OMGResponse` object:

```kotlin
interface Callback<in T> {
    fun success(response: OMGResponse<T>)
    fun fail(response: OMGResponse<ApiError>)
}
```

```kotlin
data class OMGResponse<out T>(val version: String, val success: Boolean, val data: T)

data class ApiError(val code: ErrorCode, val description: String)
```

Where:

`success` is the function invoked when the `success` boolean in the response is `true`. This function will provide the corresponding data model to an API endpoint.

`fail` is the function invoked when the `success` boolean in the response is `false`. This function will provide the `ApiError` object which contains informations about the failure.

### Get the current user

```kotlin
omgAPIClient.getCurrentUser(object : Callback<User> {
     override fun success(response: OMGResponse<User>) {
         // Do something with the user
     }

     override fun fail(response: OMGResponse<ApiError>) {
         // Handle the error
     }
})
```

### Get the addresses of the current user

```kotlin
omgAPIClient.listBalances(object : Callback<BalanceList> {
     override fun success(response: OMGResponse<BalanceList>) {
         // Do something with the address
     }

     override fun fail(response: OMGResponse<ApiError>) {
         // Handle the error
     }
})
```

> Note: For now a user will have only one address.

### Get the provider settings

```kotlin
omgAPIClient.getSettings(object : Callback<Setting> {
     override fun success(response: OMGResponse<Setting>) {
         // Do something with the setting
     }

     override fun fail(response: OMGResponse<ApiError>) {
         // Handle the error
     }
})
```

## Test
In order to run the live tests (bound to a working server) you need to fill the corresponding in the file `src/test/resources/secret.json`. 
> Note : You can see the reference in the file `secret.example.json`

The variables are:

* `base_url`
* `api_key`
* `auth_token`
* `access_key`
* `secret_key`

You can then run the test under the `src/test` folder from the Android Studio or run the command `./gradlew test`.

# Contributing

See [how you can help](.github/CONTRIBUTING.md).

## License

The OmiseGO Android SDK is released under the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).

## Sample Project

You can check out the latest sample app from the following repo : [OMGShop](https://github.com/omisego/sample-android)