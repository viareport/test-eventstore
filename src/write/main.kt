package write

/**
 * Created by benoit on 28/09/16.
 */

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.apache.http.HttpStatus
import spark.Spark.halt
import spark.Spark.post
import write.contrats.Contrat
import write.contrats.ContratRepository

private val mapper = ObjectMapper().registerModule(KotlinModule())
private val repository = ContratRepository(mapper)


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
