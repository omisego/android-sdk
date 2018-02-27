# OmiseGO Android SDK

The [OmiseGO](https://omisego.network) Android SDK allows developers to easily interact with a node of the OmiseGO eWallet.
It supports the following functionalities:

- Retrieve the current user
- Get the user addresses and balances
- List the settings for a node

## Requirements

- Minimum Android SDK version 19

- Kotlin version 1.2.21

## Installation

Since we are not hosting the packaged SDK yet, you should add the library manually following these steps:

1. Create `libs` folder in your app module.

2. Copy .aar file from the `build/outputs/aar` folder and paste in the `libs` folder.

3. Add the following code to your `app/build.gradle`, since we're using kotlin coroutines to handle thread.

```groovy
kotlin {
    experimental {
        coroutines 'enable'
    }
}
```

```groovy
dependencies {
    // ... your dependencies
    implementation(name: 'androidsdk-release', ext: 'aar')
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:0.19.3'
}
```

4. Sync the gradle

You're done.

## Usage

### Initialization

Before using the SDK to retrieve a resource, you need to initialize the client (`OMGApiClient`) with a builder (`OMGApiClient.builder`).
You should to this as soon as you obtain a valid authentication token corresponding to the current user from the Wallet API.

```kotlin
 val token = Base64.encode("$api_key:$authentication_token")
 val omgApiClient = OMGApiClient.Builder {
      setAuthorizationToken(token)
      setBaseURL(baseURL)
 }.build()
```

Where:
`apiKey` is the api key generated from your OmiseGO admin panel.
`authenticationToken` is the token corresponding to an OmiseGO Wallet user retrievable using one of our server-side SDKs.
`baseURL` is the URL of the OmiseGO Wallet API.
> You can find more info on how to retrieve this token in the OmiseGO server SDK documentations.

### Retrieving resources

Once you have an initialized client, you can retrieve different resources.
Each call takes a `Callback` interface that returns a `Response` object:

```kotlin
interface Callback<in T> {
    fun success(response: Response<T>)
    fun fail(response: Response<ApiError>)
}
```

```kotlin
data class Response<out T>(val version: String, val success: Boolean, val data: T)

data class ApiError(val code: ErrorCode, val description: String)
```

Where:

`success` is the function invoked when the `success` boolean in the response is `true`. This function will provide the corresponding data model to an API endpoint.

`fail` is the function invoked when the `success` boolean in the response is `false`. This function will provide the `ApiError` object which contains informations about the failure.

### Get the current user

```kotlin
omgApiClient.getCurrentUser(object : Callback<User> {
     override fun success(response: Response<User>) {
         // Do something with the user
     }

     override fun fail(response: Response<ApiError>) {
         // Handle the error
     }
})
```

### Get the addresses of the current user

```kotlin
omgApiClient.listBalances(object : Callback<List<Address>> {
     override fun success(response: Response<List<Address>>) {
         // Do something with the address
     }

     override fun fail(response: Response<ApiError>) {
         // Handle the error
     }
})
```

> Note: For now a user will have only one address.

### Get the provider settings

```kotlin
omgApiClient.getSettings(object : Callback<Setting> {
     override fun success(response: Response<Setting>) {
         // Do something with the setting
     }

     override fun fail(response: Response<ApiError>) {
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

You can then run the test under the `src/test` folder from the Android Studio.

## License

OmiseGO is released under the Apache license. See `LICENSE` for details.

## Sample Project

You can check out the latest sample app from the following repo : [OMGShop](https://github.com/omisego/sample-android)
