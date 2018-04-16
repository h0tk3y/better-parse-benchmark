# better-parse-benchmark

This repository contains benchmarks for [`better-parse`](https://github.com/h0tk3y/better-parse).

Currently there's only one benchmark:

### JSON 1K lines

The benchmark inputs have been taken here: https://sap.github.io/chevrotain/performance/

The JSON grammar is a stripped version of (silmeth/jsonParser)[https://github.com/silmeth/jsonParser]

Results on Intel(R) Core(TM) i7-7700HQ CPU @ 3.45 GHz:

```
Benchmark                                 Mode  Cnt    Score   Error  Units
betterParseBenchmark.JsonGrammar.json1k  thrpt   30  207.025 ± 1.975  ops/s
```

Feel free to contribute more benchmarks and comparison to other JVM parsing libraries.
