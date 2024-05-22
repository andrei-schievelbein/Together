package br.com.noke.twogether.model

data class User(
    val nome: String = "",
    val sobrenome: String = "",
    val categories: List<Category> = listOf(),
    val cargo: String = "",
    val perfil: String = "",
    val habilidades: String = "",
    val celular: String = "",
    val email: String = "",
    val endereco: String = "",
    val website: String = "",
    val imagemURL: String = "",
    val seguir: Boolean = false,
    val match: Boolean = false,
    val aprendiz: Boolean = false
)