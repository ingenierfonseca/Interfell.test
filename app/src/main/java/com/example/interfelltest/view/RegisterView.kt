package com.example.interfelltest.view

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModelProviders
import com.example.interfelltest.R
import com.example.interfelltest.common.UtilDate
import com.example.interfelltest.viewmodel.RegisterViewModel
import com.example.interfelltest.viewmodel.RegisterViewModelFactory
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interfelltest.dblogic.entity.Contravention
import kotlinx.android.synthetic.main.layout_contravention.view.*
import android.widget.DatePicker
import android.app.DatePickerDialog
import java.util.*
import android.widget.TimePicker
import android.app.TimePickerDialog


class RegisterView : AppCompatActivity(), View.OnClickListener, IRegisterResultCallBack {
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var btnRegister: AppCompatButton
    private lateinit var progress: ProgressBar
    private lateinit var txtPlate: AppCompatEditText
    private lateinit var txtDate: AppCompatEditText
    private lateinit var txtTime: AppCompatEditText
    private lateinit var container_date: LinearLayout
    private lateinit var container_time: LinearLayout
    private lateinit var container_progress: RelativeLayout
    private lateinit var checkBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnRegister = findViewById(R.id.btnRegister)
        progress = findViewById(R.id.progress)
        txtPlate = findViewById(R.id.txtPlate)
        txtDate = findViewById(R.id.txtDate)
        txtTime = findViewById(R.id.txtTime)
        checkBox = findViewById(R.id.check)
        container_date = findViewById(R.id.container_date)
        container_time = findViewById(R.id.container_time)
        container_progress = findViewById(R.id.container_progress)

        registerViewModel = ViewModelProviders. of(this, RegisterViewModelFactory(applicationContext, this)).get(RegisterViewModel::class.java)

        btnRegister.setOnClickListener(this)
        container_date.setOnClickListener(this)
        container_time.setOnClickListener(this)

        val date = UtilDate.getDateLocal()
        txtDate.setText(date.substring(0, 10))
        txtTime.setText(date.substring(11, 19))
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.container_date -> showDate()
            R.id.container_time -> showTime()
            R.id.btnRegister -> register()
        }
    }

    override fun onRegister() {
        container_progress.visibility = View.GONE
    }

    fun showDate() {
        val cldr = Calendar.getInstance()
        val day = cldr.get(Calendar.DAY_OF_MONTH)
        val month = cldr.get(Calendar.MONTH)
        val year = cldr.get(Calendar.YEAR)
        // date picker dialog
        var picker = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                txtDate.setText(
                    year.toString() + "-" + (monthOfYear + 1) + "-" + fill(dayOfMonth)
                )
            }, year, month, day
        )

        picker.show()
    }

    fun showTime() {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                val newHour = fill(selectedHour)
                txtTime.setText(
                    "$newHour:$selectedMinute:00"
                )
            }, hour, minute, true
        )
        mTimePicker.show()
    }

    fun register() {
        if (!txtPlate.text!!.isEmpty()) {
            container_progress.visibility = View.VISIBLE
            registerViewModel.Register(
                txtPlate.text.toString(),
                txtDate.text.toString() + " " + txtTime.text.toString(),
                checkBox.check.isChecked
            )
        } else {
            Toast.makeText(applicationContext, getString(R.string.label_plate_hint), Toast.LENGTH_LONG).show()
        }
    }

    override fun LoadDetail(items: List<Contravention>, item: Contravention) {
        showDetail(items, item)
    }

    fun showDetail(items: List<Contravention>, item: Contravention) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val v = inflater.inflate(R.layout.layout_detail, null)
        builder.setView(v)

        val txtTitle = v.findViewById<TextView>(R.id.txtTitle)
        val txtLast = v.findViewById<TextView>(R.id.txtLast)
        val txtContravetion = v.findViewById<TextView>(R.id.txtContravetion)
        val details = v.findViewById<RecyclerView>(R.id.details)

        var cont = 0
        for(i in items) {
            if (i.isContravention)
                cont++
        }

        var last = "Hubo Contravencion"
        if (item.isContravention)
            last = "No $last"
        txtTitle.text = getString(R.string.label_detail_title) + " " + txtPlate.text
        txtLast.text = getString(R.string.label_detail_last) + last
        txtContravetion.text = getString(R.string.label_detail_count) + " " + cont

        val layoutManager = LinearLayoutManager(this)
        details.layoutManager = layoutManager
        details.adapter = ContraventionAdapter(items)
        //builder.create()
        builder.show()
    }

    private fun fill(day: Int): String {
        if (day <= 9)
            return "0$day"
        return day.toString()
    }
}