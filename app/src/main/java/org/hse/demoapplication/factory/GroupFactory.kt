package org.hse.demoapplication.factory

import org.hse.demoapplication.model.spinner.Group

class GroupFactory {
    companion object {
        private const val minGroupNumber = 1

        fun createList(prefix: String, year: Int, groupsNumber: Int): ArrayList<Group> {
            val output = ArrayList<Group>()
            for (groupNumber in minGroupNumber..groupsNumber) {
                output.add(Group(prefix, year, groupNumber))
            }
            return output
        }
    }
}