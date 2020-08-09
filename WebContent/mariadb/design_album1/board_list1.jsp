<%@page import="model1.BoardListTO"%>
<%@page import="model1.BoardTO"%>
<%@page import="model1.BoardDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	request.setCharacterEncoding("utf-8");
		
	int cpage = 1;
	if( request.getParameter("cpage") != null && !request.getParameter("cpage").equals("")){
		cpage = Integer.parseInt(request.getParameter("cpage"));
	}
	
	BoardListTO listTO = new BoardListTO();
	listTO.setCpage(cpage);
	
	BoardDAO dao = new BoardDAO();
	listTO = dao.boardList(listTO);
	
	
	int totalRecord = listTO.getTotalRecord();
	int totalPage = listTO.getTotalPage();
	
	int blockPerPage = listTO.getBlockPerPage();
	int startBlock = listTO.getStartBlock();
	int endBlock = listTO.getEndBlock();
	
	ArrayList<BoardTO> boardLists = listTO.getBoardLists();
	
	StringBuffer strHtml = new StringBuffer();
	for( BoardTO list : boardLists){
		String seq = list.getSeq();
		String subject = list.getSubject();
		String writer = list.getWriter();
		String wdate = list.getWdate();
		int wgap = list.getWgap();
		String hit = list.getHit();
		String image = list.getImage();
		String cmt = list.getCmt();
		
	    strHtml.append("<td width='20%' class='last2'>");
	    strHtml.append("<div class='board'>");
	    strHtml.append("<table class='boardT'>");
	    strHtml.append("<tr>");
	    strHtml.append("<td class='boardThumbWrap'>");
	    strHtml.append("<div class='boardThumb'>");
	    strHtml.append("<a href='board_view1.jsp?cpage="+ cpage +"&seq=" + seq +"'><img src='../../upload/"+image+"' border='0' width='100%' /></a>");
	    strHtml.append("</div></td></tr><tr><td>");
	    strHtml.append("<div class='boardItem'>");
	    strHtml.append("<strong>"+subject+"</strong>");
	    strHtml.append("<span class='coment_number'><img src='../../images/icon_comment.png' alt='commnet'>"+cmt+"</span>");
	    if( wgap == 0 ){
	    	strHtml.append("<img src='../../images/icon_hot.gif' alt='HOT'>");
	    }
	    strHtml.append("</div></td></tr><tr>");
	    strHtml.append("<td><div class='boardItem'><span class='bold_blue'>"+writer+"</span></div></td>");
	    strHtml.append("</tr><tr>");
	    strHtml.append("<td><div class='boardItem'>"+wdate+"<font>|</font> Hit "+hit+"</div></td>");
	    strHtml.append("</tr></table></div></td>");
	  
	    
	}
	%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../../css/board_list.css">
<style type="text/css">
<!--
	.board_pagetab { text-align: center; }
	.board_pagetab a { text-decoration: none; font: 12px verdana; color: #000; padding: 0 3px 0 3px; }
	.board_pagetab a:hover { text-decoration: underline; background-color:#f2f2f2; }
	.on a { font-weight: bold; }
-->
</style>
</head>

<body>
<!-- 상단 디자인 -->
<div class="contents1"> 
	<div class="con_title"> 
		<p style="margin: 0px; text-align: right">
			<img style="vertical-align: middle" alt="" src="../../images/home_icon.gif" /> &gt; 커뮤니티 &gt; <strong>여행지리뷰</strong>
		</p>
	</div> 
	<div class="contents_sub">	
		<div class="board_top">
			<div class="bold">
				<p>총 <span class="txt_orange"><%=totalRecord %></span>건</p>
			</div>
		</div>	
		
		<!--게시판-->
		<table class="board_list">
			<tr>
			<%=strHtml %>
			</tr>
		</table>
		<!--//게시판-->	
		<div class="align_right">		
			<input type="button" value="쓰기" class="btn_write btn_txt01" style="cursor: pointer;" onclick="location.href='board_write1.jsp'" />
		</div>
		<div class="paginate_regular">
						<%
							if(startBlock == 1){
								out.println("<span><a>&lt;&lt;</a></span>");
							}else{
								out.println("<span><a href='board_list1.jsp?cpage="+ (startBlock - blockPerPage)+"'>&lt;&lt;</a></span>");
							}
							out.println("&nbsp;");
						%>
						<!--  <div align="absmiddle">
						-->
						<%
							if( cpage == 1 ){
								out.println("<span><a>&lt;</a></span>");
							}else{
								out.println("<span><a href='board_list1.jsp?cpage="+(cpage-1)+"'>&lt;</a></span>");
							}
							out.println("&nbsp;&nbsp;");
						%>
						
						
						<%
							for(int i=startBlock; i<=endBlock; i++){
								if( cpage == i ){
									out.println("<span><a>["+ i +"]</a></span>");
								}else{
									out.println("<span><a href='board_list1.jsp?cpage=" + i +"'>"+ i +"</a></span>");
								}
								
							}
						%>
					
						&nbsp;&nbsp;
						<%
							if( cpage == totalPage ){
								out.println("<span><a>&gt;</a></span>");
							}else{
								out.println("<span><a href='board_list1.jsp?cpage="+(cpage+1)+"'>&gt;</a></span>");
							}
							out.println("&nbsp;&nbsp;");
						%>
						<%
							if( endBlock == totalPage ){
								out.println("<span><a>&gt;&gt;</a></span>");
							}else{
								out.println("<span><a href='board_list1.jsp?cpage="+ (startBlock + blockPerPage)+"'>&gt;&gt;</a></span>");
							}
							out.println("&nbsp;");
						%>
						
					</div>
				</div>
		<!--//페이지넘버-->	
  	</div>
</div>
<!--//하단 디자인 -->

</body>
</html>
