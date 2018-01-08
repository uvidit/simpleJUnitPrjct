package com.ozi.poc.travisMvnGce;


import jbehave.config.ConfiguredEmbedder;
import jbehave.steps.BasicSteps;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import java.util.List;

import java.util.logging.Logger;

import static java.util.Arrays.asList;

public class TestRunner {
  private static final Logger LOG = Logger.getLogger("TestRunner");


  public static void main(String[] args) throws Exception {
    LOG.info("\r\n ======================= \r\n **** Tests has been started.....");

    // Embedder defines the configuration and candidate steps
    Embedder embedder = new ConfiguredEmbedder(1, 500); // todo: refactor with DI
    embedder.candidateSteps().add(new BasicSteps());
    embedder.runStoriesAsPaths(storyPaths("*"));

    LOG.info("\r\n **** Tests has been finished \r\n ======================= ");
  }


  //////////////// additional string utils - need to refactor and move to separate class
  private static List <String> storyPaths(String storiesFilter) {
        storiesFilter = storiesFilter == null ? "*" : storiesFilter;

        String codeLocation = CodeLocations.codeLocationFromClass(BasicSteps.class).getPath();
        if(codeLocation.toLowerCase().contains(".jar")){
            codeLocation = split(codeLocation, ".jar")[0].concat(".jar");
        }
        return new StoryFinder().findPaths(codeLocation,
                asList("**/" + storiesFilter + ".story"),
                null
        );
  }

  public static String[] split(String toSplit, String delimiter) {
    if (hasLength(toSplit) && hasLength(delimiter)) {
      int offset = toSplit.indexOf(delimiter);
      if (offset < 0) {
        return null;
      } else {
        String beforeDelimiter = toSplit.substring(0, offset);
        String afterDelimiter = toSplit.substring(offset + delimiter.length());
        return new String[]{beforeDelimiter, afterDelimiter};
      }
    } else {
      return null;
    }
  }

  public static boolean hasLength(CharSequence str) {
    return str != null && str.length() > 0;
  }

  public static boolean hasLength(String str) {
    return hasLength((CharSequence)str);
  }

}
