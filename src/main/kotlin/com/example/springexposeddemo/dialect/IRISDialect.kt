package com.example.springexposeddemo.dialect

import org.jetbrains.exposed.sql.vendors.DataTypeProvider
import org.jetbrains.exposed.sql.vendors.FunctionProvider
import org.jetbrains.exposed.sql.vendors.VendorDialect

internal object IRISDataTypeProvider : DataTypeProvider() {

    override fun binaryType(): String = "BLOB"
    override fun hexToDb(hexString: String): String {
        TODO("Not yet implemented")
    }

}

internal object IRISFunctionProvider : FunctionProvider() {
    override fun queryLimit(size: Int, offset: Long, alreadyOrdered: Boolean): String  = buildString {
        val offsetStr = if (offset>0) { " %ROWOFFSET $offset"} else { "" }
        if (isNotEmpty()) insert(6, " %ROWLIMIT $size$offsetStr")
    }

}

open class IRISDialect : VendorDialect(dialectName, IRISDataTypeProvider, IRISFunctionProvider) {
    companion object : DialectNameProvider("intersystems iris")


}