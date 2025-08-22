package com.app.barcodescanner.scanner.domain

import org.app.gs1parser.GS1Scanner
import org.app.utils.ResultKt

class GS1ParserFactory {
    fun getParser(fnc1: String, gs: String): ResultKt<GS1Scanner, Exception> {
        return GS1Scanner.getDefaultParser(fnc1, gs)
    }
}