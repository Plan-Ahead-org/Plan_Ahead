package com.palone.planahead

fun <E> List<E>.toggle(alarm: E): List<E> { // if the value is present in list, then it returns list without value, if not, then it returns with this value
    return if (this.contains(alarm))
        this.filter { it != alarm }
    else this + listOf(alarm)
}