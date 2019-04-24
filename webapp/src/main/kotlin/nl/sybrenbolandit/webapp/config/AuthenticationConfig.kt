package nl.sybrenbolandit.webapp.config

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.security.authentication.AuthenticationFailed
import io.reactivex.Flowable
import java.util.ArrayList
import io.micronaut.security.authentication.UserDetails
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.AuthenticationProvider
import org.reactivestreams.Publisher
import javax.inject.Singleton

@Singleton
@ConfigurationProperties("micronaut.security")
class AuthenticationConfig : AuthenticationProvider {

    lateinit var user: String
    lateinit var password: String

    override fun authenticate(authenticationRequest: AuthenticationRequest<*, *>): Publisher<AuthenticationResponse> {
        return if (authenticationRequest.identity == user && authenticationRequest.secret == password) {
            Flowable.just(UserDetails(authenticationRequest.identity as String, ArrayList()))
        } else Flowable.just(AuthenticationFailed())
    }
}