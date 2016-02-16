package com.sw7d

/**
 * Created by ps on 2/15/16.
 */
class RadomArchiveProcessor {
    static main(args) {
        new File("archive").eachFile { File file ->
            if (file.name.endsWith("djvu")) {
                println file.name
            }

        }
    }
}
