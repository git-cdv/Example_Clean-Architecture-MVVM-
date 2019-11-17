package com.example.manuel.baseproject.home.commons

interface BaseMapper<in A, out B> {

    fun map(type: A?): B
}