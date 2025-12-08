# RMSJIMS - Resource Management System for JIMS

A comprehensive Android application built with Kotlin and Jetpack Compose for managing resources, equipment, bookings, and maintenance requests in an educational institution. The app features role-based access control, real-time data synchronization with Supabase, and a responsive UI that adapts to different screen sizes.

## 📱 Overview

RMSJIMS is a Resource Management System designed for JIMS (Jagan Institute of Management Studies) to efficiently manage:
- **Buildings & Infrastructure**: Track multiple buildings and their details
- **Departments**: Organize departments within buildings
- **Rooms**: Manage room allocations and status
- **Equipment**: Track equipment inventory, availability, and bookings
- **Tickets**: Handle maintenance requests and support tickets
- **Bookings**: Manage equipment booking requests with approval workflow

## ✨ Features

### Core Features
- **Role-Based Access Control**: Three user roles (Admin, Staff, Assistant) with different permissions
- **Real-Time Data Sync**: Supabase integration for live data updates
- **Equipment Management**: Complete CRUD operations for equipment tracking
- **Booking System**: Request, approve, and manage equipment bookings
- **Ticket Management**: Create and track maintenance/support tickets
- **Department Management**: Organize resources by departments and buildings
- **Responsive Design**: Adaptive UI for phones, tablets, and different orientations

### User Roles

#### 👨‍💼 Admin
- Full access to all features
- Add/edit buildings and equipment
- Manage booking approvals
- View all tickets and maintenance requests
- User management capabilities

#### 👨‍🏫 Staff
- View buildings containing departments
- View departments containing equipment
- Create booking requests
- View own bookings and their status
- Create tickets for maintenance

#### 👨‍🔧 Assistant
- Similar permissions to Staff
- View available equipment
- Create booking requests
- Assist with maintenance tasks

## 🛠️ Tech Stack

### Frontend
- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern declarative UI framework
- **Material Design 3**: Material You design system
- **Navigation Compose**: Type-safe navigation
- **Koin**: Dependency injection framework
- **Coroutines & Flow**: Asynchronous programming

### Backend & Database
- **Supabase**: Backend-as-a-Service (BaaS)
  - PostgreSQL database
  - Real-time subscriptions
  - REST API
- **PostgREST**: Auto-generated REST API

### Libraries & Tools
- **Kotlinx Serialization**: JSON serialization/deserialization
- **Ktor Client**: HTTP client for API calls
- **Coil**: Image loading library
- **Accompanist**: Additional Compose utilities

## 🏗️ Architecture

The app follows **MVVM (Model-View-ViewModel)** architecture pattern:

```
┌─────────────────┐
│   UI Layer      │  ← Jetpack Compose Screens
│  (Compose UI)   │
└────────┬────────┘
         │
┌────────▼────────┐
│  ViewModel      │  ← State management, business logic
│   (StateFlow)   │
└────────┬────────┘
         │
┌────────▼────────┐
│  Repository     │  ← Data abstraction layer
└────────┬────────┘
         │
┌────────▼────────┐
│  API Service    │  ← Supabase API calls
└────────┬────────┘
         │
┌────────▼────────┐
│   Supabase      │  ← PostgreSQL database
└─────────────────┘
```

### Key Components

1. **Data Models** (`data/schema/`): Kotlin data classes representing database tables
2. **API Services** (`data/remote/apiservice/`): Interfaces defining API contracts
3. **API Implementations** (`data/remote/api/`): Supabase client implementations
4. **Repositories** (`repository/`): Abstract data sources, provide methods to ViewModels
5. **ViewModels** (`viewmodel/`): Manage UI state, expose `StateFlow<UiState<T>>`
6. **UI Screens** (`ui/screens/`): Jetpack Compose screens organized by role
7. **Components** (`ui/components/`): Reusable Compose components

## 📊 Database Schema

### Tables

#### `buildings`
- `id` (SERIAL PRIMARY KEY)
- `building_name` (VARCHAR)
- `building_number` (VARCHAR)
- `created_at`, `updated_at` (TIMESTAMP)

#### `departments`
- `id` (SERIAL PRIMARY KEY)
- `department_name` (VARCHAR)
- `address` (TEXT)
- `building_id` (INT, FK to buildings)
- `created_at`, `updated_at` (TIMESTAMP)

#### `rooms`
- `id` (SERIAL PRIMARY KEY)
- `room_name` (VARCHAR)
- `room_number` (VARCHAR)
- `location` (VARCHAR)
- `capacity` (INT)
- `status` (VARCHAR)
- `building_id` (INT, FK to buildings)
- `department_id` (INT, FK to departments)
- `created_at`, `updated_at` (TIMESTAMP)

