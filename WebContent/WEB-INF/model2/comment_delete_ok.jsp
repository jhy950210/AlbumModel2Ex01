<%@page import="model1.CommentTO"%>
<%@page import="model1.BoardDAO"%>
<%@page import="model1.BoardTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	
	String cpage = request.getParameter("cpage");
	String seq = request.getParameter("seq");
	
	CommentTO comTo = new CommentTO();
	
	comTo.setPseq(request.getParameter("seq"));
	comTo.setSeq(request.getParameter("cSeq"));
	comTo.setPassword(request.getParameter("cpassword"));
	
	BoardDAO dao = new BoardDAO();
	
	boolean flag = dao.commentDeleteOk(comTo);
	
	out.println("<script type='text/javascript'>");
	if( flag ){
		out.println("alert('댓글 삭제에 성공했습니다.');");
		out.println("location.href='./view.do?cpage="+cpage+"&seq="+seq+"';");
	}else {
		out.println("alert('댓글 삭제에 실패했습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
	
%>