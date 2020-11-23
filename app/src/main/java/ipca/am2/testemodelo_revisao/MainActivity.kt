package ipca.am2.testemodelo_revisao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var tempRegistList : MutableList<TempRegist> = ArrayList()
    lateinit var  listViewTemp : ListView
    lateinit var  adapter: TempAdaper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempRegistList.add(TempRegist("Braga", Date(), 17.4))
        tempRegistList.add(TempRegist("Barcelos", Date(), 18.4))
        tempRegistList.add(TempRegist("Famalição", Date(), 16.4))
        tempRegistList.add(TempRegist("Guimarães", Date(), 14.4))


        listViewTemp = findViewById(R.id.listViewTemp)
        adapter = TempAdaper()
        listViewTemp.adapter = adapter


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         super.onCreateOptionsMenu(menu)
            menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_media -> {
                Toast.makeText(
                    this, "Temperatura Média:${media()}",
                    Toast.LENGTH_LONG
                ).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
       fun media() : Double {

        if (tempRegistList.size < 1) return 0.0
        var sum = 0.0
        tempRegistList.forEach {
            sum += it.temperature ?: 0.0
        }
        return sum / tempRegistList.size
    }


    inner class TempAdaper : BaseAdapter() {
        override fun getCount(): Int {
            return tempRegistList.size
        }

        override fun getItem(position: Int): Any {
            return tempRegistList[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_temp, viewGroup, false )
            val textViewLocalName = rowView.findViewById<TextView>(R.id.textViewDetailLocalName)
            val textViewTemperature = rowView.findViewById<TextView>(R.id.textViewTemperature)
            val buttonRemove = rowView.findViewById<Button>(R.id.buttonRemove)

            textViewLocalName.text = tempRegistList[position].localName
            textViewTemperature.text = String.format("%.02f",
                tempRegistList[position].temperature)

            rowView.isClickable = true
            rowView.setOnClickListener {
                val intent =  Intent (this@MainActivity, TempDetailActivity::class.java)
                intent.putExtra(TempDetailActivity.LOCAL_NAME   , tempRegistList[position].localName     )
                intent.putExtra(TempDetailActivity.DATE         , tempRegistList[position].date?.time    )
                intent.putExtra(TempDetailActivity.TEMPERATURE  , tempRegistList[position].temperature   )
                startActivity(intent)

            }

            buttonRemove.setOnClickListener {
                tempRegistList.removeAt(position)
                adapter.notifyDataSetChanged()
            }

            return rowView
        }
    }
}