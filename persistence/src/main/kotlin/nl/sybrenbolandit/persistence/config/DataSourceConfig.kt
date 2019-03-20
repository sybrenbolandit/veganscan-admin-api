package nl.sybrenbolandit.persistence.config

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("vegandb")
class DataSourceConfig {
    lateinit var databaseName: String
}