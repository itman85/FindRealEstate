package ch.com.findrealestate.utils

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun Any.loadResource(path: String) = this.javaClass.classLoader?.getResource(path)?.readText()