#### `equipment`
- `id` (SERIAL PRIMARY KEY)
- `name` (VARCHAR)
- `image` (TEXT)
- `location` (VARCHAR)
- `request_status` (VARCHAR)
- `request_urgency` (VARCHAR)
- `incharge_id`, `incharge_name`, `incharge_designation`, `incharge_email`, `incharge_phone`
- `booking_date` (DATE)
- `department_id` (INT, FK to departments)
- `building_id` (INT, FK to buildings)
- `created_at`, `updated_at` (TIMESTAMP)

#### `tickets`
- `id` (SERIAL PRIMARY KEY)
- `title` (VARCHAR)
- `description` (TEXT)
- `status` (VARCHAR)
- `priority` (VARCHAR)
- `equipment_id` (INT, FK to equipment)
- `department_id` (INT, FK to departments)
- `created_by` (INT)
- `created_at`, `updated_at` (TIMESTAMP)

#### `bookings`
- `id` (SERIAL PRIMARY KEY)
- `user_id` (INT, FK to users)
- `equipment_id` (INT, FK to equipment)
- `booker_name` (VARCHAR) - Name of person making booking
- `product_name` (VARCHAR) - Equipment name
- `product_description` (TEXT) - Equipment description
- `booking_date` (DATE) - Date booking was made
- `project_name` (VARCHAR)
- `guide_name` (VARCHAR)
- `project_description` (TEXT)
- `branch` (VARCHAR)
- `department` (VARCHAR)
- `team_members` (TEXT) - Comma-separated
- `start_date`, `end_date` (DATE)
- `status` (VARCHAR) - pending, approved, rejected
- `admin_notes` (TEXT)
- `rejection_reason` (TEXT)
- `approved_by` (INT, FK to users)
- `approved_at` (TIMESTAMP)
- `created_at`, `updated_at` (TIMESTAMP)

## 🚀 Getting Started

### Prerequisites

- **Android Studio**: Hedgehog (2023.1.1) or later
- **JDK**: 11 or higher
- **Android SDK**: Minimum SDK 24, Target SDK 35
- **Supabase Account**: For backend services
- **Git**: For version control

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd RMSJIMS
   ```

2. **Configure Supabase**
   - Create a Supabase project at [supabase.com](https://supabase.com)
   - Note your project URL and anon key

3. **Set up local.properties**
   Create or edit `local.properties` in the root directory:
   ```properties
   SUPABASE_URL=https://your-project.supabase.co
   SUPABASE_ANON_KEY=your-anon-key-here
   ```

4. **Set up Database**
   - Open Supabase SQL Editor
   - Run `SEED_DEMO_DATA.sql` to create tables and insert demo data
   - Run `CREATE_BOOKINGS_TABLE_COMPLETE.sql` to create bookings table

5. **Build and Run**
   ```bash
   ./gradlew build
   ```
   Or open the project in Android Studio and click "Run"

### Project Structure

```
RMSJIMS/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/rmsjims/
│   │   │   ├── data/
│   │   │   │   ├── model/          # UI models, enums, state classes
│   │   │   │   ├── schema/         # Database schema classes
│   │   │   │   ├── remote/        # API services and implementations
│   │   │   │   └── local/         # Local storage managers
│   │   │   ├── repository/        # Data repositories
│   │   │   ├── viewmodel/         # ViewModels for each feature
│   │   │   ├── ui/
│   │   │   │   ├── screens/       # Compose screens (admin, staff, assistant, shared)
│   │   │   │   ├── components/   # Reusable UI components
│   │   │   │   └── theme/         # App theme, colors, typography
│   │   │   ├── navigation/       # Navigation graphs
│   │   │   ├── di/               # Dependency injection modules
│   │   │   └── util/             # Utility functions
│   │   └── res/                  # Resources (drawables, layouts, etc.)
│   └── build.gradle.kts
├── SEED_DEMO_DATA.sql            # Database seed script
├── CREATE_BOOKINGS_TABLE_COMPLETE.sql
├── BOOKING_QUERIES.sql           # Useful booking queries
└── README.md
```

## ⚙️ Configuration

### Build Configuration

The app uses `BuildConfig` to inject Supabase credentials at build time:

```kotlin
// In app/build.gradle.kts
buildConfigField("String", "SUPABASE_URL", "\"$supabaseUrl\"")
buildConfigField("String", "SUPABASE_KEY", "\"$supabaseKey\"")
```

### Dependency Injection (Koin)

All dependencies are registered in `app/src/main/java/com/example/rmsjims/di/appModule.kt`:

```kotlin
val appModule = module {
    // API Services
    single<BuildingsApiService> { BuildingsApi(get()) }
    
    // Repositories
    single { BuildingsRepository(get()) }
    
    // ViewModels
    viewModel { BuildingsViewModel(get()) }
}
```

## 📖 Usage

### For Developers

#### Adding a New Feature

1. **Create Data Schema** (`data/schema/`)
   ```kotlin
   @Serializable
   data class NewEntity(
       val id: Int? = null,
       @SerialName("field_name") val fieldName: String
   )
   ```

2. **Create API Service** (`data/remote/apiservice/`)
   ```kotlin
   interface NewEntityApiService {
       suspend fun getAll(): List<NewEntity>
   }
   ```

3. **Implement API** (`data/remote/api/`)
   ```kotlin
   class NewEntityApi(private val supabaseClient: SupabaseClient) : NewEntityApiService {
       override suspend fun getAll(): List<NewEntity> {
           return supabaseClient.from("new_entity").select().decodeList()
       }
   }
   ```

4. **Create Repository** (`repository/`)
   ```kotlin
   class NewEntityRepository(private val api: NewEntityApiService) {
       suspend fun getAll(): Flow<List<NewEntity>> = flow {
           emit(api.getAll())
       }
   }
   ```

5. **Create ViewModel** (`viewmodel/`)
   ```kotlin
   class NewEntityViewModel(private val repository: NewEntityRepository) : ViewModel() {
       private val _state = MutableStateFlow<UiState<List<NewEntity>>>(UiState.Loading)
       val state: StateFlow<UiState<List<NewEntity>>> = _state.asStateFlow()
       
       init {
           loadData()
       }
       
       private fun loadData() {
           viewModelScope.launch {
               repository.getAll().collect { data ->
                   _state.value = UiState.Success(data)
               }
           }
       }
   }
   ```

6. **Create UI Screen** (`ui/screens/`)
   ```kotlin
   @Composable
   fun NewEntityScreen(navController: NavHostController) {
       val viewModel: NewEntityViewModel = koinViewModel()
       val state by viewModel.state.collectAsState()
       
       when (val uiState = state) {
           is UiState.Loading -> LoadingIndicator()
           is UiState.Success -> EntityList(uiState.data)
           is UiState.Error -> ErrorMessage(uiState.exception)
       }
   }
   ```

7. **Register in DI** (`di/appModule.kt`)
   ```kotlin
   single<NewEntityApiService> { NewEntityApi(get()) }
   single { NewEntityRepository(get()) }
   viewModel { NewEntityViewModel(get()) }
   ```

#### Role-Based Access Control

Use `UserRole` enum to check permissions:

```kotlin
val userRole = remember { UserRole.ADMIN } // Get from session

