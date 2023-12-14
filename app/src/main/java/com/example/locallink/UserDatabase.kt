package com.example.locallink

class UserDatabase {
    companion object {
        var users: MutableList<User> = generateUsers()
        var localUserMapToBuilding: MutableMap<String, MutableList<User>> = mutableMapOf()
        var generalUserMapToBuilding: MutableMap<String, MutableList<User>> = mutableMapOf()
        var usersFoundinSearch: MutableList<User> = mutableListOf()
        var numUsersFound: Int = 0

        fun init(set: Set<String>) {
            numUsersFound = 0
            generateUsers()
            generateGeneralMapToBuilding()
            generateLocalMapToBuilding(set)
            calculateNumUsers()
            generateUsersInSearch()
        }

        private fun generateUsersInSearch() {
            usersFoundinSearch = mutableListOf()
            for (building in localUserMapToBuilding) {
                for (user in localUserMapToBuilding[building.key]!!) {
                    usersFoundinSearch.add(user)
                }
            }
        }

        private fun calculateNumUsers() {
            for (building in localUserMapToBuilding) {
                numUsersFound += localUserMapToBuilding[building.key]!!.size
            }
        }

        private fun generateLocalMapToBuilding(set: Set<String>) {
            localUserMapToBuilding = mutableMapOf()
            for (buildingName in set) {
                if (generalUserMapToBuilding.containsKey(buildingName)) {
                    localUserMapToBuilding[buildingName] = generalUserMapToBuilding[buildingName]!!
                }
            }
        }
        private fun generateGeneralMapToBuilding() {
            generalUserMapToBuilding = mutableMapOf()
            for (user in users) {
                if (!generalUserMapToBuilding.containsKey(user.building)) {
                    generalUserMapToBuilding[user.building] = mutableListOf()
                }
                generalUserMapToBuilding[user.building]?.add(user)
            }
        }
       private fun generateUsers(): MutableList<User> {
            val result: MutableList<User> = mutableListOf()
            result.add(User("Danny",
                "Hayden Hall",
                "He/Him",
                "Computer Science & Game Development",
                "HCI, Computer Graphics, Game Capstone",
                "Lifting, hiking, biking, guitar",
                "Looking for a study buddy to help me prepare for my graphics exam!",
                "U_5"))
            result.add(User("Johnny",
                "Snell Library",
                "He/Him",
                "Computer Science",
                "Computer Graphics, Advanced Writing",
                "Competitive Programming, Cooking, Climbing",
                "Trying to meet other Northeastern students that want to grab a coffee with me!",
                "U_4"
                ))
            result.add(User("Maria",
                "ISEC",
                "She/Her",
                "Data Science & Economics",
                "Foundations of Data Science, HCI, Information Retrieval",
                "Playing with my cat, Reading",
                "Hey, I'm Maria! I'm looking to make some new friends that want to join a cat cafe with me!",
                "U_3"
            ))
            result.add(User("Chad",
                "ISEC",
                "He/Him",
                "Biology",
                "Biochemistry, Biology Capstone",
                "Concerts, Volleyball",
                "Hello, looking for someone to get lunch with me at IV",
                "U_2"
            ))
            result.add(User("Alia",
                "Ryder Hall",
                "She/Her",
                "Computer Science & Design",
                "Object Oriented Programming, Graphic Design 2",
                "Writing, Drawing, Reading",
                "Hello, new to Boston, looking for students to connect with and explore around!",
                "U_1"
            ))

            return result
        }
    }
}


class User constructor(val name: String,
                       val building: String,
                       val pronouns: String,
                       val major: String,
                       val classes: String,
                       val interests: String,
                       val bio: String,
                       val userId: String) {
}