package com.cebul.jez.controllers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.jdbc.JDBCPieDataset;
import org.jfree.util.Rotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cebul.jez.entity.Hist_Wyszuk;
import com.cebul.jez.service.HistService;
import com.cebul.jez.useful.JsonHist;
import com.cebul.jez.useful.JsonObject;

import java.sql.Connection;

@Controller
public class HistoryController {
	
	// próba

	@Autowired
	public HistService histService;

	@RequestMapping(value = "/panel/statystyki", method = RequestMethod.GET)
	public void stats(HttpServletResponse response, Model model) throws IOException {
		// return "statystyki";

	/*	response.setContentType("image/png");

		JDBCPieDataset pdata = createDataSet();

		JFreeChart chart = createChart(pdata, "Pie Chart");

		try {
			ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart,
					500, 300);
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "statystyki";
		
		*/
		
		Connection connection = null;
		try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try {
		connection=
		DriverManager.getConnection("jdbc:mysql://localhost/portal_aukcyjny?user=root&password=&useUnicode=true&characterEncoding=utf-8");
		} catch (SQLException e) {
		e.printStackTrace();
		}
		}catch (InstantiationException e) {
		e.printStackTrace();
		} catch (IllegalAccessException e) {
		e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
		e.printStackTrace();
		}
		JDBCPieDataset dataset = new JDBCPieDataset(connection);
		try {
		dataset.executeQuery("select k.nazwa,count(data) as ilosc from hist_wyszuk join kategorie k on idkat=k.id group by idkat");
		JFreeChart chart = ChartFactory.createPieChart
		         ("Najczęściej przeszukiwane kategorie", dataset, true, true, false);
		chart.setBorderPaint(Color.black);
		chart.setBorderStroke(new BasicStroke(10.0f));
		chart.setBorderVisible(true);
		if (chart != null) {
		int width = 500;
		int height = 350;
		final ChartRenderingInfo info = new ChartRenderingInfo
		                               (new StandardEntityCollection());
		response.setContentType("image/png");
		OutputStream out=response.getOutputStream();
		ChartUtilities.writeChartAsPNG(out, chart, width, height,info);
		}
		}catch (SQLException e) {
		e.printStackTrace();
		}
		
		
	}

	private JDBCPieDataset createDataSet() {
		// DefaultPieDataset dpd = new DefaultPieDataset();

		// dpd.setValue("Mac", 21);
		// dpd.setValue("Linux", 30);
		// dpd.setValue("Windows", 40);
		// dpd.setValue("Others", 9);

		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			try {
				connection = DriverManager
						.getConnection("jdbc:mysql://localhost/portal_aukcyjny?user=root&password=&useUnicode=true&characterEncoding=utf-8");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		JDBCPieDataset jdpd = new JDBCPieDataset(connection);

		try
		{
			jdpd.executeQuery("select data,idKat from hist_wyszuk");
		
		
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return jdpd;
	}

	private JFreeChart createChart(JDBCPieDataset pdata, String chartTitle) {
		
	
		JFreeChart chart = ChartFactory.createPieChart(chartTitle, pdata, true,
				true, false);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);

		return chart;
	}

	@RequestMapping(value = "/stats.json", method = RequestMethod.GET)
	public @ResponseBody
	JsonHist getStats(Model model) {
		List<Hist_Wyszuk> r = histService.getHistory();
		JsonHist jso = new JsonHist();
		jso.generateHistoria(r);

		return jso;
	}
}
