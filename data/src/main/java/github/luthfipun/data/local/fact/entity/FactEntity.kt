package github.luthfipun.data.local.fact.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import github.luthfipun.domain.entity.Fact

@Entity(tableName = "cat_fact")
data class FactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fact: String
)

fun FactEntity.toFact(): Fact {
    return Fact(fact)
}
