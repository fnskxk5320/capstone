package CapProject.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CapProject.model.command.CommandHandler;

@WebServlet("/control")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String,CommandHandler> commandHandlerMap = new HashMap();
	
    public ControllerServlet() {
        super();
    }
    
    public void init() throws ServletException{
    	String configFile = getInitParameter("configFile");
    	Properties prop = new Properties();
    	String configFilePath = getServletContext().getRealPath(configFile);
    	try(FileReader fis = new FileReader(configFilePath)) {
    		prop.load(fis);
    	} catch(IOException e){
    		throw new ServletException(e);
    	}
    	Iterator keyIter = prop.keySet().iterator();
    	while(keyIter.hasNext()) {
    		String command = (String)keyIter.next();
    		String handlerClassName = prop.getProperty(command);
    		try {
    			Class<?> handlerClass = Class.forName(handlerClassName);
    			CommandHandler handlerInstance = (CommandHandler)handlerClass.newInstance();
    			commandHandlerMap.put(command,handlerInstance);
    		}catch(Exception e) {
    			throw new ServletException(e);
    		}
    	}
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}

	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String command = request.getRequestURI();
		if(command.indexOf(request.getContextPath()) == 0) {
			command = command.substring(request.getContextPath().length());
			if(command.contains("?")) {
				String[] strings = command.split("?");
				command = strings[0];
			}
		}else {
			command = command.substring(5);
		}
		CommandHandler handler = commandHandlerMap.get(command);
		boolean finish = false;
		try {
			 finish = handler.process(request, response);
		} catch(Exception e) {
			throw new ServletException(e);
		}
		
	}
}
