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
	boolean flag = (Boolean)request.getAttribute("flag");
	
	out.println("<script type='text/javascript'>");
	if( flag ){
		out.println("alert('글쓰기에 성공했습니다.');");
		out.println("location.href='./list.do';");
	} else {
		out.println("alert('글쓰기에 실패했습니다.');");
		out.println("history.back();"); // 되돌아가기
	}
	out.println("</script>");
	
%>