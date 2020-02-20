package com.nik.konverter.model.forms

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root
import org.simpleframework.xml.convert.Convert
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

@Root(name = "Valute")
data class Valute (
    @field:Attribute(name = "ID", required = false)
    var id: String = "",

    @field:Element(name = "NumCode", required = false)
    var numCode: String = "",

    @field:Element(name = "CharCode", required = false)
    var charCode: String = "",

    @field:Element(name = "Nominal", required = false)
    var nominal: String = "",

    @field:Element(name = "Name", required = false)
    var name: String = "",

    @field:Element(name = "Value", required = false)
    var value: String = "")

