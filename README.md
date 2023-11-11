# Base-MVVM-Android 

## With base_1 
> Tech 
  - MMVM clean architecture
  - DataBinding
  - Coroutine
  - Epoxy/ListAdapter
  - Navigation
> Lib
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
  - [`Glide (Caching image)`](https://github.com/bumptech/glide)
- Navigation
  - [`Navigation Component (Screen navigation)`](https://developer.android.com/guide/navigation/get-started)
- RecyclerView Multiple View Type
  - [`Epoxy Airbnb (RecyclerView)`](https://github.com/airbnb/epoxy)
  - [`ListAdapter (RecyclerView)`](https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter)
> Tree
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
