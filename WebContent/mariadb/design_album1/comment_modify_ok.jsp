<%@page import="model1.BoardDAO"%>
<%@page import="model1.CommentTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");

	String cpage = request.getParameter("cpage");
	String seq = request.getParameter("seq");
	CommentTO comTo = new CommentTO();
	
	comTo.setSeq(request.getParameter("cSeq"));
	comTo.setPassword(request.getParameter("cpassword"));
	comTo.setContent(request.getParameter("ccontent"));
	comTo.setWriter(request.getParameter("cwriter"));
	
	
	BoardDAO dao = new BoardDAO();
	
	boolean flag = dao.commentModifyOk(comTo);
	
	out.println("<script type='text/javascript'>");
	if( flag ){
		out.println("alert('댓글 수정에 성공했습니다.');");
		out.println("location.href='./board_view1.jsp?cpage="+cpage+"&seq="+seq+"';");
	}else {
		out.println("alert('댓글 수정에 실패했습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
	
%>