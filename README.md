# Cryptocurrency Transaction Viewer

An Android app that displays real-time Bitcoin and Tezos blockchain transactions with a clean, modern UI built using Jetpack Compose.

## Features

- View transactions from the latest Bitcoin block
- Display transaction details (hash, amount, time, fees)
- User authentication flow
- Dark/light theme support
- Offline caching for transaction data

## Technologies

- **UI**: Jetpack Compose with Material 3 design
- **Architecture**: Clean Architecture with MVVM pattern
- **Dependency Injection**: Hilt
- **Networking**: Retrofit with OkHttp
- **Local Storage**: Room Database
- **Concurrency**: Kotlin Coroutines and Flow
- **Data Serialization**: Gson
- **Testing**: JUnit, Mockito, and Compose UI testing

## Architecture

The app follows Clean Architecture principles with three main layers:

### Domain Layer
- Contains business logic, entities, and use cases
- Independent of any frameworks

### Data Layer
- Implements repository interfaces from the domain layer
- Manages remote data sources (API) and local caching (Room)

### Presentation Layer
- Implements the UI using Jetpack Compose
- Uses ViewModels to handle UI state and business logic

## API Integration

The app connects to:
- [blockchain.info](https://blockchain.info/api) for Bitcoin blockchain data
- [tzkt.io](https://api.tzkt.io) for Tezos blockchain data

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or newer
- Android SDK 31 or higher

### Installation
1. Clone the repository:
```
git clone https://github.com/dancrux/Crypto-transaction-viewer.git
```

2. Open the project in Android Studio

3. Build and run the app on an emulator or device

## License


## Acknowledgments

- Blockchain.info for their free API
- TzKT for their Tezos API
