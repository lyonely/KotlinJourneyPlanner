package journeyplan

// Add your code for the route planner in this file.

class SubwayMap(private val map: List<Segment>) {
    fun routesFrom(origin: Station, destination: Station): List<Route> {
        val listOfRoutes: MutableList<Route> = mutableListOf<Route>()
        val visitedStack: MutableList<Station> = mutableListOf(origin)
        fun routeMaker(
            pivot: Station,
            destination1: Station,
            visited: MutableList<Station>,
            path: MutableList<Segment>
        ) {
            visited.add(pivot)
            for (segment in map.filter { it.station1 == pivot }.filter { it.station2 !in visited }) {
                if (segment.station2 == destination1) {
                    listOfRoutes.add(Route(path + segment))
                } else {
                    routeMaker(segment.station2, destination1, visited, (path.plus(segment) as MutableList<Segment>))
                }
            }
        }
        routeMaker(origin, destination, visitedStack, mutableListOf<Segment>())
        return listOfRoutes
    }
}

fun main() {
    val map = londonUndergound()
    val routes = map.routesFrom(bakerStreet, lancasterGate)
    for (route in routes) {
        println(route)
    }
}

val bakerlooLine = Line("Bakerloo")
val centralLine = Line("Central")

val bakerStreet = Station("Baker Street")
val regentsPark = Station("Regent's Park")
val oxfordCircus = Station("Oxford Circus")
val bondStreet = Station("Bond Street")
val marbleArch = Station("Marble Arch")
val lancasterGate = Station("Lancaster Gate")

fun londonUndergound(): SubwayMap {

    return SubwayMap(
        listOf(
            Segment(bakerStreet, regentsPark, bakerlooLine, 3),
            Segment(regentsPark, bakerStreet, bakerlooLine, 4),
            Segment(regentsPark, oxfordCircus, bakerlooLine, 2),
            Segment(oxfordCircus, regentsPark, bakerlooLine, 3),
            Segment(oxfordCircus, bondStreet, centralLine, 5),
            Segment(bondStreet, marbleArch, centralLine, 4),
            Segment(marbleArch, lancasterGate, centralLine, 3)
        )
    )
}
