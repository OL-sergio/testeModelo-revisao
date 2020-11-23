package ipca.am2.testemodelo_revisao

import java.util.*
import java.util.zip.DataFormatException

class TempRegist {

    var localName       : String? = null
    var date            : Date?   = null
    var temperature     : Double? = null

    constructor(localName: String?, date: Date?, temperature: Double?){
        this.localName = localName
        this.date      = date
        this.temperature    =   temperature
    }

}