import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.parser.Parser

object SimpleJsonGrammar : Grammar<Any?>() {
    @Suppress("unused")
    private val whiteSpace by token("[\r|\n]|\\s+", ignore = true)

    /* the regex "[^\\"]*(\\["nrtbf\\][^\\"]*)*" matches:
     * "               – opening double quote,
     * [^\\"]*         – any number of not escaped characters, nor double quotes
     * (
     *   \\["nrtbf\\]  – backslash followed by special character (\", \n, \r, \\, etc.)
     *   [^\\"]*       – and any number of non-special characters
     * )*              – repeating as a group any number of times
     * "               – closing double quote
     */
    private val stringLiteral by token("\"[^\\\\\"]*(\\\\[\"nrtbf\\\\][^\\\\\"]*)*\"")

    private val comma by token(",")
    private val colon by token(":")

    private val openingBrace by token("\\{")
    private val closingBrace by token("}")

    private val openingBracket by token("\\[")
    private val closingBracket by token("]")

    private val nullToken by token("\\bnull\\b")
    private val trueToken by token("\\btrue\\b")
    private val falseToken by token("\\bfalse\\b")

    private val num by token("-?[0-9]*(?:\\.[0-9]*)?")

    private val jsonNull: Parser<Any?> by nullToken asJust null
    private val jsonBool: Parser<Boolean> by (trueToken asJust true) or (falseToken asJust false)
    private val string: Parser<String> by (stringLiteral use { text.substring(1, text.lastIndex) })

    private val number: Parser<Double> by
        num use { text.toDouble() }

    private val jsonPrimitiveValue: Parser<Any?> by
        jsonNull or jsonBool or string or number

    private val jsonObject: Parser<Map<String, Any?>> by
        (-openingBrace * separated(string * -colon * this, comma, true) * -closingBrace)
            .map { mutableMapOf<String, Any?>().apply { it.terms.forEach { put(it.t1, it.t2) } } }

    private val jsonArray: Parser<List<Any?>> by
        (-openingBracket * separated(this, comma, true) * -closingBracket)
            .map { it.terms }

    override val rootParser by jsonPrimitiveValue or jsonObject or jsonArray
}

