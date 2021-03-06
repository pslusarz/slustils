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
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-")+"_"+result.year+"_"+result.index_in_year
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
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-")+"_" + result.year + "_" + result.index_in_year
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
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
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
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
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
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            //println result
        } else if (fileName.toLowerCase().startsWith("%C5%BBycie-".toLowerCase())) { //%C5%BBycie-1963-238.txt
            result.series_name = 'Życie Radomskie'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[1])
            result.index_in_year = Integer.valueOf(chunks[2])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
        } else if (fileName.toLowerCase().startsWith("195".toLowerCase())) { //1955-283.txt
            result.series_name = 'Życie Radomskie'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[0])
            result.index_in_year = Integer.valueOf(chunks[1])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
        } else if (fileName.toLowerCase().startsWith("C-4a-".toLowerCase())) {//C-4a-1947-1-0001-1.txt
            result.series_name = 'Życie Radomskie'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[2])
            result.index_in_year = Integer.valueOf(chunks[3].split("_")[0])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            println result
        } else if (fileName.toLowerCase().startsWith("C-5-".toLowerCase())) {//C-5-1940-1-0001-1.txt, C-5-1944-82a-0001-1.txt
            result.series_name = 'Dziennik Radomski'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[2])
            if (chunks[3] == '82a') {
                result.index_in_year = 500
            } else {
                result.index_in_year = Integer.valueOf(chunks[3])
            }
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
        } else if (fileName.toLowerCase().startsWith("C-6-".toLowerCase())) {//C-6-1944-1-0001-1.txt
            result.series_name = 'Dziennik Radomski'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[2])
            result.index_in_year = Integer.valueOf(chunks[3])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
        } else if (fileName.toLowerCase().startsWith("C-4-".toLowerCase())) {//C-4-1946-1-0001-1.txt
            result.series_name = 'Dziennik Powszechny'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[2])
            result.index_in_year = Integer.valueOf(chunks[3].split("_")[0])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            //println result
        } else if (fileName.toLowerCase().startsWith("Brzask-".toLowerCase())) {//Brzask-1917-21-22-00001
            result.series_name = 'Brzask'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[1])
            result.index_in_year = Integer.valueOf(chunks[2])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            //println result
        } else if (fileName.toLowerCase().startsWith("C-2-".toLowerCase())) {//C-2-1922-22.txt
            result.series_name = 'Brzask'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[2])
            result.index_in_year = Integer.valueOf(chunks[3].split("_")[0])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            //println result
        } else if (fileName.toLowerCase().startsWith("C-8-".toLowerCase())) {//C-8-1849-0001.txt
            result.series_name = 'Dodatek do Dziennika Gubernianego'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[2])
            result.index_in_year = Integer.valueOf(chunks[3].split("_")[0])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            //println result
        } else if (fileName.toLowerCase().startsWith("C-9-".toLowerCase())) {//C-9-1919-0001.txt
            result.series_name = 'Dziennik Urzędowy'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[2])
            result.index_in_year = Integer.valueOf(chunks[3].split("_")[0])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            println result
        } else if (fileName.toLowerCase().startsWith("C-10-".toLowerCase())) {//C-10-1936-0001.txt
            result.series_name = 'Dzień Dobry Ziemi Radomskiej'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[2])
            result.index_in_year = Integer.valueOf(chunks[3].split("_")[0])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            println result
        } else if (fileName.toLowerCase().startsWith("C-11-".toLowerCase())) {//C-11-1935-0001.txt
            result.series_name = 'Express Poranny'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[2])
            result.index_in_year = Integer.valueOf(chunks[3].split("_")[0])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            println result
        } else if (fileName.toLowerCase().startsWith("C-12-".toLowerCase())) {//C-11-1935-0001.txt
            result.series_name = 'Filareta'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[2])
            result.index_in_year = Integer.valueOf(chunks[3].split("_")[0])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            println result
        } else if (fileName.toLowerCase().startsWith("C-13-".toLowerCase())) {//C-13-1918-2-0001.txt
            result.series_name = 'Gazeta Kielecka'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[2])
            if (chunks[3] == 'dn') {
                result.index_in_year = 500
            } else {
                result.index_in_year = Integer.valueOf(chunks[3].split("_")[0])
            }
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            println result
        }  else if (fileName.toLowerCase().startsWith("C-28-".toLowerCase())) {//C-28-1940-3-0001.txt C-28-1940-5-d-0001
            result.series_name = 'Biuletyn Informacyjny Izby Przemysłowo-Handlowej dla Dystryktu Radomskiego'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[2])
            result.index_in_year = Integer.valueOf(chunks[3].split(/\(/)[0].split('_')[0])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            println result
        } else if (fileName.toLowerCase().startsWith("C-43-".toLowerCase())) {//C-43-1923-3-0001.txt C-28-1940-5-d-0001
            result.series_name = 'Słowo Radomskie i Kieleckie'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[2])
            result.index_in_year = Integer.valueOf(chunks[3].split(/\(/)[0].split('_')[0])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            println result
        }  else if (fileName.toLowerCase().startsWith("C-49-".toLowerCase())) {//C-43-1923-3-0001.txt C-28-1940-5-d-0001
            result.series_name = 'Ziemia Radomska'
            result.url_file_name = fileName - ".txt"
            result.original_file_extension = "djvu"
            def chunks = (fileName - ".txt").split("-")
            result.year = Integer.valueOf(chunks[2])
            result.index_in_year = Integer.valueOf(chunks[3].split(/\(/)[0].split('_')[0])
            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
            println result
        }
//        else if (fileName.toLowerCase().startsWith("C-56-".toLowerCase())) {//C-43-1923-3-0001.txt C-28-1940-5-d-0001
//            result.series_name = 'Dziennik Urzędowy Guberni Radomskiej'
//            result.url_file_name = fileName - ".txt"
//            result.original_file_extension = "djvu"
//            def chunks = (fileName - ".txt").split("-")
//            result.year = Integer.valueOf(chunks[2])
//            if (chunks[4]?.startsWith('d')) {
//                int sub = ['d', 'dn', 'd(I)', 'd(II)', "d(III)"].indexOf(chunks[4])
//                result.index_in_year = Integer.valueOf(chunks[3].split(/\(/)[0].split('_')[0]) * 10 + sub
//            } else {
//                result.index_in_year = Integer.valueOf(chunks[3].split(/\(/)[0].split('_')[0]) * 10
//            }
//            result.id = result.series_name + ", rok "+result.year+", nr. "+result.index_in_year
//            result.file_name = (result.series_name).toLowerCase().replaceAll(" ","-") + "_"+result.year+"_"+result.index_in_year
//            result.series_index = Long.valueOf(""+result.year+""+(result.index_in_year+"").padLeft(7,'0'))
//            println result
//        }
        return result
    }

}
