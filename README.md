# Base-MVVM-Android 

## :skull: With base_1 in from branch base_1 (Recommended use :boom:)
>:pushpin: Technical overview 
  - MMVM clean architecture
  - DataBinding
  - Coroutine
  - Objectbox - MMKV
  - Koin
  - Epoxy/ListAdapter
  - Navigation
>:pushpin: Specific library used
- Asynchronous programming
  - [`Corountine`](https://kotlinlang.org/docs/coroutines-basics.html)
- Network
  - [`Retrofit (Http client + API Declaration)`](https://square.github.io/retrofit/)
  - [`Gson (Parse Json)`](https://github.com/google/gson)
  - [`Okhttp (Client-Handling-Logging)`](https://square.github.io/okhttp/)
- Local Storage
  - [`ObjectBox (NoSQL-Database)`](https://docs.objectbox.io/android)
  - [`MMKV (Storage key-value)`](https://github.com/Tencent/MMKV)
- Dependency Injection
  - [`Koin (Inject component)`](https://insert-koin.io/)
- Debug
  - [`ObjectBox-Admin (View Database)`](https://docs.objectbox.io/data-browser)
  - [`Timber (Logging)`](https://github.com/JakeWharton/timber)
- Dimension
  - [`SDP (A scalable size unit)`](https://github.com/intuit/sdp)
- Load Image
  - [`Glide (Load & Caching image)`](https://github.com/bumptech/glide)
- Navigation
  - [`Navigation Component (Screen navigation)`](https://developer.android.com/guide/navigation/get-started)
- RecyclerView Multiple View Type
  - [`Epoxy Airbnb (RecyclerView)`](https://github.com/airbnb/epoxy)
  - [`ListAdapter (RecyclerView)`](https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter)
>:pushpin: Tree
```
src
|───base
│   ├── config
│   ├── view
│   ├── extension
│   └── BaseComponent(Activity, Navigation, Fragment,Adapter, Log...)
|
|───app
│   ├── presentation
│       └── home
│           ├── HomeFragment.kt
│           ├── HomeNavigation.kt
│           └── HomeViewModel.kt
│   ├── use_case
│   ├── data
│       ├── model
│       ├── repository
│       └── data_source
│           ├── local
│           └── remote
│   ├── utils
│       ├── constant
│       ├── extension
│       └── util
│   └── di (ViewModelModule, DatabaseModule, NetworkModule...)
└── MyApplication.kt

```


## :skull: With base 2 in from branch base_2
>:pushpin: Technical overview 
  - The technologies are the same as base_1 except for the network layer 
>:pushpin: Specific library used
- Network (From Jetbrain Lib)
  - [`Ktor (Client + Logging + Manager Url)`](https://ktor.io/docs/getting-started-ktor-client.html)
  - [`kotlinx.serialization (Parse Json)`](https://github.com/Kotlin/kotlinx.serialization)


## :skull: With base 3 in from branch base_3
>:pushpin: Technical overview 
  - MMVM clean architecture
  - ViewBinding
  - RxAndroid - RxJava/RxKotlin - RxBinding
  - Room - SharedPrefercens
  - Koin
  - ListAdapter
  - Navigation
>:pushpin: Specific library used
- Asynchronous programming
  - [`RxJava`](https://github.com/ReactiveX/RxJava)
  - [`RxKotlin`](https://github.com/ReactiveX/RxKotlin)
  - [`RxAndroid`](https://github.com/ReactiveX/RxAndroid)
  - [`RxBinding - (Input - Validate form) `](https://github.com/JakeWharton/RxBinding)
- Network
  - [`Retrofit (Http client + API Declaration)`](https://square.github.io/retrofit/)
  - [`Gson (Parse Json)`](https://github.com/google/gson)
  - [`Okhttp (Client-Handling-Logging)`](https://square.github.io/okhttp/)
- Local Storage
  - [`Room (Abstraction layer over SQLite)`](https://developer.android.com/training/data-storage/room)
  - [`SharedPreferences (Storage key-value)`](https://developer.android.com/reference/android/content/SharedPreferences)
- Dependency Injection
  - [`Koin (Inject component)`](https://insert-koin.io/)
- Load Image
  - [`Picasso (Load & Caching image)`](https://square.github.io/picasso/)
- Navigation
  - [`Navigation Component (Screen navigation)`](https://developer.android.com/guide/navigation/get-started)
- RecyclerView
  - [`ListAdapter (RecyclerView)`](https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter)
- Firebase
  - [`Firebase Cloud Message (Receiver message from server )`](https://firebase.google.com/docs/cloud-messaging)


<p align="center">
---- From Tiểu Vy :hearts: ----
</p>

