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
//                File xml = new File("../archive/xml/"+classification.file_name+".xml")
//                xml.delete()
//                xml << "<add><doc>"+System.properties['line.separator']
//                classification.keySet().each {String key ->
//                    xml << '  <field name = "'+key+'">'+classification[key]+"</field>"+System.properties['line.separator']
//                }
//                xml << '  <field name = "content"><![CDATA['+System.properties['line.separator']
//                file.eachLine { String line ->
//                    if (line.endsWith('¬') || line.endsWith('-')) {
//                        xml << StringEscapeUtils.escapeXml11(line.substring(0, line.length()-1))
//                    } else {
//                        xml << StringEscapeUtils.escapeXml11(line) + System.properties['line.separator']
//                    }
//                }
//                xml << '  ]]></field>'+System.properties['line.separator']
//                xml << "</doc></add>"
            } else {
                println file.name
            }
            total++
        }
        println "total: " + total + ", classified: " + classified
    }

    static Map<String, Object> classify(String fileName) {
        Map<String, String> result = [:]
        //println "  GFGFGFGF "+fileName
        if (fileName.toLowerCase().startsWith('zycie%20radomskie')) {
            def chunks = (fileName - '.txt').split('-')
            result.series_name = 'Życie Radomskie'
            result.year = Integer.valueOf(chunks[1])
            result.index_in_year = Integer.valueOf(chunks[2])
            result.series_index = Integer.valueOf(""+chunks[1]+chunks[2].padLeft(5,'0'))
            result.id = 'Życie Radomskie, rok: '+result.year+", nr. "+chunks[2]
            result.file_name = "zycie_radomskie_"+result.year+"_"+result.index_in_year
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            //println result
        } else if (fileName.toLowerCase().startsWith("wszech%c5%9bwiat-")) { //Wszech%C5%9Bwiat-1887-27-00001 or Wszech%C5%9Bwiat-1887-spis-00001
            def chunks = (fileName - '.txt').split('-')
            result.series_name = 'Wszechświat'
            result.year = Integer.valueOf(chunks[1])
            try {
                result.index_in_year = Integer.valueOf(chunks[2])
            } catch (NumberFormatException e) {
                result.index_in_year = 0
            }
            result.series_index = Integer.valueOf(""+chunks[1]+(result.index_in_year+"").padLeft(5,'0'))
            result.id = 'Wszechświat, rok: '+result.year+", nr. "+chunks[2]
            result.file_name = "wszechswiat_"+result.year+"_"+result.index_in_year
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            //println result
        } else if(fileName.toLowerCase().startsWith("%c5%bbycie%20radomskie-")) { //%C5%BBycie%20Radomskie-1967-nr68
            def chunks = (fileName - '.txt').split('-')
            result.series_name = 'Życie Radomskie'
            try {
                result.year = Integer.valueOf(chunks[1])
                result.index_in_year = Integer.valueOf(chunks[2] - 'nr')
            } catch (NumberFormatException e) {
                result.year = Integer.valueOf(chunks[1].split('%20')[0])
                result.index_in_year = Integer.valueOf(chunks[1].split('%20')[1])
            }
            result.series_index = Long.valueOf("" + result.year + "" + (result.index_in_year + "").padLeft(7, '0'))
            result.id = 'Życie Radomskie, rok: ' + result.year + ", nr. " + result.index_in_year
            result.file_name = "zycie_radomskie_" + result.year + "_" + result.index_in_year
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            //println result
        } else if (fileName.toLowerCase().startsWith("%C5%BBycie%20Radomskie%20nr%20".toLowerCase())) { //%C5%BBycie%20Radomskie%20nr%20258.txt
            result.series_name = 'Życie Radomskie'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            result.index_in_year = Integer.valueOf( (fileName - ".txt").split("%20")[3])
            result.year = 1951
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = result.series_name + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            //println result
        } else if(fileName.toLowerCase().startsWith("%c5%bbycie%20radomskie%20")) { //%C5%BBycie%20Radomskie%201963-248.txt
            result.series_name = 'Życie Radomskie'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("%20")[2].split('-')
            result.year = Integer.valueOf(chunks[0])
            result.index_in_year = Integer.valueOf(chunks[1])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = result.series_name + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            //println result
        } else if(fileName.toLowerCase().startsWith("%C5%BBycie%20Radomskie,%20".toLowerCase())) { //%C5%BBycie%20Radomskie,%201967,%20nr%2086-.txt
            result.series_name = 'Życie Radomskie'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split(",%20")
            result.year = Integer.valueOf(chunks[1])
            result.index_in_year = Integer.valueOf((chunks[2]-"nr%20")- '-')
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = result.series_name + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            //println result
        }
        return result
    }

}
