package write

import java.util.*

/**
 * Created 28/09/16.
 */

abstract class Event(val eventType:String) {
    val eventId: String = UUID.randomUUID().toString()
    abstract val data:Map<String,Any>
}
