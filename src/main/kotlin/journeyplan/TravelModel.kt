package journeyplan

// Add your code for modelling public transport networks in this file.

class Station(val name: String) {
    override fun toString(): String = name
}

class Line(val name: String) {
    override fun toString(): String = "$name Line"
}

class Segment(
    val station1: Station,
    val station2: Station,
    val line: Line,
    val travelTime: Int
)

class Route(val route: List<Segment>) {
    fun duration(): Int {
        var output = 0
        for (segment in route) {
            output += segment.travelTime
        }
        return output
    }
    fun numChanges(): Int {
        val lines = mutableListOf<Line>()
        for (segment in route) {
            lines.add(segment.line)
        }
        return (lines.distinct().size - 1)
    }
    override fun toString(): String {
        var origin = route.first().station1
        var destination = route.last().station2
        var line = route.first().line
        val output = StringBuilder()
        val totalDuration = duration()
        val numberChanges = numChanges()
        output.append("$origin to $destination - $totalDuration minutes, $numberChanges changes\n")
        for (segment in route) {
            if (segment.line == line) {
                destination = segment.station2
            } else if (segment.line != line) {
                output.append(" - $origin to $destination by $line\n")
                origin = destination
                destination = segment.station2
                line = segment.line
            }
        }
        output.append(" - $origin to $destination by $line")
        return output.toString()
    }
}
