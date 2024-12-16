package com.marcinmoskala.cpg

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import kotlin.wasm.unsafe.UnsafeWasmMemoryApi
import kotlin.wasm.unsafe.withScopedMemoryAllocator
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.w3c.fetch.Response

@Composable
fun LoadFont(body: @Composable () -> Unit) {
    val fontFamilyResolver = LocalFontFamilyResolver.current
    val fontsLoaded = remember { mutableStateOf(false) }

    if (fontsLoaded.value) {
        body()
    }

    LaunchedEffect(Unit) {
        val notoEmojisBytes = loadRes("./NotoColorEmoji.ttf").toByteArray()
        val fontFamily = FontFamily(listOf(Font("NotoColorEmoji", notoEmojisBytes)))
        fontFamilyResolver.preload(fontFamily)
        fontsLoaded.value = true
    }
}

suspend fun loadRes(url: String): ArrayBuffer {
    return window.fetch(url).await<Response>().arrayBuffer().await()
}

fun ArrayBuffer.toByteArray(): ByteArray {
    val source = Int8Array(this, 0, byteLength)
    return jsInt8ArrayToKotlinByteArray(source)
}

internal fun jsInt8ArrayToKotlinByteArray(x: Int8Array): ByteArray {
    val size = x.length

    @OptIn(UnsafeWasmMemoryApi::class)
    return withScopedMemoryAllocator { allocator ->
        val memBuffer = allocator.allocate(size)
        val dstAddress = memBuffer.address.toInt()
        jsExportInt8ArrayToWasm(x, size, dstAddress)
        ByteArray(size) { i -> (memBuffer + i).loadByte() }
    }
}

@JsFun(
    """ (src, size, dstAddr) => {
        const mem8 = new Int8Array(wasmExports.memory.buffer, dstAddr, size);
        mem8.set(src);
    }
"""
)
external fun jsExportInt8ArrayToWasm(src: Int8Array, size: Int, dstAddr: Int)