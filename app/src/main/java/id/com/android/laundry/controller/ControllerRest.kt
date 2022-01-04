package id.com.android.laundry.controller

import android.app.Application
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import id.com.android.laundry.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

class ControllerRest {

    fun newInstance(application: Application): ControllerEndpoint {

        val gsonBuilder = GsonBuilder().setLenient().registerTypeAdapter(Integer::class.java, IntegerTypeAdapter()).registerTypeAdapter(String::class.java, StringTypeAdapter())
        gsonBuilder.serializeNulls()
        val client = OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60,TimeUnit.SECONDS)
        if(BuildConfig.DEBUG) client.addInterceptor(logInterceptor)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://onlinelaundryrianto.000webhostapp.com/api_laundry/")
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .client(client.build())
            .build()
        return retrofit.create(ControllerEndpoint::class.java)
    }


    private val logInterceptor: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor      = HttpLoggingInterceptor()
            httpLoggingInterceptor.level    = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }

    class IntegerTypeAdapter : TypeAdapter<Int>() {
        @Throws(IOException::class)
        override fun read(reader: JsonReader): Int? {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull()
                return 0
            }
            val stringValue = reader.nextString()
            return try {
                Integer.valueOf(stringValue)
            } catch (e: NumberFormatException) {
                0
            }
        }

        @Throws(IOException::class)
        override fun write(writer: JsonWriter, value: Int?) {
            if (value == null) {
                writer.nullValue()
                return
            }
            writer.value(value)
        }
    }

    class StringTypeAdapter : TypeAdapter<String>() {
        @Throws(IOException::class)
        override fun read(reader: JsonReader): String {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull()
                return ""
            }
            val stringValue = reader.nextString()
            try {
                return stringValue.toString()
            } catch (e: Exception) {
                return ""
            }

        }

        @Throws(IOException::class)
        override fun write(writer: JsonWriter, value: String?) {
            if (value == null) {
                writer.nullValue()
                return
            }
            writer.value(value)
        }
    }

}
