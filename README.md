This is a Kotlin Multiplatform project targeting Android, iOS, Desktop (JVM).
<img width="1500" height="909" alt="bundle-transparent" src="https://github.com/user-attachments/assets/4e11bffd-839c-4eae-9d60-5802fa0757f3" />

<img width="2718" height="2316" alt="mobile-screens" src="https://github.com/user-attachments/assets/55ec2600-9ecb-4d5d-a8ad-1650dfe2dc17" />

<img width="2984" height="2304" alt="tablet-chat" src="https://github.com/user-attachments/assets/df54ebfd-e7d7-4f14-9841-2abe75c630b3" />

<img src="https://github.com/user-attachments/assets/d129ba8b-a8ee-4763-86e1-a2639dc7ccad" 
     alt="in-collaboration-with-jetbrains" 
     width="200" 
     height="50" />

<img src="https://github.com/user-attachments/assets/dec2400b-4f4b-442f-ab0c-425510db847c" 
     alt="kmp-cmp" 
     width="150" 
     height="50" />

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
