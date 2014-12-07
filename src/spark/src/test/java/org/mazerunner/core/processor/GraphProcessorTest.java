package org.mazerunner.core.processor;

import junit.framework.TestCase;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Test;
import org.mazerunner.core.algorithms;
import org.mazerunner.core.config.ConfigurationLoader;
import org.mazerunner.core.hdfs.FileUtil;
import org.mazerunner.core.models.ProcessorMessage;

public class GraphProcessorTest extends TestCase {

    @Test
    public void testProcessEdgeList() throws Exception {

        ConfigurationLoader.testPropertyAccess = true;

        String nodeList =
                "0 1\n" +
                "1 3\n" +
                "3 0";

        // Create test path
        String path = ConfigurationLoader.getInstance().getHadoopHdfsUri() + "/test/edgeList.txt";

        // Test writing the PageRank result to HDFS path
        FileUtil.writeListFile(path, nodeList);

        GraphProcessor.processEdgeList(new ProcessorMessage(path, GraphProcessor.TRIANGLE_COUNT));
    }

    @Test
    public void testShortestPaths() throws Exception {

        ConfigurationLoader.testPropertyAccess = true;

        String nodeList = "0 1\n" +
                "1 2\n" +
                "2 3\n" +
                "3 4\n" +
                "4 5";

        // Create test path
        String path = ConfigurationLoader.getInstance().getHadoopHdfsUri() + "/test/edgeList.txt";

        // Test writing the PageRank result to HDFS path
        FileUtil.writeListFile(path, nodeList);
        JavaSparkContext javaSparkContext = GraphProcessor.initializeSparkContext();
        String results = algorithms.shortestPath(javaSparkContext.sc(), path, 1, 5);
        System.out.println(results);
    }
}