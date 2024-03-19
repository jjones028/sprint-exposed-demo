package com.example.springexposeddemo.dialect

import org.jetbrains.exposed.sql.FieldSet
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.QueryBuilder
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ExpressionAlias
import org.jetbrains.exposed.sql.ColumnSet
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.Select
import org.jetbrains.exposed.sql.vendors.DataTypeProvider
import org.jetbrains.exposed.sql.vendors.FunctionProvider
import org.jetbrains.exposed.sql.vendors.VendorDialect
import org.jetbrains.exposed.sql.vendors.currentDialect

internal object IRISDataTypeProvider : DataTypeProvider() {

    override fun binaryType(): String = "BLOB"
    override fun hexToDb(hexString: String): String {
        TODO("Not yet implemented")
    }

}

internal object IRISFunctionProvider : FunctionProvider()

open class IRISDialect : VendorDialect(dialectName, IRISDataTypeProvider, IRISFunctionProvider) {
    companion object : DialectNameProvider("intersystems iris")


}

open class IrisQuery(override var set: FieldSet, where: Op<Boolean>?) : Query(set, where) {
    override fun prepareSQL(builder: QueryBuilder): String {
        require(set.fields.isNotEmpty()) { "Can't prepare SELECT statement without columns or expressions to retrieve" }

        builder {
            append("SELECT ")
            limit?.let {
                append("%ROWLIMIT $it")
                append(if (offset>0) { " %ROWOFFSET $offset"} else { "" })
                append(" ")
            }
            if (count) {
                append("COUNT(*)")
            } else {
                if (distinct) {
                    append("DISTINCT ")
                }
                set.realFields.appendTo { +it }
            }

            if (set.source != Table.Dual || currentDialect.supportsDualTableConcept) {
                append(" FROM ")
                set.source.describe(transaction, this)
            }

            where?.let {
                append(" WHERE ")
                +it
            }

            if (!count) {
                if (groupedByColumns.isNotEmpty()) {
                    append(" GROUP BY ")
                    groupedByColumns.appendTo {
                        +((it as? ExpressionAlias)?.aliasOnlyExpression() ?: it)
                    }
                }

                having?.let {
                    append(" HAVING ")
                    append(it)
                }

                if (orderByExpressions.isNotEmpty()) {
                    append(" ORDER BY ")
                    orderByExpressions.appendTo { (expression, sortOrder) ->
                        currentDialect.dataTypeProvider.precessOrderByClause(this, expression, sortOrder)
                    }
                }
            }
        }
        return builder.toString()
    }
}

fun FieldSet.selectAll(): Query = IrisQuery(this, null)

fun ColumnSet.irisSelect(column: Expression<*>, vararg columns: Expression<*>): Query =
    IrisQuery(Select(this, listOf(column) + columns), null)

fun ColumnSet.irisSelect(columns: List<Expression<*>>): Query = IrisQuery(Select(this, columns), null)
