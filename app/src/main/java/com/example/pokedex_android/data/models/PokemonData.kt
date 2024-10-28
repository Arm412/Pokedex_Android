import kotlinx.serialization.Serializable

@Serializable
data class PokemonData(
    val id: Int,
    val name: Name,
    val type: List<String>,
    val base: BaseStats? = null,
    val species: String,
    val description: String,
    val evolution: Evolution,
    val profile: Profile,
    val image: Image
)

@Serializable
data class Name(
    val english: String,
    val japanese: String,
    val chinese: String,
    val french: String
)

@Serializable
data class BaseStats(
    val HP: Int,
    val Attack: Int,
    val Defense: Int,
    val Sp_Attack: Int,
    val Sp_Defense: Int,
    val Speed: Int
)

@Serializable
data class Evolution(
    val next: List<List<String>>? = null,
    val prev: List<String>? = null
)

@Serializable
data class Profile(
    val ability: List<List<String>>,
    val egg: List<String>? = null,
    val gender: String,
    val height: String,
    val weight: String
)

@Serializable
data class Image(
    val sprite: String,
    val thumbnail: String,
    val hires: String = ""
)
