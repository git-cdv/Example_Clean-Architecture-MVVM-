package com.example.manuel.baseproject.core

interface BaseMapper<in A, out B> {

    fun map(type: A?): B
}