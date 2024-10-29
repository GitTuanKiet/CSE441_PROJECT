package com.tuankiet.sample.features.admin.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.tuankiet.sample.R
import com.tuankiet.sample.core.platform.BaseFragment
import com.tuankiet.sample.features.admin.data.repositories.AgentRepository
import com.tuankiet.sample.features.admin.data.repositories.DateRespository
import com.tuankiet.sample.features.admin.data.repositories.MessageRepository
import com.tuankiet.sample.features.admin.data.repositories.UserRepository
import com.tuankiet.sample.features.admin.ui.viewmodel.AgentViewModel
import com.tuankiet.sample.features.admin.ui.viewmodel.DateViewModel
import com.tuankiet.sample.features.admin.ui.viewmodel.MessageViewModel
import com.tuankiet.sample.features.admin.ui.viewmodel.UserViewModel
import com.tuankiet.sample.features.admin.ui.viewmodel.UserViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class HomeAdminFragment : BaseFragment() {
    private lateinit var lineChart1 : LineChart
    private lateinit var lineChart2 : LineChart
    private lateinit var view : View
    private lateinit var  txtTotalUsers : TextView
    private lateinit var  txtTotalAgents : TextView
    private lateinit var  txtOnlineUsers : TextView
    private lateinit var  txtAverageResponseTime : TextView
    private val userRepository = UserRepository()
    private val agentRepository = AgentRepository()
    private val messsageRepository = MessageRepository()
    private val messageViewModel : MessageViewModel = MessageViewModel(messsageRepository)
    private val userviewModel: UserViewModel by viewModels { UserViewModelFactory(userRepository) }
    private val agentViewModel: AgentViewModel = AgentViewModel(agentRepository)
    private val dateRepository = DateRespository()
    private val dateViewModel = DateViewModel(dateRepository)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        view = inflater.inflate(R.layout.fragment_home_admin, container, false)
        Map()
        getData()


////        // Thiết lập gradient fill
////        Drawable drawable = getResources().getDrawable(R.drawable.gradient_fill);
////        lineDataSet.setFillDrawable(drawable);



        return view
    }
    fun Map(){
        txtTotalUsers = view.findViewById(R.id.totalUsers)
        txtTotalAgents = view.findViewById(R.id.totalAgents)
        txtOnlineUsers = view.findViewById(R.id.onlineUsers)
        txtAverageResponseTime = view.findViewById(R.id.averageresponsetime)
        lineChart1 = view.findViewById(R.id.lineChart1)
        lineChart2 = view.findViewById(R.id.lineChart2)
    }
    fun setupChart1(visitCounts: List<Pair<String, Long>>) {
        val entries = prepareChartData(visitCounts)
        val lineDataSet = LineDataSet(entries, "")
        lineDataSet.setColor(Color.BLUE)
        lineDataSet.setLineWidth(2f)
        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawValues(false)

        val lineData = LineData(lineDataSet)
        lineChart1.data = lineData
        lineChart1.description.isEnabled = false
        lineChart1.axisRight.isEnabled = false
        lineChart1.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart1.xAxis.setDrawGridLines(false)

        val leftAxis: YAxis = lineChart1.axisLeft
        leftAxis.axisMinimum = 0f // Đặt giá trị tối thiểu cho trục y
        leftAxis.axisMaximum = visitCounts.maxOfOrNull { it.second }?.toFloat() ?: 1f // Đặt giá trị tối đa cho trục y
        lineChart1.xAxis.setAvoidFirstLastClipping(true)

        val legend: Legend = lineChart1.legend
        legend.isEnabled = false
        lineChart1.setExtraOffsets(10f, 10f, 10f, 10f)
        lineChart1.invalidate()
    }
    fun setupChart2(visitCounts: List<Pair<String, Long>>) {
        val entries = prepareChartData(visitCounts)
        val lineDataSet = LineDataSet(entries, "")
        lineDataSet.setColor(Color.BLUE)
        lineDataSet.setLineWidth(2f)
        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawValues(false)

        val lineData = LineData(lineDataSet)
        lineChart2.data = lineData
        lineChart2.description.isEnabled = false
        lineChart2.axisRight.isEnabled = false
        lineChart2.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart2.xAxis.setDrawGridLines(false)

        val leftAxis: YAxis = lineChart2.axisLeft
        leftAxis.axisMinimum = 0f // Đặt giá trị tối thiểu cho trục y
        leftAxis.axisMaximum = visitCounts.maxOfOrNull { it.second }?.toFloat() ?: 1f // Đặt giá trị tối đa cho trục y
        lineChart2.xAxis.setAvoidFirstLastClipping(true)

        val legend: Legend = lineChart2.legend
        legend.isEnabled = false
        lineChart2.setExtraOffsets(10f, 10f, 10f, 10f)
        lineChart2.invalidate()
    }
    fun prepareChartData(visitCounts: List<Pair<String, Long>>): List<Entry> {
        val entries = mutableListOf<Entry>()

        for (i in visitCounts.indices) {
            val entry = Entry(i.toFloat(), visitCounts[i].second.toFloat()) // Chỉ số i là cột x, giá trị truy cập là cột y
            entries.add(entry)
        }

        return entries
    }
    fun getVisitCountsForMonth(callback: (List<Pair<String, Long>>) -> Unit) {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        val dateListExpected = generateDaysOfMonth(month, year)


        userviewModel.fetchListVisit()
        userviewModel.listVisit.observe(viewLifecycleOwner) { visits ->
            val dateFomat = SimpleDateFormat("dd", Locale.US)
            val dailyVisitMap = mutableMapOf<String, Long>()
            for (visit in visits) {
                for (userVisit in visit){
                    val date = dateFomat.format(userVisit)
                    dailyVisitMap[date] = dailyVisitMap.getOrDefault(date, 0L) + 1
                }
            }
            val result = dateListExpected.map { date ->
                date to (dailyVisitMap[date] ?: 0L)
            }
            callback(result)
        }
//        dateViewModel.fetchDateByMonth(month)
//        dateViewModel.date.observe(viewLifecycleOwner){ dateList->
//            dateList ?. let {
//                val visitCountsMap = mutableMapOf<String, Long>()
//                for(dateData in it ){
//                    visitCountsMap[dateData.date] = dateData.count
//                }
//                val result = dateListExpected.map { date ->
//                    date to (visitCountsMap[date] ?: 0L)
//                }
////                Log.d("loi" , result.toString())
//                callback(result)
//            }
//        }

    }
    fun getMessagesForMonth(callback: (List<Pair<String, Long>>) -> Unit){
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        val dateListExpected = generateDaysOfMonth(month, year)
        messageViewModel.fetchMessageByMonth(month , year)
        messageViewModel.messageCount.observe(viewLifecycleOwner){messageCount ->
//            Log.d("loi" , messageCount.toString())
            val result: List<Pair<String, Long>> = dateListExpected.map { date ->
                val day = date.toIntOrNull() ?: 0
                date to (messageCount[day]?.toLong() ?: 0L)
            }
//            Log.d("loi", "Danh sách ngày đã cập nhật: $result")
            callback(result)
        }
    }
    fun getAverageRes(){
        messageViewModel.fetchMessage()
        messageViewModel.message.observe(viewLifecycleOwner){ message ->
            message ?. let {
                val size = it.size / 2
                var timeRes: Long = 0L;
                for(value in  it){
                    timeRes += value.completionTime
                }
                val average = (timeRes / size).toFloat()
                txtAverageResponseTime.text = average.toString()
            }
        }
    }
    fun generateDaysOfMonth(month: Int, year: Int): List<String> {
        val dateFormat = SimpleDateFormat("dd", Locale.US)
        val calendar = Calendar.getInstance().apply {
            set(Calendar.MONTH, month - 1)
            set(Calendar.YEAR, year)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        return List(daysInMonth) {
            dateFormat.format(calendar.time).also { calendar.add(Calendar.DAY_OF_MONTH, 1) }
        }
    }
    fun getData(){
        userviewModel.fetchUsers()
        var totalUsers = 0
        var totalAgents = 0
        var onlineUsers = 0
        userviewModel.users.observe(viewLifecycleOwner) { users ->
           users?.let {
               totalUsers = it.size
               onlineUsers = it.count { user -> user.isOnline }
           }
            txtTotalUsers.text = totalUsers.toString()
            txtOnlineUsers.text = onlineUsers.toString()
        }
        agentViewModel.fetchAgent()
        agentViewModel.agents.observe(viewLifecycleOwner){ agents ->
            agents?.let{
                totalAgents = it.size
            }
            txtTotalAgents.text = totalAgents.toString()
        }

        getVisitCountsForMonth { visitCounts ->
            setupChart1(visitCounts)
        }
        getMessagesForMonth{ messageCount ->
            setupChart2(messageCount)
        }
        getAverageRes()
    }

}