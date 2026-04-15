package com.example.funfacts.data


data class Fact(
    val id: String,
    val text: String,
    val source: String,
    val sourceUrl: String,
    val language: String,
    val permalink: String
){

}