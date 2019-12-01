package com.aryansa.rizqi.moviejetpack.domain.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(
    @field:ColumnInfo(name = "idMovie")
    var idMovie: Int? = null,
    @field:ColumnInfo(name = "title")
    var title: String? = null,
    @field:ColumnInfo(name = "releaseDate")
    var releaseDate: String? = null,
    @field:ColumnInfo(name = "posterPath")
    var posterPath: String? = null,
    @field:ColumnInfo(name = "typeMovie")
    var typeMovie: Int? = null)  {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}