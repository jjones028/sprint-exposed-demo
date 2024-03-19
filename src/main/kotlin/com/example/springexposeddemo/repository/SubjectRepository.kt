package com.example.springexposeddemo.repository

import com.example.springexposeddemo.dialect.irisSelect
import com.example.springexposeddemo.entity.SubjectEntity
import com.example.springexposeddemo.entity.Subjects
import com.example.springexposeddemo.model.Subject
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface SubjectRepository {
    fun findAll(): List<Subject>

    fun queryAll(): List<Subject>
}

@Repository
@Transactional
class SubjectRepositoryImpl: SubjectRepository {
    override fun findAll(): List<Subject> = SubjectEntity.all().map(SubjectEntity::toModel)
    override fun queryAll(): List<Subject> {
        val query = Subjects.irisSelect(Subjects.id).limit(1, offset = 10)
        return query.map {
            Subject(
                it[Subjects.id].value,
                it[Subjects.commonName],
                it[Subjects.countryCode],
                it[Subjects.stateOrProvidence],
                it[Subjects.locality],
                it[Subjects.organization],
                it[Subjects.organizationalUnit],
                it[Subjects.emailAddress]
        ) }
    }


}
