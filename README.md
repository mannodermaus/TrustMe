# TrustMe
TrustMe is a lightweight library that helps you with creating `SSLSocketFactory` instances for your custom CA, so that your restrictive network clients can authenticate SSL requests against a self-signed server.

## Download

No jcenter yet; sorry!

```groovy
buildscript {
	repositories {
        maven { url "https://dl.bintray.com/aurae/maven" }
    }
}
```

Add **one of the two** library dependencies based on your use case:

```groovy
dependencies {
	compile "com.github.aurae.trustme:trustme-java:0.2.0"
	compile "com.github.aurae.trustme:trustme-android:0.2.0"
}
```

## Usage

### Java

TrustMe provides the interface to custom `SSLSocketFactory` instances:

```java
// Grab your CA file handle
File myCaFile = new File("certs/selfsigned/my_ca.cer");

// Create the SSLSocketFactory
SSLSocketFactory sslFactory = TrustMe.newSSLSocketFactory(myCaFile);
```

```java
// Get the CA's content bytes (from a URL, for instance)
byte[] myCaBytes = ...;

// Create the SSLSocketFactory
SSLSocketFactory sslFactory = TrustMe.newSSLSocketFactory(myCaBytes);
```

### Android

`trustme-android` provides an additional method of retrieving an SSLSocketFactory, which is using a raw resource file:

```java
// Get the CA's raw resource ID and a context
@RawRes int myCa = R.raw.my_ca;
Context context = getActivity();

// Create the SSLSocketFactory
SSLSocketFactory sslFactory = TrustMeAndroid.newSSLSocketFactory(context, myCa);
```

## License

	Copyright 2015 Marcel Schnelle

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
