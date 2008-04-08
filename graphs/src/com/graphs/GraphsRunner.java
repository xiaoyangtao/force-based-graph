package com.graphs;

import com.graphs.engine.data.GraphContener;
import com.graphs.engine.data.GraphLoader;
import com.graphs.engine.data.Settings;
import com.graphs.graphicengine.GraphFrame;

public class GraphsRunner {

	public static void main(String[] args) {
		Settings.XMLFileName = "graph.xml";
		GraphFrame frame = new GraphFrame(GraphLoader.load());
	}

}
