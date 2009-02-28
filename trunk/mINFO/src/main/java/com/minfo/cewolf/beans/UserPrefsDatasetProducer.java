package com.minfo.cewolf.beans;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.minfo.faces.beans.PoolListController;
import com.minfo.model.StatsPair;
import com.minfo.model.UserPrefs;
import com.minfo.model.UserStats;
import com.minfo.services.NewsFeeder;

import de.laures.cewolf.ChartPostProcessor;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.taglib.CewolfChartFactory;

public class UserPrefsDatasetProducer implements DatasetProducer {
	private static Logger log = Logger.getLogger(PoolListController.class);

	private final String[] categories = NewsFeeder.channels.keySet().toArray(
			new String[0]);
	private final String[] seriesNames = { "TAK", "NIE" };
	private List<UserPrefs> userPrefs;

	public String getProducerId() {
		return "UserStatsDatasetProducer";
	}

	public boolean hasExpired(Map arg0, Date since) {
		log.debug(getClass().getName() + "hasExpired()");
		return (System.currentTimeMillis() - since.getTime()) > 5000;

	}



	public void setUserPrefs(List<UserPrefs> userPrefs) {
		this.userPrefs = userPrefs;
	}


	public Object produceDataset(Map params) throws DatasetProduceException {
		log.debug("producing data.");
		DefaultPieDataset dataset = null;

		dataset = new DefaultPieDataset();
		for(UserPrefs up:userPrefs) {
			dataset.setValue(up.getTag().getName(),up.getPart()*100.0);
		}
		return dataset;

	}

	/*
	 * public void processChart(Object obj, Map arg1) { try { JFreeChart chart =
	 * (JFreeChart) obj; chart.getCategoryPlot().getRenderer().setSeriesPaint(0,
	 * Color.green); chart.getCategoryPlot().getRenderer().setSeriesPaint(1,
	 * Color.red);
	 *  } catch (Exception ex) { log.error(ex.getMessage(), ex); }
	 *  }
	 */
}
