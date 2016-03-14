package com.sw7d

import org.apache.commons.lang3.StringEscapeUtils

class RadomDocumentConstructor {
    static main(args) {
        int total = 0
        int classified = 0
        new File("../archive/text").eachFile { File file ->

            def classification = classify(file.name)
            if (classification.size() > 0) {
                classified++
                File xml = new File("../archive/xml/"+classification.file_name+".xml")
                xml.delete()
                xml << "<add><doc>"+System.properties['line.separator']
                classification.keySet().each {String key ->
                    xml << '  <field name = "'+key+'">'+classification[key]+"</field>"+System.properties['line.separator']
                }
                xml << '  <field name = "content"><![CDATA['+System.properties['line.separator']
                file.eachLine { String line ->
                    xml << StringEscapeUtils.escapeXml11(line)+System.properties['line.separator']
                }
                xml << '  ]]></field>'+System.properties['line.separator']
                xml << "</doc></add>"
            } else {
                println file.name
            }
            total++
        }
        println "total: " + total + ", classified: " + classified
    }

    static Map<String, Object> classify(String fileName) {
        Map<String, String> result = [:]
        if (fileName.toLowerCase().startsWith('zycie%20radomskie')) {
            def chunks = (fileName - '.txt').split('-')
            result.series_name = 'Zycie Radomskie'
            result.year = Integer.valueOf(chunks[1])
            result.index_in_year = Integer.valueOf(chunks[2])
            result.series_index = Integer.valueOf(""+chunks[1]+chunks[2].padLeft(5,'0'))
            result.id = 'Zycie Radomskie, rok: '+result.year+", nr. "+chunks[2]
            result.file_name = "zycie_radomskie_"+result.year+"_"+result.index_in_year
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            println result
        }

        return result
    }

}
