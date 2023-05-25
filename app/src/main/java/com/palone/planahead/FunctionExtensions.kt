package com.palone.planahead

fun <E> List<E>.flip(alarm: E): List<E> {
    return if (this.contains(alarm))
        this.filter { it != alarm }
    else this + listOf(alarm)
}