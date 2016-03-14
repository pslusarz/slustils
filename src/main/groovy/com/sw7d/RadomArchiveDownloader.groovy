package com.sw7d

import groovy.util.logging.Log

@Log
class RadomArchiveDownloader {
    static main(args) {

        def errors = []
        def result
        //(4243..6000).each { index ->
        //[4243, 4244, 4245, 4246, 4278, 4279, 4280, 4281, 4282, 4283, 4314, 4315, 4316, 4317, 4318, 4319, 4320, 4321, 4322, 4323, 4584, 4585, 4586, 4587, 4996, 4997, 5127, 5231, 5240, 5258, 5272, 5300, 5306, 5325].each {index ->
        //(15963..30000).each {index ->
        (32096..39000).collect{"$it".padLeft(4, "0")}.each {index ->

            try {
                result = Command.run("curl http://bc.mbpradom.pl/Content/${index}/ -v")
                while (result.retryLater) {
                    println "retrying..."
                    sleep(10000)
                    result = Command.run("curl http://bc.mbpradom.pl/Content/${index}/ -v")
                }
                if (!result.moveOn) {
                    String location = parseLocation(result.err)
                    println index + "  " + location
                    result = Command.run("curl $location -o archive/${location.split('/').last()}")
                    while (result.retryLater) {
                        println "retrying..."
                        sleep(10000)
                        result = Command.run("curl $location -o archive/${location.split('/').last()}")
                    }
                } else {
                    println "moving on... NOT FOUND"
                    errors << index
                }
            } catch (Exception e) {
                e.printStackTrace()
                println ">>>> Error: "+index +"  "+e.message
                println result?.err
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
