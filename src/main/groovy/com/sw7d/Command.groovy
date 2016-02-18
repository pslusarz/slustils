package com.sw7d

class Command {
    static Map<String, String> run(String command) {
        def proc = (command).execute()
        def out = new StringBuffer()
        def err = new StringBuffer()
        proc.consumeProcessOutput( out, err )
        proc.waitFor()
        Map<String, String> result = [:]
        result["out"] = out.toString()
        result["err"] = err.toString()
        println "Command: "+command
println "OUT:"
        println result.out
        println "ERR:"
        println result.err
        if (result.err?.contains("Could not resolve host")) {
            result["retryLater"] = true
        } else if (result.err?.contains("Connection reset by peer")) {
            result["retryLater"] = true
        } else if (result.err?.contains("Operation timed out")) {
            result["retryLater"] = true
        } else if (result.err?.contains("503 Service Unavailable")) {
            result["retryLater"] = true
        } else if (result.err?.contains("404 Not Found")) {
            result["moveOn"] = true
        }
        return result
    }
}
