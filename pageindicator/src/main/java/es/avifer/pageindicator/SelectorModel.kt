package es.avifer.pageindicator

data class SelectorModel(
    val id: Int,
    var isSelected: Boolean,
) {
    companion object {
        fun createList(quantity: Int, selected: Int = 0) =
            (0 until quantity).map { SelectorModel(it, it == selected) }
    }
}