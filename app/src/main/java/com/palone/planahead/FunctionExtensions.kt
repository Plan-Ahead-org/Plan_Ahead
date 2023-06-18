package com.palone.planahead

fun <E> List<E>.toggle(item: E): List<E> { // if the value is present in list, then it returns list without value, if not, then it returns with this value
    return if (this.contains(item))
        this.filter { it != item }
    else this + listOf(item)
}