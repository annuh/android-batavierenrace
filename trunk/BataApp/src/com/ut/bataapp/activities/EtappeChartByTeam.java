package com.ut.bataapp.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Etappe;
import com.actionbarsherlock.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class EtappeChartByTeam extends SherlockFragmentActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.chartbyteam);
		LinearLayout view = (LinearLayout) this.findViewById(R.id.chart);
		view.addView(this.execute(getApplicationContext()));
	}

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public View execute(Context context) {
    String[] titles = new String[] { "2008", "2007" };
    HashMap<Integer, Integer> tijden = api.getLooptijdenByEtappe(new Etappe(0));
    Integer aantal = tijden.size();
    double[] anderetijdenX = new double[aantal];
    double[] anderetijdenY = new double[aantal];
    double[] teamtijdX = new double[aantal];
    double[] teamtijdY = new double[aantal];
    int i = 0;
    for (Entry<Integer, Integer> entry : tijden.entrySet()) {
        Double key = (double)entry.getKey();
        Double value = (double)entry.getValue();
        anderetijdenX[i] = key;
        anderetijdenY[i] = value;
        i++;
    }

    teamtijdX[1] = 4;
    teamtijdY[1] = 4;
    List<double[]> valuesX = new ArrayList<double[]>();
    valuesX.add(anderetijdenX);
    valuesX.add(teamtijdX);
    List<double[]> valuesY = new ArrayList<double[]>();
    valuesY.add(anderetijdenY);
    valuesY.add(teamtijdY);
    
    
    //values.add(new double[] { 14230, 12300, 0, 15244, 15900, 19200, 22030, 21200, 19500, 15500,
    //    12600, 14000 });
    //values.add(new double[] { 0, 0, 9240, 10540, 7900, 9200, 12030, 11200, 9500, 10500,
    //    11600, 13500 });
  
    int[] colors = new int[] { Color.BLUE, Color.CYAN };
    XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
    setChartSettings(renderer, "", "Tijd (minuten)", "Aantal teams", 0.5,
        12.5, 0, 10, Color.RED, Color.LTGRAY);
    renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
    renderer.getSeriesRendererAt(1).setDisplayChartValues(true);
    renderer.setXLabels(12);
    renderer.setYLabels(10);
    renderer.setXLabelsAlign(Align.LEFT);
    renderer.setYLabelsAlign(Align.LEFT);
    renderer.setPanEnabled(true, false);
    // renderer.setZoomEnabled(false);
    renderer.setZoomRate(1.1f);
    renderer.setBarSpacing(0.5f);
    return ChartFactory.getBarChartView(context, buildBarDataset(titles, valuesX, valuesY), renderer,
        Type.STACKED);
  }
  
  protected XYMultipleSeriesDataset buildBarDataset(String[] titles, List<double[]> values) {
	    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	    int length = titles.length;
	    for (int i = 0; i < length; i++) {
	      CategorySeries series = new CategorySeries(titles[i]);
	      double[] v = values.get(i);
	      int seriesLength = v.length;
	      for (int k = 0; k < seriesLength; k++) {
	        series.add(v[k]);
	      }
	      dataset.addSeries(series.toXYSeries());
	      
	    }
	    return dataset;
	  }
  
  protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    int length = colors.length;
	    for (int i = 0; i < length; i++) {
	      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	      r.setColor(colors[i]);
	      renderer.addSeriesRenderer(r);
	    }
	    return renderer;
	  }
  
  protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
		String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
	    int labelsColor) {
	  	renderer.setChartTitle(title);
	    renderer.setXTitle(xTitle);
	    renderer.setYTitle(yTitle);
	    renderer.setXAxisMin(xMin);
	    renderer.setXAxisMax(xMax);
	    renderer.setYAxisMin(yMin);
	    renderer.setYAxisMax(yMax);
	    renderer.setAxesColor(Color.BLACK);
	    renderer.setLabelsColor(Color.BLACK);
	    renderer.setBackgroundColor(Color.WHITE);
	    renderer.setMarginsColor(Color.WHITE);
	    renderer.setBarSpacing(0.5f);
	    renderer.setPanEnabled(false);
	  }
  
  protected XYMultipleSeriesDataset buildBarDataset(String[] titles, List<double[]> valuesX, List<double[]> valuesY) {
	    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	    int length = titles.length;
	    for (int i = 0; i < length; i++) {
	      XYSeries xyseries = new XYSeries(titles[i]);
	      double[] x = valuesX.get(i);
	      double[] y = valuesY.get(i);
	      int seriesLength = x.length;
	      for (int k = 0; k < seriesLength; k++) {
	    	  xyseries.add(x[k], y[k]);
	    	  Log.v("test", Double.toString(x[k]));
	    	  Log.v("Y", Double.toString(y[k]));
	      }
	      dataset.addSeries(xyseries);
	    }
	    return dataset;
	  }

}
