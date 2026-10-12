package sv.edu.retrofitrepoapp

import com.google.gson.annotations.SerializedName

// Clase que representa la respuesta JSON de Dog API:
// { "message": [ "https://...jpg", ... ], "status": "success" }
data class DogsResponse(
    // Estado de la respuesta ("success" o "error")
    @SerializedName("status") val status: String?,
    // Lista de URLs de imágenes; en el JSON la clave se llama "message"
    @SerializedName("message") val images: List<String>?
)
