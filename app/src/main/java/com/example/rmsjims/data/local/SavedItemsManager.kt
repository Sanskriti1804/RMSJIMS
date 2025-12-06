package com.example.rmsjims.data.local

import android.content.Context
import android.content.SharedPreferences

class SavedItemsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("saved_items_prefs", Context.MODE_PRIVATE)
    
    companion object {
        private const val KEY_SAVED_ITEMS = "saved_equipment_ids"
    }
    
    fun isItemSaved(itemId: Int): Boolean {
        val savedIds = getSavedItemIds()
        return savedIds.contains(itemId)
    }
    
    fun saveItem(itemId: Int) {
        val savedIds = getSavedItemIds().toMutableSet()
        savedIds.add(itemId)
        saveItemIds(savedIds)
    }
    
    fun unsaveItem(itemId: Int) {
        val savedIds = getSavedItemIds().toMutableSet()
        savedIds.remove(itemId)
        saveItemIds(savedIds)
    }
    
    fun toggleItem(itemId: Int): Boolean {
        val isCurrentlySaved = isItemSaved(itemId)
        if (isCurrentlySaved) {
            unsaveItem(itemId)
        } else {
            saveItem(itemId)
        }
        return !isCurrentlySaved
    }
    
    fun getSavedItemIds(): Set<Int> {
        val savedIdsString = prefs.getString(KEY_SAVED_ITEMS, "") ?: ""
        return if (savedIdsString.isEmpty()) {
            emptySet()
        } else {
            savedIdsString.split(",").mapNotNull { it.toIntOrNull() }.toSet()
        }
    }
    
    private fun saveItemIds(ids: Set<Int>) {
        val idsString = ids.joinToString(",")
        prefs.edit().putString(KEY_SAVED_ITEMS, idsString).apply()
    }
}

