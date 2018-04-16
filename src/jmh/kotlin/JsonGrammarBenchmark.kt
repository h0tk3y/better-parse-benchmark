package com.github.h0tk3y.betterParseBenchmark

import SimpleJsonGrammar
import com.github.h0tk3y.betterParse.grammar.parseToEnd
import jsonSample1K
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.infra.Blackhole

open class JsonGrammar {
    @Benchmark
    open fun json1k(bh: Blackhole?) {
        val parseToEnd = SimpleJsonGrammar.parseToEnd(jsonSample1K)
        bh?.consume(parseToEnd)
    }
}

fun main(args: Array<String>) {
    val j = JsonGrammar()
    for (i in 1..10000) {
        j.json1k(null)
    }
}