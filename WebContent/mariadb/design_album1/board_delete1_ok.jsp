<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model1.BoardDAO"%>
<%@page import="model1.BoardTO"%>

<%
	request.setCharacterEncoding("utf-8");
	String cpage = request.getParameter("cpage");
	String seq = request.getParameter("seq");

	BoardTO to = new BoardTO();
	
	to.setSeq(request.getParameter("seq"));
	to.setPassword(request.getParameter("password"));
	
	BoardDAO dao = new BoardDAO();
	
	boolean flag = dao.boardDeleteOk(to);
	System.out.println(to.getSeq());
	
	out.println("<script type='text/javascript'>");
	if( flag ){
		out.println("alert('글삭제에 성공했습니다.');");
		out.println("location.href='./board_list1.jsp?cpage="+cpage+"&seq="+seq+"';");
	}else {
		out.println("alert('비밀번호가 잘못되었습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
	
%>