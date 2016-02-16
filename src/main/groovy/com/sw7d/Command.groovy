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
        def errors = result.err
        return result
    }
}
