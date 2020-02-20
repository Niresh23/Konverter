package com.nik.konverter.model.forms

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.ElementMap
import org.simpleframework.xml.Root

@Root
data class ValCurs(
    @field:Attribute(name = "Date")
    var date:String,

    @field:Attribute(name = "name")
    var name: String,

    @field:ElementList(entry = "Valute", inline = true)
    var valutes: List<Valute>) {
    constructor() : this("","",mutableListOf<Valute>())
}