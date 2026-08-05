package com.prev85.lifecalendar.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Query("SELECT * FROM entries WHERE date >= :from AND date <= :to ORDER BY date ASC")
    fun getBetween(from: String, to: String): Flow<List<Entry>>

    @Query("SELECT * FROM entries ORDER BY date DESC")
    fun getAll(): Flow<List<Entry>>

    @Query("SELECT COUNT(*) FROM entries")
    fun count(): Flow<Int>

    @Insert
    suspend fun insert(entry: Entry): Long

    @Insert
    suspend fun insertAll(entries: List<Entry>)

    @Query("DELETE FROM entries")
    suspend fun clearAll()

    @Update
    suspend fun update(entry: Entry)

    @Delete
    suspend fun delete(entry: Entry)
}

@Dao
interface EventDao {
    @Query("SELECT * FROM events ORDER BY date ASC")
    fun getAll(): Flow<List<Event>>

    @Insert
    suspend fun insert(event: Event): Long

    @Insert
    suspend fun insertAll(events: List<Event>)

    @Query("DELETE FROM events")
    suspend fun clearAll()

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)
}
