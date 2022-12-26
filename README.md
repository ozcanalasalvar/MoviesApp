# MoviesApp
![build](https://github.com/OzcanAlasalvar/MoviesApp/actions/workflows/android_build.yml/badge.svg)
<br>
Movie app that uses modern android components and MVVM architecture.
<br>

<p align="center">
  <img src="https://github.com/OzcanAlasalvar/MoviesApp/blob/main/art/home.png" width="350">
  <img src="https://github.com/OzcanAlasalvar/MoviesApp/blob/main/art/detail.png" width="350">
</p>


<br>


### Libraries

- Application entirely written in [Kotlin](https://kotlinlang.org)
- Asynchronous processing using [Coroutines](https://kotlin.github.io/kotlinx.coroutines/)
- Uses [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection
- Uses [Github Actions](https://docs.github.com/en/actions/learn-github-actions)
- Uses [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
- Uses [JUnit4](https://developer.android.com/training/testing/junit-rules), [Espresso](https://developer.android.com/training/testing/espresso), [Fragment Tests](https://developer.android.com/guide/fragments/test) among other libraries for unit & instrumented tests.

<br>

> **_NOTE:_** You can check [Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAiAhqCdBhB0EiwAH8M_GrMEjOEbwExgFLdJeDkdX78_DMaJ7G2tOnKxX5jO4l3SPqZF38oFkRoCyb8QAvD_BwE&gclsrc=aw.ds) implementation of app from [here](https://github.com/OzcanAlasalvar/MoviesApp/tree/compose-main)


### API keys

You need to supply API / client keys for the service the app uses.

- [TMDb](https://developers.themoviedb.org)

Once you obtain the key, you can set them in your `~/local.properties`:

```
# Get this from TMDb
API_KEY=<insert>
```
