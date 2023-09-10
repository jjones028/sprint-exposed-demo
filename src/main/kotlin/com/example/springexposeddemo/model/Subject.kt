package com.example.springexposeddemo.model
data class Subject(
    val ID: Long?,
    val CommonName: String,
    val CountryCode: String?,
    val StateOrProvidence: String?,
    val Locality: String?,
    val Organization: String?,
    val OrganizationalUnit: String?,
    val emailAddress: String?
)
