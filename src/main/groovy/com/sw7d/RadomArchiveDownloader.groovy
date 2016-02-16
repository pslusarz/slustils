package com.sw7d

import groovy.util.logging.Log

@Log
class RadomArchiveDownloader {
    static main(args) {

        def errors = []
        def result
        (3808..5000).each { index ->
            try {
                String location = parseLocation(Command.run("curl http://bc.mbpradom.pl/Content/${index}/ -v").err)
                println index+"  "+location
                //println location.split('/').last()
                result = Command.run("curl $location -o archive/${location.split('/').last()}")
                println result.out
                println result.err
            } catch (Exception e) {
                e.printStackTrace()
                println ">>>> Error: "+index +"  "+e.message
                println result.err
                errors << index
            }
        }
        println "Done."
        println "Errors: "+errors

    }

    static String parseLocation(String messyLog) {
//println messyLog
        return messyLog.split(System.properties['line.separator']).find {it.contains("Location: ")}?.split("Location: ")[1]
    }


}
