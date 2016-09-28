package write.contrats

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.HttpHeaders
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder

class ContratRepository(val mapper:ObjectMapper) {
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