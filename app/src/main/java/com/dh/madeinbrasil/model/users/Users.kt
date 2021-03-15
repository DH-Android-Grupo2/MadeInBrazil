package com.dh.madeinbrasil.model.users

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users")
data class Users (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "email")
    var email: String?,
    @ColumnInfo(name = "password")
    var password: String?,
    @ColumnInfo(name = "picture")
    var picture: String?
        ): Parcelable {

    override fun equals(other: Any?): Boolean {
        return (other as? Users)?.let {
            it.email == email
        } ?: false
    }

}
