package com.selfdev.tracking.data

/** يحدد العنوان والوصف الفرعي المناسبين لأي معرّف فئة، سواء كان من الخطة السباعية أو من الفئات الأخرى. */
object CategoryResolver {

    data class Resolved(val title: String, val description: String?)

    fun resolve(categoryId: String, subItem: String?): Resolved {
        LifeGoals.byId(categoryId)?.let { goal ->
            return Resolved(title = goal.title, description = goal.gain)
        }
        val category = GoalCategory.all.firstOrNull { it.id == categoryId }
        return Resolved(
            title = category?.title ?: categoryId,
            description = subItem
        )
    }
}
