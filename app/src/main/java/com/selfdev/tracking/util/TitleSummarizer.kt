package com.selfdev.tracking.util

/**
 * يولّد عنوانًا مختصرًا آليًا من نص الإجراء المدخل، وفق المطلوب:
 * "يتم تلخيصه آليًا بأقل عدد ممكن من الكلمات".
 *
 * يعمل بالكامل على الجهاز بدون إنترنت: يستبعد أدوات الربط والحروف الشائعة
 * ثم يأخذ أهم الكلمات الأولى ذات الدلالة من النص.
 */
object TitleSummarizer {

    private val stopWords = setOf(
        "في", "من", "إلى", "على", "عن", "مع", "هذا", "هذه", "ذلك", "التي", "الذي",
        "و", "أو", "ثم", "كان", "كانت", "أن", "إن", "لا", "لم", "لن", "قد", "كل",
        "بعد", "قبل", "عند", "حتى", "كما", "بين", "هو", "هي", "أنا", "نحن"
    )

    private const val MAX_WORDS = 5
    private const val MAX_CHARS = 40

    fun summarize(body: String): String {
        val cleaned = body.trim().replace(Regex("\\s+"), " ")
        if (cleaned.isEmpty()) return "إجراء بدون عنوان"

        val meaningfulWords = cleaned.split(" ")
            .filter { it.isNotBlank() && it !in stopWords }
            .take(MAX_WORDS)

        val title = if (meaningfulWords.isNotEmpty()) {
            meaningfulWords.joinToString(" ")
        } else {
            cleaned.split(" ").take(MAX_WORDS).joinToString(" ")
        }

        return if (title.length > MAX_CHARS) title.take(MAX_CHARS).trim() + "…" else title
    }
}
