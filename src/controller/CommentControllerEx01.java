package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model2.CommentAction;
import model2.CommentModifyAction;
import model2.CommentModifyOkAction;
import model2.CommentWriteOkAction;

/**
 * Servlet implementation class BoardControllerEx01
 */
@WebServlet("/comment/*")
public class CommentControllerEx01 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding( "utf-8" );

			String path = request.getRequestURI().replaceAll( request.getContextPath() + "/comment", "" );
			
			String url = "/WEB-INF/views/error.jsp";
			CommentAction commentAction = null;
			
			if(path.equals("/modify_ok.do")) {
				 commentAction = new CommentModifyOkAction();
				 commentAction.execute(request, response);
				 
				 url = "/WEB-INF/model2/comment_modify_ok.jsp";
			} else if(path.equals("/write_ok.do")) {
				 commentAction = new CommentWriteOkAction();
				 commentAction.execute(request, response);
				
				 url = "/WEB-INF/model2/comment_ok.jsp";
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher( url );
			dispatcher.forward(request, response);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
