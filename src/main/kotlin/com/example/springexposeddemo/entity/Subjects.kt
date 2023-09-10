package com.example.springexposeddemo.entity

import com.example.springexposeddemo.model.Subject
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

internal object Subjects : LongIdTable("PKI.Subject") {
    val commonName = varchar("CommonName", length = 250)
    val countryCode = varchar("CountryCode", length = 250)
    val stateOrProvidence = varchar("StateOrProvidence", length = 250)
    val locality = varchar("Locality", length = 250)
    val organization = varchar("Organization", length = 250)
    val organizationalUnit = varchar("OrganizationalUnit", length = 250)
    val emailAddress = varchar("emailAddress", length = 250)
}

internal class SubjectEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<SubjectEntity>(Subjects)
    private val commonName by Subjects.commonName
    private val countryCode by Subjects.countryCode
    private val stateOrProvidence by Subjects.stateOrProvidence
    private val locality by Subjects.locality
    private val organization by Subjects.organization
    private val organizationalUnit by Subjects.organizationalUnit
    private val emailAddress by Subjects.emailAddress

    fun toModel() = Subject(
        id.value,
        commonName,
        countryCode,
        stateOrProvidence,
        locality,
        organization,
        organizationalUnit,
        emailAddress
    )
}