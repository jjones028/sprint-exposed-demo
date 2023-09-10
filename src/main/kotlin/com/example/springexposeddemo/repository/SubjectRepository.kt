package com.example.springexposeddemo.repository

import com.example.springexposeddemo.entity.SubjectEntity
import com.example.springexposeddemo.model.Subject
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface SubjectRepository {
    fun findAll(): List<Subject>
}

@Repository
@Transactional
class SubjectRepositoryImpl: SubjectRepository {
    override fun findAll(): List<Subject> = SubjectEntity.all().map { it.toModel() }
}