when (userRole) {
    UserRole.ADMIN -> {
        // Show admin-only features
    }
    UserRole.STAFF, UserRole.ASSISTANT -> {
        // Show staff/assistant features
    }
    else -> {}
}
```

### For Users

#### Admin
1. Login with admin credentials
2. Access admin dashboard
3. Manage buildings, equipment, bookings
4. Approve/reject booking requests
5. View all tickets and maintenance requests

#### Staff/Assistant
1. Login with staff/assistant credentials
2. Browse available equipment
3. Create booking requests
4. View booking status
5. Create maintenance tickets

## 🔐 Security

- **API Keys**: Stored in `local.properties` (not committed to version control)
- **Session Management**: Secure session handling with Supabase
- **Role-Based Access**: Enforced at both UI and API levels
- **Data Validation**: Input validation on all forms

## 🧪 Testing

### Running Tests

```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest
```

## 📝 Database Queries

### Useful Queries

See `BOOKING_QUERIES.sql` for sample queries including:
- Get pending bookings
- Get bookings by user
- Get bookings by status
- Update booking status

## 🐛 Troubleshooting

### Common Issues

1. **Build fails with "Unresolved reference"**
   - Sync Gradle files: `File > Sync Project with Gradle Files`
   - Invalidate caches: `File > Invalidate Caches / Restart`

2. **Supabase connection errors**
   - Verify `SUPABASE_URL` and `SUPABASE_ANON_KEY` in `local.properties`
   - Check Supabase project is active
   - Verify network permissions in `AndroidManifest.xml`

3. **Database errors**
   - Ensure all SQL scripts have been run
   - Check table names match schema classes
   - Verify foreign key relationships

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style

- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex logic
- Keep functions focused and small
- Use `UiState` sealed class for state management

## 📄 License

This project is proprietary software for JIMS (Jagan Institute of Management Studies).

## 👥 Authors

- Development Team - JIMS

## 🙏 Acknowledgments

- Supabase for backend infrastructure
- Jetpack Compose team for the amazing UI framework
- Material Design for design guidelines

## 📞 Support

For issues, questions, or contributions, please contact the development team.

---

**Version**: 1.0  
**Last Updated**: December 2024  
**Minimum Android Version**: 7.0 (API 24)  
**Target Android Version**: 15.0 (API 35)
