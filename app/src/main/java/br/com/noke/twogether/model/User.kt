package br.com.noke.twogether.model


data class User(
    val id: String = "",
    val first: String = "",
    val last: String = "",
    val born: Int = 0,
    val categories: List<Category> = listOf()
)