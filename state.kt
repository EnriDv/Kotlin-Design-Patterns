import java.lang.IllegalStateException

// --- 1. La Interfaz  ---
interface EstadoCita {
    fun aceptar(cita: Cita)
    fun rechazar(cita: Cita)
    fun completar(cita: Cita)
    fun cancelar(cita: Cita)
}

// --- 2. Estados Concretos ---

/** Estado: PENDIENTE */
class EstadoPendiente : EstadoCita {
    override fun aceptar(cita: Cita) {
        println("[Cita ${cita.id}]: ACEPTADA. Notificando al cliente...")
        cita.transicionarA(EstadoAceptada()) 
    }

    override fun rechazar(cita: Cita) {
        println("[Cita ${cita.id}]: RECHAZADA. Notificando al cliente...")
        cita.transicionarA(EstadoRechazada())
    }

    override fun completar(cita: Cita) {
        throw IllegalStateException("Cita ${cita.id} aún no está aceptada. No se puede completar.")
    }

    override fun cancelar(cita: Cita) {
        println("[Cita ${cita.id}]: CANCELADA (por cliente o local).")
        cita.transicionarA(EstadoCancelada())
    }
}

/** Estado: ACEPTADA */
class EstadoAceptada : EstadoCita {
    override fun aceptar(cita: Cita) {
        println("[Cita ${cita.id}]: Ya está aceptada. No se puede volver a aceptar.")
    }

    override fun rechazar(cita: Cita) {
        println("[Cita ${cita.id}]: RECHAZADA (post-aceptación). Notificando...")
        cita.transicionarA(EstadoRechazada())
    }

    override fun completar(cita: Cita) {
        println("[Cita ${cita.id}]: COMPLETADA. Procesando pago...")
        cita.transicionarA(EstadoCompletada())
    }

    override fun cancelar(cita: Cita) {
        println("[Cita ${cita.id}]: CANCELADA (estaba aceptada). Liberando horario.")
        cita.transicionarA(EstadoCancelada())
    }
}

/** Estado: RECHAZADA */
class EstadoRechazada : EstadoCita {
    override fun aceptar(cita: Cita) {
        println("[Cita ${cita.id}]: Está RECHAZADA. No se puede aceptar.")
    }
    override fun rechazar(cita: Cita) {
        println("[Cita ${cita.id}]: Ya está rechazada.")
    }
    override fun completar(cita: Cita) {
        println("[Cita ${cita.id}]: Está RECHAZADA. No se puede completar.")
    }
    override fun cancelar(cita: Cita) {
        println("[Cita ${cita.id}]: Ya está rechazada.")
    }
}

/** Estado: COMPLETADA */
class EstadoCompletada : EstadoCita {
    override fun aceptar(cita: Cita) {
        println("[Cita ${cita.id}]: Está COMPLETADA. No se puede aceptar.")
    }
    override fun rechazar(cita: Cita) {
        println("[Cita ${cita.id}]: Está COMPLETADA. No se puede rechazar.")
    }
    override fun completar(cita: Cita) {
        println("[Cita ${cita.id}]: Ya está completada.")
    }
    override fun cancelar(cita: Cita) {
        println("[Cita ${cita.id}]: Está COMPLETADA. No se puede cancelar.")
    }
}

/** Estado: CANCELADA */
class EstadoCancelada : EstadoCita {
    override fun aceptar(cita: Cita) {
        println("[Cita ${cita.id}]: Está CANCELADA. No se puede aceptar.")
    }
    override fun rechazar(cita: Cita) {
        println("[Cita ${cita.id}]: Está CANCELADA. No se puede rechazar.")
    }
    override fun completar(cita: Cita) {
        println("[Cita ${cita.id}]: Está CANCELADA. No se puede completar.")
    }
    override fun cancelar(cita: Cita) {
        println("[Cita ${cita.id}]: Ya está cancelada.")
    }
}


// --- 3. El Contexto (La Cita) ---
class Cita(val id: String) {
    
    private var estadoActual: EstadoCita = EstadoPendiente()

    init {
        println("[Cita $id]: Creada. Estado inicial: PENDIENTE")
    }

    fun transicionarA(nuevoEstado: EstadoCita) {
        this.estadoActual = nuevoEstado
        println("... [Cita $id]: Transición -> ${nuevoEstado.javaClass.simpleName}")
    }

    // --- Acciones del Usuario (Cliente o Local) ---
    fun aceptar() = estadoActual.aceptar(this)
    fun rechazar() = estadoActual.rechazar(this)
    fun completar() = estadoActual.completar(this)
    fun cancelar() = estadoActual.cancelar(this)
}

fun main() {

    println("--- Escenario 1: Flujo Exitoso (Cita de Ana) ---")
    val citaAna = Cita("Corte con Ana")
    citaAna.aceptar()
    citaAna.aceptar()
    citaAna.completar()


    println("\n--- Escenario 2: Flujo Rechazado (Cita de Bruno) ---")
    val citaBruno = Cita("Manicure Bruno")
    citaBruno.rechazar()
    citaBruno.aceptar()


    println("\n--- Escenario 3: Flujo de Cancelación (Cita de Carla) ---")
    val citaCarla = Cita("Peinado Carla")
    citaCarla.aceptar()
    citaCarla.cancelar()
    citaCarla.completar()

    
    println("\n--- Escenario 4: Acción Inválida (Cita de David) ---")
    val citaDavid = Cita("Tinte David")
    try {
        citaDavid.completar()
    } catch (e: IllegalStateException) {
        println("... ERROR CAPTURADO: ${e.message}")
    }
}