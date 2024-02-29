package by.bashlikovvv.domain.repository

interface IBaseRepository<T, Index : Comparable<Index>> {

    val data: MutableList<Pair<Index, T>>

    fun getItemById(id: Index) = data.find { it.first == id }

    fun addItem(id: Index, item: T): T?

    fun getLastItem(): T?

    fun removeItem(id: Index): Boolean

}