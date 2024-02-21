package com.example.exploracerca.modelo

data class Usuario(
    val id: String?,
    val userId: String,
    val displayName: String,
    val comentarios: String,
){
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to this.userId,
            "display_name" to this.displayName,
            "comentarios" to this.comentarios
        )
    }
}