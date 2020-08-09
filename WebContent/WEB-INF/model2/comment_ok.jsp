<%@page import="model1.BoardDAO"%>
<%@page import="model1.CommentTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	boolean flag = (Boolean)request.getAttribute("flag");
	String seq = (String)request.getAttribute("seq");
	String cpage = (String)request.getAttribute("cpage");
	
	out.println("<script type='text/javascript'>");
	if( flag ){
		out.println("alert('댓글 작성에 성공했습니다.');");
		out.println("location.href='../board/view.do?cpage="+cpage+"&seq="+seq+"';");
	}else {
		out.println("alert('댓글 작성에 실패했습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
	
%>