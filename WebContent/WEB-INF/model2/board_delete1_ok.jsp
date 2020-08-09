<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model1.BoardDAO"%>
<%@page import="model1.BoardTO"%>

<%
	String cpage = (String)request.getAttribute("cpage");
	String seq = (String)request.getAttribute("seq");
	Boolean flag = (Boolean)request.getAttribute("flag");
	
	out.println("<script type='text/javascript'>");
	if( flag ){
		out.println("alert('글삭제에 성공했습니다.');");
		out.println("location.href='./list.do?cpage="+cpage+"&seq="+seq+"';");
	}else {
		out.println("alert('비밀번호가 잘못되었습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
	
%>