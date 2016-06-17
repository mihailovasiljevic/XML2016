package controllers;

import play.*;
import play.libs.Files;
import play.mvc.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import models.*;

public class Application extends Controller {
	
	public static String projectPath;
	
	static {
    	File file = new File(".");
    	try {
			projectPath = file.getCanonicalPath().toString();
			System.out.println("projectPath " +  projectPath);
		} catch (IOException e) {
			System.out.println("Project path is not valid");
			e.printStackTrace();
		}
	}
	
	
    public static void index() {
        
    }
    
    public static void sendingAct(String uri) {
    	File file = new File(projectPath+"/XML2016/public/tmp/pdf/act_"+uri+".pdf");
    	File new_file = new File(projectPath+"/HistoryApp/acts/act_"+uri+".pdf");
    	Files.copy(file, new_file);
    	redirect("http://localhost:9000/#!/");
    }

}