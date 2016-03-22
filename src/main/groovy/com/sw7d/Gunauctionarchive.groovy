package com.sw7d

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Gunauctionarchive {
    static long done = 0
    static long hits = 0

    static def main(args) {
        int numberOfThreads = 10
        def radoms = new Vector<Long>()

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads)

        (1288000..14000000).each { //1253719 May 2, 2001
            executor.execute(new Runner(index: it, results: radoms))

        }
        long start = System.currentTimeMillis()
        executor.shutdown()
        while (!executor.terminated) {
            executor.awaitTermination(60, TimeUnit.SECONDS)
            if (radoms) {
                println "radoms: " + radoms.toString()
            }
            println "took: " + (System.currentTimeMillis() - start) + ", done: " + done + ", hits: " + hits
        }
        println "took: " + (System.currentTimeMillis() - start)
        println "radoms: " + radoms
    }


}

class Runner implements Runnable {
    def results
    long index

    @Override
    void run() {
        def result = Command.run("curl -v http://www.gunauction.com/buy/$index")
        String text = result.out.toLowerCase()
        if ((text.contains('radom') || text.contains('radum') || text.contains('radon') || text.contains('random'))
                && !(text.contains('mosin')) && !(text.contains('makarov'))
                && !(text.contains('tantal')) && !(text.contains('tokarev'))
                && !(text.contains('pps43'))
                && !(text.contains('czak'))
                && !(text.contains('bayonet'))
                && !(text.contains('beryl'))
                && !(text.contains('mag98'))
                && !(text.contains('archer'))
        ) {
            results << index
        }
        if (!result.err.contains('410 Content Removed')) {
            Gunauctionarchive.hits++
        }
        Gunauctionarchive.done++
    }
}
