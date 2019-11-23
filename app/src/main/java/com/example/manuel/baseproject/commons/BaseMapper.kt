package com.example.manuel.baseproject.commons

interface BaseMapper<in A, out B> {

    fun map(type: A?): B
}