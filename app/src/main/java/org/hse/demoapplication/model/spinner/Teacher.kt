package org.hse.demoapplication.model.spinner

class Teacher(val firstName : String, val secondName : String) : SpinnerItem {

    override fun getId(): Int {
        return 1
    }

    override fun getName(): String {
        return this.toString()
    }

    override fun toString(): String {
        return "$firstName $secondName"
    }
}