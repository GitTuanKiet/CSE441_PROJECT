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
import com.tuankiet.sample.features.admin.data.models.MessageModel
import com.tuankiet.sample.features.admin.data.repositorys.AgentRepository
import com.tuankiet.sample.features.admin.data.repositorys.DateRespository
import com.tuankiet.sample.features.admin.data.repositorys.MessageRepository
import com.tuankiet.sample.features.admin.data.repositorys.UserRepository
import com.tuankiet.sample.features.admin.ui.viewmodel.AgentViewModel
import com.tuankiet.sample.features.admin.ui.viewmodel.DateViewModel
import com.tuankiet.sample.features.admin.ui.viewmodel.MessageViewModel
import com.tuankiet.sample.features.admin.ui.viewmodel.UserViewModel
import com.tuankiet.sample.features.admin.ui.viewmodel.UserViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
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
//        // Tạo dữ liệu cho biểu đồ
//        lineChart1 = view.findViewById(R.id.lineChart1)
//        lineChart2 = view.findViewById(R.id.lineChart2)
//        val lineEntries: ArrayList<Entry> = ArrayList<Entry>()
//        lineEntries.add(Entry(1f, 100f))
//        lineEntries.add(Entry(2f, 120f))
//        lineEntries.add(Entry(3f, 150f))
//        lineEntries.add(Entry(4f, 130f))
//        lineEntries.add(Entry(5f, 170f))
//
//        // Tạo dataset từ các Entry
//        val lineDataSet: LineDataSet = LineDataSet(lineEntries, "")
//
//        // Tạo LineData và thêm vào LineChart
//        val lineData: LineData = LineData(lineDataSet)
//        lineChart1.setData(lineData)
//
//        // Cập nhật biểu đồ
//        lineChart1.invalidate() // làm mới biểu đồ để hiển thị
//        lineDataSet.setColor(Color.BLUE) // Đổi màu của đường
//        //        lineDataSet.setCircleColor(Color.RED); // Màu của các điểm trên đường
//        lineDataSet.setLineWidth(2f) // Độ dày của đường
//        //        lineDataSet.setCircleRadius(4f); // Kích thước điểm
//        lineDataSet.setDrawCircles(false) // Ẩn các điểm trên đường
//        lineDataSet.setDrawValues(false) // Ẩn giá trị tại các điểm
//        lineDataSet.setDrawFilled(true)
//        // Tùy chỉnh các thành phần khác của biểu đồ
//        lineChart1.getDescription().setEnabled(false) // Ẩn phần mô tả
//        lineChart1.getAxisRight().setEnabled(false) // Ẩn trục y bên phải
//        lineChart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM) // Đặt trục x ở dưới
//
//        // Xóa đường kẻ của trục x
//        lineChart1.getXAxis().setDrawGridLines(false) // Ẩn đường kẻ của trục x
//        lineChart1.getXAxis().setDrawLabels(true) // Hiển thị nhãn trục x
//
//        // Ẩn hình vẽ biểu thị (legend)
//        val legend: Legend = lineChart1.getLegend()
//        legend.setEnabled(false) // Ẩn legend
//
//        //        ArrayList<Entry> entries = new ArrayList<>();
////        entries.add(new Entry(0, 1));
////        entries.add(new Entry(1, 3));
////        entries.add(new Entry(2, 2));
////        entries.add(new Entry(3, 5));
////        entries.add(new Entry(4, 4));
////
////        LineDataSet lineDataSet = new LineDataSet(entries, "Sample Data");
////        lineDataSet.setColor(Color.BLUE);
////        lineDataSet.setLineWidth(2f);
////        lineDataSet.setDrawFilled(true);
////
////        // Thiết lập gradient fill
////        Drawable drawable = getResources().getDrawable(R.drawable.gradient_fill);
////        lineDataSet.setFillDrawable(drawable);
////
////        LineData lineData = new LineData(lineDataSet);
////        lineChart1.setData(lineData);
////        lineChart1.invalidate(); // Refresh biểu đồ
////
////
////        // Thiết lập trục Y
////        YAxis yAxis = lineChart1.getAxisLeft();
////        yAxis.setDrawGridLines(false); // Ẩn đường kẻ của trục Y
////        yAxis.setDrawLabels(true); // Hiển thị nhãn trục Y
////
////        YAxis rightAxis = lineChart1.getAxisRight();
////        rightAxis.setEnabled(false); // Ẩn trục Y bên phải
////        // Thiết lập trục X
////        lineChart1.getXAxis().setDrawGridLines(false); // Ẩn đường kẻ của trục X
////        lineChart1.getXAxis().setDrawLabels(true); // Hiển thị nhãn trục X
////        // Thiết lập legend
////        Legend legend = lineChart1.getLegend();
////        legend.setEnabled(true);
//
//        // Tạo dữ liệu cho biểu đồ
//        val lineEntries2: ArrayList<Entry> = ArrayList<Entry>()
//        lineEntries2.add(Entry(1f, 100f))
//        lineEntries2.add(Entry(2f, 120f))
//        lineEntries2.add(Entry(3f, 150f))
//        lineEntries2.add(Entry(4f, 130f))
//        lineEntries2.add(Entry(5f, 170f))
//
//        // Tạo dataset từ các Entry
//        val lineDataSet2: LineDataSet = LineDataSet(lineEntries2, "")
//
//        // Tạo LineData và thêm vào LineChart
//        val lineData2: LineData = LineData(lineDataSet)
//        lineChart2.setData(lineData2)
//
//        // Cập nhật biểu đồ
//        lineChart2.invalidate() // làm mới biểu đồ để hiển thị
//        lineDataSet.setColor(Color.BLUE) // Đổi màu của đường
//        //        lineDataSet.setCircleColor(Color.RED); // Màu của các điểm trên đường
//        lineDataSet.setLineWidth(2f) // Độ dày của đường
//        //        lineDataSet.setCircleRadius(4f); // Kích thước điểm
//        lineDataSet.setDrawCircles(false) // Ẩn các điểm trên đường
//        lineDataSet.setDrawValues(false) // Ẩn giá trị tại các điểm
//        lineDataSet.setDrawFilled(true)
//        // Tùy chỉnh các thành phần khác của biểu đồ
//        lineChart2.getDescription().setEnabled(false) // Ẩn phần mô tả
//        lineChart2.getAxisRight().setEnabled(false) // Ẩn trục y bên phải
//        lineChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM) // Đặt trục x ở dưới
//
//        // Xóa đường kẻ của trục x
//        lineChart2.getXAxis().setDrawGridLines(false) // Ẩn đường kẻ của trục x
//        lineChart2.getXAxis().setDrawLabels(true) // Hiển thị nhãn trục x
//
//        // Ẩn hình vẽ biểu thị (legend)
//        val legend2: Legend = lineChart1.getLegend()
//        legend2.setEnabled(true) // Ẩn legend



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
        dateViewModel.fetchDateByMonth(month)
        dateViewModel.date.observe(viewLifecycleOwner){ dateList->
            dateList ?. let {
                val visitCountsMap = mutableMapOf<String, Long>()
                for(dateData in it ){
                    visitCountsMap[dateData.date] = dateData.count
                }
                val result = dateListExpected.map { date ->
                    date to (visitCountsMap[date] ?: 0L)
                }
//                Log.d("loi" , result.toString())
                callback(result)
            }
        }

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