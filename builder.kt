class Salon private constructor(
    val id: String,
    val nombre: String,
    val direccion: String,
    val telefono: String?,
    val descripcion: String?,
    val fotosUrls: List<String>,
    val servicios: List<Servicio>,
    val horario: Map<String, String>
) {

    override fun toString(): String {
        return """
        Salon(
            id='$id', 
            nombre='$nombre', 
            direccion='$direccion', 
            telefono=$telefono, 
            descripcion=$descripcion, 
            fotos=${fotosUrls.size} items, 
            servicios=${servicios.size} items,
            horario=$horario
        )
        """.trimIndent()
    }

    class Builder(
        private val id: String,
        private val nombre: String
    ) {

        private var direccion: String = ""
        private var telefono: String? = null
        private var descripcion: String? = null
        private var fotosUrls: List<String> = emptyList()
        private var servicios: List<Servicio> = emptyList()
        private var horario: Map<String, String> = emptyMap()

        fun direccion(direccion: String) = apply { this.direccion = direccion }
        fun telefono(telefono: String) = apply { this.telefono = telefono }
        fun descripcion(desc: String) = apply { this.descripcion = desc }
        fun fotos(urls: List<String>) = apply { this.fotosUrls = urls }
        fun servicios(servicios: List<Servicio>) = apply { this.servicios = servicios }
        fun horario(horario: Map<String, String>) = apply { this.horario = horario }

        fun build(): Salon {
            return Salon(
                id = id,
                nombre = nombre,
                direccion = direccion,
                telefono = telefono,
                descripcion = descripcion,
                fotosUrls = fotosUrls,
                servicios = servicios,
                horario = horario
            )
        }
    }
}

data class Servicio(val nombre: String, val precio: Double)


fun main() {
    val corte = Servicio("Corte de Dama", 25.0)
    val peinado = Servicio("Peinado de Fiesta", 40.0)
    val manicure = Servicio("Manicure Completa", 15.0)

    val miSalon = Salon.Builder("salon-uuid-123", "Glamour Hair Studio")
        .direccion("Av. Siempre Viva 742")
        .telefono("555-1234")
        .descripcion("Especialistas en colorimetría y peinados de boda.")
        .servicios(listOf(corte, peinado, manicure))
        .fotos(listOf("url/foto1.jpg", "url/foto_fachada.jpg"))
        .horario(mapOf("Lunes-Viernes" to "09:00-18:00", "Sábado" to "10:00-15:00"))
        .build()

    println("--- 🏛️ Patrón Builder ---")
    println(miSalon)
}