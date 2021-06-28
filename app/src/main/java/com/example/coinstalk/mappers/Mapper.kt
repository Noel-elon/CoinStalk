package com.example.coinstalk.mappers

interface Mapper<D, A> {
    fun mapToFirst(type: A): D
    fun mapToSecond(type: D): A
}