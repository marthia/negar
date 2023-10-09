package me.marthia.negar.utitlies

import java.util.concurrent.Flow

interface ProductsDao {

    suspend fun load(path: String): Flow
}