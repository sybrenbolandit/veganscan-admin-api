package nl.sybrenbolandit.webapp

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("nl.sybrenbolandit")
                .mainClass(Application.javaClass)
                .start()
    }
}