package com.example.manuel.baseproject.home.domain

import com.example.manuel.baseproject.home.commons.datatype.Result
import com.example.manuel.baseproject.home.domain.model.BeersEntity

interface BeersRepository {

    suspend fun getAllBeers(): Result<BeersEntity>?
}