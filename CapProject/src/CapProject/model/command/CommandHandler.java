package CapProject.model.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandHandler {
	public boolean process(HttpServletRequest request, HttpServletResponse response);
}
