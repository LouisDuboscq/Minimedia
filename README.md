Minimedia is a small demo application of a media. 
The application knows how to retrieve articles and videos from a URL, 
store information to manage an offline mode, display data, 
play videos and read article details.

![app](art/img.png)

# Tech

- Kotlin
- Kotlin multiplatform library
- MVVM Architecture 
- Jetpack Compose
- Jetpack Compose navigation
- ExoPlayer
- Junit
- Ktor
- SQLDelight
- Coil

# Tests

## Jetpack Compose tests

[HomeScreenTest](androidApp/src/androidTest/kotlin/HomeScreenTest.kt)
[StoryDetailScreenTest](androidApp/src/androidTest/kotlin/StoryDetailScreenTest.kt)
[StoryItemTest](androidApp/src/androidTest/kotlin/StoryItemTest.kt)
[VideoItemTest](androidApp/src/androidTest/kotlin/VideoItemTest.kt)

## Unit tests

[GetMediasTest](shared/src/commonTest/kotlin/com/lduboscq/minimedia/GetMediasTest.kt)
[MediaRepositoryTest](shared/src/commonTest/kotlin/com/lduboscq/minimedia/MediaRepositoryTest.kt)
