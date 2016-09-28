/**
 * Created by benoit on 28/09/16.
 */

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.apache.http.HttpHeaders
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import spark.Spark.halt
import spark.Spark.post
import java.util.*

private val mapper = ObjectMapper().registerModule(KotlinModule())
private val repository = ContratRepository()

class ContratRepository() {
    // TODO gerer le stop du client http
    val client = HttpClientBuilder.create().build()

    fun  contratExists(reference: String): Boolean {
        val request = HttpGet("http://127.0.0.1:2113/streams/contrat-$reference/0")
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        val response = client.execute(request)

        return response.statusLine.statusCode == HttpStatus.SC_OK
    }

    fun save(contrat: Contrat) {

        val request = HttpPost("http://127.0.0.1:2113/streams/contrat-${contrat.reference}")
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.eventstore.events+json")

        request.entity = StringEntity(mapper.writeValueAsString(contrat.events))

        val response = client.execute(request)
        if(response.statusLine.statusCode != HttpStatus.SC_CREATED ) {
            throw Exception("ERROR : Creation Events de contrat failed")
        }


    }
}


fun main(args: Array<String>) {
    post("/commands/contrats/createContrat", { req, res ->
        val command = mapper.readValue(req.body(), Map::class.java)
        val reference = command["reference"] as? String
        val label = command["label"] as? String
        if(reference != null && label != null && !repository.contratExists(reference)) {
            val contrat = Contrat(reference, label)
            repository.save(contrat)
        } else {
            halt(HttpStatus.SC_CONFLICT, "Le contrat avec la référence $reference existe déjà")
        }
    })
}


class Contrat {
    var reference: String? = ""
    var label: String? = ""
    val events: List<Event>

    constructor(reference:String, label:String) {
        val createdContratEvent = CreatedContratEvent(reference, label)
        this.applyChange(createdContratEvent)
        events = listOf(createdContratEvent)
    }


    private fun  applyChange(createdContratEvent: CreatedContratEvent) {
        this.reference = createdContratEvent.reference
        this.label = createdContratEvent.label
    }


}

abstract class Event(val eventType:String) {
    val eventId: String = UUID.randomUUID().toString()
    abstract var data:Map<String,Any>
}

class CreatedContratEvent(val reference: String, val label: String) : Event("CREATED_CONTRAT_EVENT") {
    override var data: Map<String, Any>
        get() = mapOf(Pair("reference",reference),Pair("label",label))
        set(value) {/*nothing to do*/}

}
