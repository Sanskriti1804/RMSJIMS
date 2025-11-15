# Debugging Guide: Empty Home and Equipment Screens

## Overview
This guide helps identify why the Home and Equipment screens show no data. Comprehensive logging has been added to trace the data flow from Supabase → API → Repository → ViewModel → UI.

## How to Use This Guide

1. **Run the app** and navigate to Home screen, then Equipment screen
2. **Open Logcat** in Android Studio (View → Tool Windows → Logcat)
3. **Filter logs** by these tags:
   - `RMSJimsApp` - App initialization
   - `SupabaseModule` - Supabase client creation
   - `ItemCategoriesVM` - Categories ViewModel
   - `ItemCategoriesApi` - Categories API calls
   - `ItemsViewModel` - Items ViewModel
   - `ItemsApi` - Items API calls

## Diagnostic Checklist

### Step 1: Verify Supabase Configuration

**Check logs for:**
```
RMSJimsApp: Supabase URL loaded: https://xxxxx.supabase.co...
RMSJimsApp: Supabase Key loaded: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**If you see:**
- `EMPTY` → **ROOT CAUSE**: Supabase credentials not set in `local.properties`
  - **FIX**: Add `SUPABASE_URL` and `SUPABASE_KEY` to `local.properties` and rebuild

**If you see:**
- `YOUR_SUPABASE_PROJECT_URL_HERE` → **ROOT CAUSE**: Placeholder values still in use
  - **FIX**: Replace placeholders with actual Supabase credentials

### Step 2: Verify Supabase Client Initialization

**Check logs for:**
```
SupabaseModule: Creating Supabase client...
SupabaseModule: Supabase client created successfully
```

**If you see:**
- `FAILED to create Supabase client` → **ROOT CAUSE**: Invalid URL or Key format
  - **FIX**: Verify URL starts with `https://` and key is the correct anon key

### Step 3: Verify Categories API Call

**Check logs for:**
```
ItemCategoriesVM: ViewModel initialized, fetching categories...
ItemCategoriesApi: Making Supabase query to 'item_categories' table...
ItemCategoriesApi: Supabase query successful, returned X categories
ItemCategoriesVM: Received X categories from repository
```

**Possible Issues:**

#### A. Exception in API Call
**If you see:**
```
ItemCategoriesApi: Supabase query FAILED
ItemCategoriesApi: Exception type: HttpRequestException
ItemCategoriesApi: Exception message: 404 Not Found
```
**ROOT CAUSE**: Table `item_categories` doesn't exist in Supabase
- **FIX**: Create the table in Supabase dashboard

**If you see:**
```
ItemCategoriesApi: Exception type: HttpRequestException
ItemCategoriesApi: Exception message: 401 Unauthorized
```
**ROOT CAUSE**: RLS (Row Level Security) blocking access
- **FIX**: In Supabase dashboard → Authentication → Policies:
  - Enable RLS on `item_categories` table
  - Create policy: `Allow public read access` for SELECT operations
  - Policy: `CREATE POLICY "Allow public read" ON item_categories FOR SELECT USING (true);`

**If you see:**
```
ItemCategoriesApi: Exception type: HttpRequestException
ItemCategoriesApi: Exception message: 400 Bad Request
```
**ROOT CAUSE**: Table schema mismatch or serialization error
- **FIX**: Verify table columns match `ItemCategories` schema (id, name, created_at)

#### B. Empty List Returned
**If you see:**
```
ItemCategoriesApi: Supabase query successful, returned 0 categories
ItemCategoriesVM: WARNING: Repository returned empty list!
```
**ROOT CAUSE**: Table exists but has no data
- **FIX**: Insert test data into `item_categories` table in Supabase

### Step 4: Verify Items API Call

**Check logs for:**
```
ItemsViewModel: ViewModel initialized, fetching items...
ItemsApi: Making Supabase query to 'items' table...
ItemsApi: Supabase query successful, returned X items
ItemsViewModel: Received X items from repository
```

**Same issues as Step 3 apply:**
- Table doesn't exist → Create `items` table
- 401 Unauthorized → Fix RLS policies
- 400 Bad Request → Verify schema matches `Items` data class
- Empty list → Insert test data

### Step 5: Verify UI State

**Check the UI state in HomeScreen:**
- If `categoriesState` is `UiState.Loading` → Data is still loading (wait a few seconds)
- If `categoriesState` is `UiState.Error` → Check logs for exception details
- If `categoriesState` is `UiState.Success` with empty list → See Step 3B

**Check the UI state in EquipmentScreen:**
- If `itemsState` is `UiState.Loading` → Data is still loading
- If `itemsState` is `UiState.Error` → Check logs for exception details
- If `itemsState` is `UiState.Success` with empty list → See Step 4

## Common Root Causes Summary

### 1. **Supabase Credentials Missing** (Most Likely)
- **Symptom**: App crashes on startup or logs show "EMPTY"
- **Fix**: Add `SUPABASE_URL` and `SUPABASE_KEY` to `local.properties`

### 2. **RLS Policies Blocking Access** (Very Common)
- **Symptom**: 401 Unauthorized errors in logs
- **Fix**: Create public read policies for `item_categories` and `items` tables

### 3. **Tables Don't Exist**
- **Symptom**: 404 Not Found errors in logs
- **Fix**: Create tables in Supabase dashboard

### 4. **Empty Tables**
- **Symptom**: Query succeeds but returns 0 items
- **Fix**: Insert test data into tables

### 5. **Schema Mismatch**
- **Symptom**: 400 Bad Request or deserialization errors
- **Fix**: Verify table columns match Kotlin data classes

## Additional Issue Found (Not Root Cause)

**EquipmentScreen doesn't filter by category**: The `ItemsViewModel` fetches ALL items, but the EquipmentScreen receives a `categoryName` parameter that is never used. This means:
- EquipmentScreen shows all items regardless of selected category
- This is a bug but NOT the reason for empty screens

## Next Steps

1. Run the app and check Logcat
2. Identify which step fails from the checklist above
3. Apply the corresponding fix
4. Rebuild and test again

## Quick Test Commands

To test Supabase connection manually, you can add this temporary code to verify:

```kotlin
// In ItemCategoriesApi.getCategories(), add before return:
Log.d("ItemCategoriesApi", "Table name: item_categories")
Log.d("ItemCategoriesApi", "Client URL: ${supabaseClient.supabaseUrl}")
```

## Expected Log Flow (Success Case)

```
RMSJimsApp: Application onCreate() called
RMSJimsApp: Supabase URL loaded: https://xxxxx.supabase.co...
RMSJimsApp: Supabase Key loaded: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
SupabaseModule: Creating Supabase client...
SupabaseModule: Supabase client created successfully
RMSJimsApp: Koin initialized successfully
ItemCategoriesVM: ViewModel initialized, fetching categories...
ItemCategoriesVM: Calling repository.fetchCategories()...
ItemCategoriesApi: Making Supabase query to 'item_categories' table...
ItemCategoriesApi: Supabase query successful, returned 5 categories
ItemCategoriesVM: Received 5 categories from repository
ItemCategoriesVM: Categories: [Category1, Category2, Category3, Category4, Category5]
ItemsViewModel: ViewModel initialized, fetching items...
ItemsViewModel: Calling repository.fetchItems()...
ItemsApi: Making Supabase query to 'items' table...
ItemsApi: Supabase query successful, returned 10 items
ItemsViewModel: Received 10 items from repository
```

If your logs don't match this flow, identify where it diverges and apply the corresponding fix.

