package com.example.sach.model

data class Book(
    val id: Int,
    val tensach: String,
    val tacgia: String,
    val namxb: String,
    val hinhanh: Int,
    val mota: String,
    val favorite: Boolean = false
)