package org.hse.demoapplication.model.spinner

class Group(val prefix : String, val year : Int, val number : Int) : SpinnerItem {

    private val groupNamePattern = "%s-%d-%d"

    override fun getId(): Int {
        return 0
    }

    override fun getName(): String {
        return this.toString()
    }

    override fun toString() : String {
        return groupNamePattern.format(prefix, year, number)
    }
}