# TrustMe
TrustMe is a lightweight library that helps you with creating `SSLSocketFactory` instances
for your custom CA, so that your restrictive network clients can authenticate SSL requests
against a self-signed server.

## Download

Add **one of the two** library dependencies based on your use case:

```groovy
dependencies {
	compile "de.mannodermaus.trustme:trustme-jre:1.0.0"
	compile "de.mannodermaus.trustme:trustme-android:1.0.0"
}
```

## Usage

### JRE

TrustMe provides the interface to custom `SSLSocketFactory` instances:

```kotlin
// From a File
val myCaFile = File("certs/selfsigned/my_ca.cer")
val sslFactory = TrustMe.file(myCaFile)
```

```kotlin
// From a ByteArray
val myCaBytes = …
val sslFactory = TrustMe.bytes(myCaBytes)
```

```kotlin
// From any InputStream (will propagate errors during creation)
val myCaInputStreeam = …
val sslFactory = TrustMe.inputStream(myCaInputStream)
```

### Android

`trustme-android` provides an additional method of retrieving an SSLSocketFactory,
which is using a raw resource file:

```kotlin
@RawRes val myCa = R.raw.my_ca
val context = getActivity()
val sslFactory = TrustMeAndroid.resource(context, myCa)
```

## License

	Copyright 2017 Marcel Schnelle

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
