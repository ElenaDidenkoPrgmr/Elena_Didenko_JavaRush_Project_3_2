package com.javarush.eldidenko.servlet_quest.launch;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.EmptyResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final String MESSAGE_TOMCAT_STARTED_SUCCESSFUL = "Tomcat started successful, webPort: ";
    private static final String MESSAGE_APPLICATION_ROOT_FOLDER_SUCCESS = "application resolved root folder: ";
    private static final String MESSAGE_APPLICATION_ROOT_FOLDER_ERROR = "root folder doesnt resolved: ";

    private static File getRootFolder() {
        try {
            File root;
            String runningJarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replaceAll("\\\\", "/");
            int lastIndexOf = runningJarPath.lastIndexOf("/target/");
            if (lastIndexOf < 0) {
                root = new File("");
            } else {
                root = new File(runningJarPath.substring(0, lastIndexOf));
            }
            LOGGER.debug(MESSAGE_APPLICATION_ROOT_FOLDER_SUCCESS + root.getAbsolutePath());

            return root;
        } catch (URISyntaxException ex) {
            String errorMsg = MESSAGE_APPLICATION_ROOT_FOLDER_ERROR + ex;
            LOGGER.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
    }

    public static void main(String[] args) throws Exception {
        File root = getRootFolder();
        System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
        Tomcat tomcat = new Tomcat();
        Path tempPath = Files.createTempDirectory("tomcat-base-dir");
        tomcat.setBaseDir(tempPath.toString());

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.parseInt(webPort));
        tomcat.getConnector(); // Trigger the creation of the default connector
        File webContentFolder = new File(root.getAbsolutePath(), "src/main/webapp/");
        if (!webContentFolder.exists()) {
            webContentFolder = Files.createTempDirectory("default-doc-base").toFile();
        }
        StandardContext ctx = (StandardContext) tomcat.addWebapp("", webContentFolder.getAbsolutePath());
        //Set execution independent of current thread context classloader (compatibility with exec:java mojo)
        ctx.setParentClassLoader(Main.class.getClassLoader());

        LOGGER.debug("configuring app with basedir: " + webContentFolder.getAbsolutePath());

        // Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work
        File additionWebInfClassesFolder = new File(root.getAbsolutePath(), "target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);

        WebResourceSet resourceSet;
        if (additionWebInfClassesFolder.exists()) {
            resourceSet = new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClassesFolder.getAbsolutePath(), "/");
            LOGGER.debug("loading WEB-INF resources from as '{}'", additionWebInfClassesFolder.getAbsolutePath());
        } else {
            resourceSet = new EmptyResourceSet(resources);
        }
        resources.addPreResources(resourceSet);
        ctx.setResources(resources);

        tomcat.start();
        LOGGER.debug(MESSAGE_TOMCAT_STARTED_SUCCESSFUL + webPort);
        tomcat.getConnector();
        tomcat.getServer().await();
    }
}
