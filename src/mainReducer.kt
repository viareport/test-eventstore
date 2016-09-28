
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.apache.http.HttpHeaders
import org.apache.http.HttpStatus
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import kotlin.comparisons.compareBy
import kotlin.concurrent.fixedRateTimer

/**
 * Created 28/09/16.
 */

val contrats:MutableMap<String, MutableMap<String,String>> = mutableMapOf()
private val mapper = ObjectMapper().registerModule(KotlinModule())
var lastNumber = 0;

@JsonIgnoreProperties(ignoreUnknown = true)
class Stream(val entries:List<EventStoreEvent>)
@JsonIgnoreProperties(ignoreUnknown = true)
class EventStoreEvent(val positionEventNumber:Int, val eventId:String, val eventType:String, val streamId:String, val data:String)

fun main(args: Array<String>) {
    val client = HttpClientBuilder.create().build()
    val request = HttpGet("http://127.0.0.1:2113/streams/\$ce-contrat?embed=body")
    request.setHeader(HttpHeaders.ACCEPT, "application/json")
    val response = client.execute(request)
    parseEvent(response)

    val fixedRateTimer = fixedRateTimer(name = "contrats-subscription",
            initialDelay = 0, period = 1000) {
        val request = HttpGet("http://127.0.0.1:2113/streams/\$ce-contrat/${lastNumber+1}/forward/20?embed=body")
        request.setHeader(HttpHeaders.ACCEPT, "application/json")
        val response = client.execute(request)
        parseEvent(response)
    }
}

private fun parseEvent(response: CloseableHttpResponse) {
    if (response.statusLine.statusCode == HttpStatus.SC_OK) {
        val body = EntityUtils.toString(response.entity)
        val entries = mapper.readValue(body, Stream::class.java).entries
        entries.sortedWith(compareBy({ event -> event.positionEventNumber })).forEach { event ->
            var contrat = mapper.readValue(event.data, Map::class.java) as Map<String, String>
            if (!contrats.containsKey(event.streamId)) {
                contrats.put(event.streamId, mutableMapOf());
            }
            contrats.get(event.streamId)?.putAll(contrat)
            lastNumber= event.positionEventNumber;
        }
    } else {
        throw Exception("ERROR: EventStore not working")
    }
}
