package journeyplan

// Add your code for the route planner in this file.

class SubwayMap(private val map: List<Segment>) {
    fun routesFrom(origin: Station, destination: Station, optimisingFor: (Route) -> Int = Route::duration): List<Route> {
        val listOfRoutes: MutableList<Route> = mutableListOf<Route>()
        val visitedStack: MutableList<Station> = mutableListOf(origin)
        fun routeMaker(
            pivot: Station,
            destination1: Station,
            visited: MutableList<Station>,
            path: MutableList<Segment>
        ) {

            for (
                segment in map.filter { it.station1 == pivot }
                    .filter { it.station2 !in visited }
                    .filter { it.line.working }
                    .filter { pivot.state || path.last().line == it.line }
            ) {
                if (segment.station2 == destination1) {
                    listOfRoutes.add(Route(path + segment))
                } else {
                    routeMaker(
                        segment.station2,
                        destination1,
                        visited.plus(pivot) as MutableList<Station>,
                        path.plus(segment) as MutableList<Segment>
                    )
                }
            }
        }
        routeMaker(origin, destination, visitedStack, mutableListOf<Segment>())
        return listOfRoutes.sortedBy(optimisingFor)
    }
}
