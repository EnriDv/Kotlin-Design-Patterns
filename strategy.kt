data class SalonParaBusqueda(
    val nombre: String,
    val distanciaKm: Double,
    val calificacion: Double, // (de 1 a 5)
    val precioPromedio: Double
)

interface SalonSearchStrategy {
    fun buscar(salones: List<SalonParaBusqueda>): List<SalonParaBusqueda>
}



class CercaniaStrategy : SalonSearchStrategy {
    override fun buscar(salones: List<SalonParaBusqueda>): List<SalonParaBusqueda> {
        println("Aplicando Estrategia: Ordenando por más cercanos...")
        return salones.sortedBy { it.distanciaKm }
    }
}

class CalificacionStrategy : SalonSearchStrategy {
    override fun buscar(salones: List<SalonParaBusqueda>): List<SalonParaBusqueda> {
        println("Aplicando Estrategia: Ordenando por mejor calificación...")
        return salones.sortedByDescending { it.calificacion }
    }
}

class PrecioStrategy : SalonSearchStrategy {
    override fun buscar(salones: List<SalonParaBusqueda>): List<SalonParaBusqueda> {
        println("Aplicando Estrategia: Ordenando por más económicos...")
        return salones.sortedBy { it.precioPromedio }
    }
}


class SalonFinder {

    private var estrategiaActual: SalonSearchStrategy = CercaniaStrategy()

    fun setEstrategia(nuevaEstrategia: SalonSearchStrategy) {
        this.estrategiaActual = nuevaEstrategia
    }

    fun obtenerSalones(listaCompleta: List<SalonParaBusqueda>): List<SalonParaBusqueda> {
        return estrategiaActual.buscar(listaCompleta)
    }
}


fun main() {
    val salonA = SalonParaBusqueda("Bellas Uñas", 2.5, 4.8, 20.0)
    val salonB = SalonParaBusqueda("Cortes Rápidos", 0.5, 3.5, 15.0)
    val salonC = SalonParaBusqueda("Spa Divino", 8.0, 5.0, 50.0)
    val listaDeSalones = listOf(salonA, salonB, salonC)

    println("\n--- 🗺️ Patrón Strategy ---")

    val buscador = SalonFinder()
    
    var resultados = buscador.obtenerSalones(listaDeSalones)
    resultados.forEach { println(" - ${it.nombre} (${it.distanciaKm} km)") }

    println("\n* Cliente cambia el filtro a 'Mejor Calificados' *")
    buscador.setEstrategia(CalificacionStrategy())
    resultados = buscador.obtenerSalones(listaDeSalones)
    resultados.forEach { println(" - ${it.nombre} (${it.calificacion} estrellas)") }

    println("\n* Cliente cambia el filtro a 'Más Baratos' *")
    buscador.setEstrategia(PrecioStrategy())
    resultados = buscador.obtenerSalones(listaDeSalones)
    resultados.forEach { println(" - ${it.nombre} (\$${it.precioPromedio})") }
}