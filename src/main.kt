data class EnDict(val current: String, val next: String?, val code: Int?)
data class DeDict(val code: Int?, val prev: String?, val output: String?)

fun enCode(str: String): List<Int?> {
    val enCodeList = mutableListOf<EnDict>()
    val enCodeMap = mutableMapOf<String, Int>()
    for(y in 0..126) {
        enCodeMap[y.toChar().toString()] = y
    }
    var y = 0
    for(i in str.indices) {
        if(i < y) {
            continue
        }
        var dictString = str[i].toString()
        for(x in i..str.length) {
            if(x < str.length - 1) {
                y = x + 1
                if(enCodeMap[dictString + str[x+1]] == null) {
                    enCodeMap[dictString + str[x+1]] = enCodeMap.size + 1
                    enCodeList.add(EnDict(dictString, str[x+1].toString(), enCodeMap[dictString]))
                    break
                } else {
                    if(x+1 == str.length - 1) {
                        enCodeList.add(EnDict(dictString + str[x+1], null, enCodeMap[dictString + str[x+1]]))
                    } else {
                        dictString += str[x + 1]
                    }
                }
            }
        }

    }

//    for(i in enCodeList) {
//        print("${i.code}, ")
//    }
    return enCodeList.map{
        it.code
    }
}

fun deCode(str: List<Int?>): String {
    val deCodeMap = mutableMapOf<Int, String>()
    val deCodeList = mutableListOf<DeDict>()
    for(y in 0..126) {
        deCodeMap[y] = y.toChar().toString()
    }
    deCodeList.add(DeDict(str[0], null, str[0]?.toChar().toString()))
    for(i in 1 until str.size) {
        deCodeList.add(DeDict(str[i], deCodeList[deCodeList.size - 1].output, deCodeMap[str[i]]))
        deCodeMap[deCodeMap.size + 1] = "${deCodeList[deCodeList.size - 2].output}${deCodeMap[str[i]]?.get(0)}"
    }

    return deCodeList.map{
        it.output
    }.joinToString(separator = "")
}

fun main() {
//    println("TOBEORNOTTOBEORTOBEORNOT")
    val a = enCode("sdasdsawdsadcadsarsasdsadadsdsdsdsdsdsdsdsdssd")
    println(a)
    println(deCode(a))
}