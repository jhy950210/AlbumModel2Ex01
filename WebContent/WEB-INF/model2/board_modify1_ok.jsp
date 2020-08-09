<%@page import="java.io.File"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model1.BoardDAO"%>
<%@page import="model1.BoardTO"%>

<%
	
	boolean flag = (Boolean)request.getAttribute("flag");
	String seq = (String)request.getAttribute("seq");
	String cpage = (String)request.getAttribute("cpage");
	
	out.println("<script type='text/javascript'>");
	if( flag ){
		out.println("alert('글수정에 성공했습니다.');");
		out.println("location.href='./view1.jsp?cpage="+cpage+"&seq="+seq+"';");
	}else {
		out.println("alert('비밀번호가 잘못되었습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
	
%>