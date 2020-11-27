package journeyplan

// Add your code for modelling public transport networks in this file.

class Station(val name: String, var state: Boolean = true) {
  fun close() {
    state = false
  }

  fun open() {
    state = true
  }

  override fun toString(): String = name
}

class Line(val name: String, var working: Boolean = true) {
  fun suspend() {
    working = false
  }

  fun resume() {
    working = true
  }

  override fun toString(): String = "$name Line"
}

class Segment(
    val station1: Station,
    val station2: Station,
    val line: Line,
    val travelTime: Int
)

class Route(val segments: List<Segment>) {
  fun duration(): Int {
    var output = 0
    for (segment in segments) {
      output += segment.travelTime
    }
    return output
  }

  fun numChanges(): Int {
    val lines = mutableListOf<Line>()
    for (segment in segments) {
      lines.add(segment.line)
    }
    return (lines.distinct().size - 1)
  }

  override fun toString(): String {
    var origin = segments.first().station1
    var destination = segments.last().station2
    var line = segments.first().line
    val output = StringBuilder()
    val totalDuration = duration()
    val numberChanges = numChanges()
    output.append("$origin to $destination - $totalDuration minutes, $numberChanges changes\n")
    for (segment in segments) {
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
