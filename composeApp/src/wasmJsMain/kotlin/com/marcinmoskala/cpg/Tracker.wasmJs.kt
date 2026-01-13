package com.marcinmoskala.composeexercises.ui.samples.guesser

import kotlin.js.JsAny
import kotlin.js.toJsString

external fun gtag(command: String, eventName: String, params: JsAny? = definedExternally)

actual fun trackEvent(eventName: String, params: Map<String, String>) {
    try {
        val jsParams = createJsObject()
        params.forEach { (key, value) ->
            setJsProperty(jsParams, key, value.toJsString())
        }
        gtag("event", eventName, jsParams)
    } catch (e: Throwable) {
        println("Failed to track event: $e")
    }
}

private fun createJsObject(): JsAny = js("{}")

private fun setJsProperty(obj: JsAny, name: String, value: JsAny): Unit = js("obj[name] = value")
