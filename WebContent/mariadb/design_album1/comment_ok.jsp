<%@page import="model1.BoardDAO"%>
<%@page import="model1.CommentTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String seq = request.getParameter("seq");
	String cpage = request.getParameter("cpage");
	CommentTO comTo = new CommentTO();
	
	comTo.setPseq(request.getParameter("seq"));
	comTo.setPassword(request.getParameter("cpassword"));
	comTo.setContent(request.getParameter("ccontent"));
	comTo.setWriter(request.getParameter("cwriter"));
	
	BoardDAO dao = new BoardDAO();
	
	boolean flag = dao.commentWriteOk(comTo);
	
	out.println("<script type='text/javascript'>");
	if( flag ){
		out.println("alert('댓글 작성에 성공했습니다.');");
		out.println("location.href='./board_view1.jsp?cpage="+cpage+"&seq="+seq+"';");
	}else {
		out.println("alert('댓글 작성에 실패했습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
	
%>