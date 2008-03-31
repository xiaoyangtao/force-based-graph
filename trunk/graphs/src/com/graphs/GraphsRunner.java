package com.graphs;

import com.graphs.engine.data.GraphContener;
import com.graphs.engine.data.GraphLoader;
import com.graphs.graphicengine.GraphFrame;

public class GraphsRunner {

	public static void main(String[] args) {
		GraphFrame frame = new GraphFrame(GraphLoader.load());
	}

}
