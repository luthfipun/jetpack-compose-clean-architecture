package github.luthfipun.common

fun randomColors(): Int {
    return (Math.random() * 16777215).toInt() or (0xFF shl 24)
}