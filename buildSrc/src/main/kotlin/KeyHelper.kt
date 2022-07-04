import java.io.File
import java.io.FileInputStream
import java.util.*

object KeyHelper {
    const val KEY_STORE_FILE = "storeFile"
    const val KEY_STORE_PASSWORD = "storePassword"
    const val KEY_ALIAS = "keyAlias"
    const val KEY_PASSWORD = "keyPassword"

    const val API_KEY_DEV = "API_KEY_DEV"
    const val API_KEY_STAGING = "API_KEY_STAGING"
    const val API_KEY_PROD = "API_KEY_PROD"

    const val BASE_URL = "BASE_URL"

    private val properties by lazy {
        Properties().apply { load(FileInputStream(File("key.properties"))) }
    }

    fun getValue(key: String): String {
        return properties.getProperty(key)
    }
}