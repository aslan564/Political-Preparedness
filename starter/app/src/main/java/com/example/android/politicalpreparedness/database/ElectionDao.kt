package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.entity.Election
import com.example.android.politicalpreparedness.network.models.entity.RepresentativeLocation

@Dao
interface ElectionDao {

    //TODO: Add insert query

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(election: Election)*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(election: Election)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCity(vararg location: RepresentativeLocation)

    //TODO: Add select all election query
    @Query("SELECT * FROM election_table ORDER BY id DESC")
    fun getAllElection(): LiveData<List<Election>>

    @Query("SELECT * FROM representative_location")
    fun getAllLocation(): LiveData<List<RepresentativeLocation>>

    //TODO: Add select single election query
    @Query("SELECT * FROM election_table WHERE id=:id")
    suspend fun getSingleElection(id: Int): Election


    //TODO: Add delete query
    @Query("DELETE  FROM election_table  WHERE id=:id")
    suspend fun deleteElection(id: Int)

    //TODO: Add clear query

}