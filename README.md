# 📱 Android AIDL IPC Example
This project demonstrates **Inter-Process Communication (IPC)** in Android using **AIDL (Android Interface Definition Language)**.
It consists of two apps:
- Server App → Exposes a bound service via AIDL.
- Client App → Connects to the service and communicates across processes.

## Features
- AIDL interface for cross-app communication.
- Sending and receiving primitive types and custom objects.
- Example of sending a List of custom objects using Parcelable.
- Using coroutines in Service (suspend functions with CoroutineScope).
- Clean Architecture with UseCase and ViewModel separation.

## 🛠️ Tech Stack
- Kotlin
- Android AIDL
- Coroutines
- Room (for persistence)
- MVVM + UseCases

## 🎯 How to Run
1. Clone this repo.
2. Build and install the AIDL Server app.
3. Build and install the AIDL Client app.
4. Open client app → connect to service → interact with server.
