package com.tuankiet.sample.features.admin.ui.fragments

import android.graphics.Color
import android.os.Bundle

import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.tuankiet.sample.R
import com.tuankiet.sample.core.platform.BaseFragment


class HomeAdminFragment : BaseFragment() {
    private lateinit var lineChart1 : LineChart
    private lateinit var lineChart2 : LineChart
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_home_admin, container, false)
        // Tạo dữ liệu cho biểu đồ
        lineChart1 = view.findViewById(R.id.lineChart1)
        lineChart2 = view.findViewById(R.id.lineChart2)
        val lineEntries: ArrayList<Entry> = ArrayList<Entry>()
        lineEntries.add(Entry(1f, 100f))
        lineEntries.add(Entry(2f, 120f))
        lineEntries.add(Entry(3f, 150f))
        lineEntries.add(Entry(4f, 130f))
        lineEntries.add(Entry(5f, 170f))

        // Tạo dataset từ các Entry
        val lineDataSet: LineDataSet = LineDataSet(lineEntries, "")

        // Tạo LineData và thêm vào LineChart
        val lineData: LineData = LineData(lineDataSet)
        lineChart1.setData(lineData)

        // Cập nhật biểu đồ
        lineChart1.invalidate() // làm mới biểu đồ để hiển thị
        lineDataSet.setColor(Color.BLUE) // Đổi màu của đường
        //        lineDataSet.setCircleColor(Color.RED); // Màu của các điểm trên đường
        lineDataSet.setLineWidth(2f) // Độ dày của đường
        //        lineDataSet.setCircleRadius(4f); // Kích thước điểm
        lineDataSet.setDrawCircles(false) // Ẩn các điểm trên đường
        lineDataSet.setDrawValues(false) // Ẩn giá trị tại các điểm
        lineDataSet.setDrawFilled(true)
        // Tùy chỉnh các thành phần khác của biểu đồ
        lineChart1.getDescription().setEnabled(false) // Ẩn phần mô tả
        lineChart1.getAxisRight().setEnabled(false) // Ẩn trục y bên phải
        lineChart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM) // Đặt trục x ở dưới

        // Xóa đường kẻ của trục x
        lineChart1.getXAxis().setDrawGridLines(false) // Ẩn đường kẻ của trục x
        lineChart1.getXAxis().setDrawLabels(true) // Hiển thị nhãn trục x

        // Ẩn hình vẽ biểu thị (legend)
        val legend: Legend = lineChart1.getLegend()
        legend.setEnabled(false) // Ẩn legend

        //        ArrayList<Entry> entries = new ArrayList<>();
//        entries.add(new Entry(0, 1));
//        entries.add(new Entry(1, 3));
//        entries.add(new Entry(2, 2));
//        entries.add(new Entry(3, 5));
//        entries.add(new Entry(4, 4));
//
//        LineDataSet lineDataSet = new LineDataSet(entries, "Sample Data");
//        lineDataSet.setColor(Color.BLUE);
//        lineDataSet.setLineWidth(2f);
//        lineDataSet.setDrawFilled(true);
//
//        // Thiết lập gradient fill
//        Drawable drawable = getResources().getDrawable(R.drawable.gradient_fill);
//        lineDataSet.setFillDrawable(drawable);
//
//        LineData lineData = new LineData(lineDataSet);
//        lineChart1.setData(lineData);
//        lineChart1.invalidate(); // Refresh biểu đồ
//
//
//        // Thiết lập trục Y
//        YAxis yAxis = lineChart1.getAxisLeft();
//        yAxis.setDrawGridLines(false); // Ẩn đường kẻ của trục Y
//        yAxis.setDrawLabels(true); // Hiển thị nhãn trục Y
//
//        YAxis rightAxis = lineChart1.getAxisRight();
//        rightAxis.setEnabled(false); // Ẩn trục Y bên phải
//        // Thiết lập trục X
//        lineChart1.getXAxis().setDrawGridLines(false); // Ẩn đường kẻ của trục X
//        lineChart1.getXAxis().setDrawLabels(true); // Hiển thị nhãn trục X
//        // Thiết lập legend
//        Legend legend = lineChart1.getLegend();
//        legend.setEnabled(true);

        // Tạo dữ liệu cho biểu đồ
        val lineEntries2: ArrayList<Entry> = ArrayList<Entry>()
        lineEntries2.add(Entry(1f, 100f))
        lineEntries2.add(Entry(2f, 120f))
        lineEntries2.add(Entry(3f, 150f))
        lineEntries2.add(Entry(4f, 130f))
        lineEntries2.add(Entry(5f, 170f))

        // Tạo dataset từ các Entry
        val lineDataSet2: LineDataSet = LineDataSet(lineEntries2, "")

        // Tạo LineData và thêm vào LineChart
        val lineData2: LineData = LineData(lineDataSet)
        lineChart2.setData(lineData2)

        // Cập nhật biểu đồ
        lineChart2.invalidate() // làm mới biểu đồ để hiển thị
        lineDataSet.setColor(Color.BLUE) // Đổi màu của đường
        //        lineDataSet.setCircleColor(Color.RED); // Màu của các điểm trên đường
        lineDataSet.setLineWidth(2f) // Độ dày của đường
        //        lineDataSet.setCircleRadius(4f); // Kích thước điểm
        lineDataSet.setDrawCircles(false) // Ẩn các điểm trên đường
        lineDataSet.setDrawValues(false) // Ẩn giá trị tại các điểm
        lineDataSet.setDrawFilled(true)
        // Tùy chỉnh các thành phần khác của biểu đồ
        lineChart2.getDescription().setEnabled(false) // Ẩn phần mô tả
        lineChart2.getAxisRight().setEnabled(false) // Ẩn trục y bên phải
        lineChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM) // Đặt trục x ở dưới

        // Xóa đường kẻ của trục x
        lineChart2.getXAxis().setDrawGridLines(false) // Ẩn đường kẻ của trục x
        lineChart2.getXAxis().setDrawLabels(true) // Hiển thị nhãn trục x

        // Ẩn hình vẽ biểu thị (legend)
        val legend2: Legend = lineChart1.getLegend()
        legend2.setEnabled(false) // Ẩn legend

        return view
    }
}