<%@page import="java.io.File"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model1.BoardDAO"%>
<%@page import="model1.BoardTO"%>

<%
	String uploadPath = "C:/JSP/jsp-workspace/AlbumModel1Ex01/WebContent/upload";
	int maxFileSize = 1024 * 1024 * 5;
	String encType = "utf-8";
	
	MultipartRequest multi = new MultipartRequest( request, uploadPath, maxFileSize, encType, new DefaultFileRenamePolicy() );
	String cpage = multi.getParameter("cpage");
	String seq =multi.getParameter("seq");
	
	BoardTO to = new BoardTO();
	to.setSeq(multi.getParameter("seq"));
	
	to.setSubject(multi.getParameter("subject"));
	to.setWriter(multi.getParameter("writer"));
	to.setPassword(multi.getParameter("password"));
	to.setContent(multi.getParameter("content"));
	
	to.setWip(request.getRemoteAddr());
	// 후처리가 필요함
	to.setMail("");
	if(!multi.getParameter("mail1").equals("") && !multi.getParameter("mail2").equals("")) {
		to.setMail(multi.getParameter("mail1") + "@" + multi.getParameter("mail2"));
	}
	
	String newFilename = multi.getFilesystemName("upload");
	to.setImage(newFilename);
	
	File newFile = multi.getFile("upload");
	
	BoardDAO dao = new BoardDAO();
	
	boolean flag = dao.boardModifyOk(to);
	
	out.println("<script type='text/javascript'>");
	if( flag ){
		out.println("alert('글수정에 성공했습니다.');");
		out.println("location.href='./board_view1.jsp?cpage="+cpage+"&seq="+seq+"';");
	}else {
		out.println("alert('비밀번호가 잘못되었습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
	
%>