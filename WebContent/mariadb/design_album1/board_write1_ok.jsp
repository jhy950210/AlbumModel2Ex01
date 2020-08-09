<%@page import="model1.BoardTO"%>
<%@page import="model1.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="java.io.File" %>


<%
	String uploadPath ="C:/JSP/jsp-workspace/AlbumModel1Ex01/WebContent/upload";
	int maxFileSize = 1024 * 1024 * 5;
	String encType = "utf-8";
	
	MultipartRequest multi = new MultipartRequest(request, uploadPath, maxFileSize, encType, new DefaultFileRenamePolicy());
	
	BoardTO to = new BoardTO();
	
	to.setSubject(multi.getParameter("subject"));
	to.setWriter(multi.getParameter("writer"));
	to.setMail("");
	if( !multi.getParameter("mail1").equals("") && !multi.getParameter("mail2").equals("")){
		to.setMail(multi.getParameter("mail1") + "@" + multi.getParameter("mail2"));
	}
		
	
	to.setPassword(multi.getParameter("password"));
	to.setContent(multi.getParameter("content"));
	
	to.setImage(multi.getFilesystemName("upload"));
	
	to.setWip(request.getRemoteAddr());
	
	BoardDAO dao = new BoardDAO();
	boolean flag = dao.boardWriteOk(to);
	
	
	out.println("<script type='text/javascript'>");
	if( flag ){
		out.println("alert('글쓰기에 성공했습니다.');");
		out.println("location.href='./board_list1.jsp';");
	} else {
		out.println("alert('글쓰기에 실패했습니다.');");
		out.println("history.back();"); // 되돌아가기
	}
	out.println("</script>");
	
%>