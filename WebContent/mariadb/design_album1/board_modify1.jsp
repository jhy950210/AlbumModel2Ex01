<%@page import="model1.BoardDAO"%>
<%@page import="model1.BoardTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	request.setCharacterEncoding("utf-8");
	
	String seq = request.getParameter("seq");
	String cpage = request.getParameter("cpage");
	
	BoardTO to = new BoardTO();
	to.setSeq(seq);
	
	BoardDAO dao = new BoardDAO();
	to = dao.boardModify(to);
	
	String subject = to.getSubject();
	String writer = to.getWriter();
	String mail = to.getMail();
	String content = to.getContent();
	
	String mail1 = mail.substring(0,mail.indexOf("@"));
	String mail2 = mail.substring(mail.indexOf("@") + 1);
	String filename = to.getImage();
	
	
	%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../../css/board_write.css">
<script type="text/javascript">
	window.onload = function() {
		document.getElementById('mbtn').onclick = function() {
			if( document.mfrm.password.value.trim() == '' ){
				alert('비밀번호를 입력하셔야 합니다.');
				return false;
			}
			document.mfrm.submit();
		};
	};
</script>
</head>

<body>
<!-- 상단 디자인 -->
<div class="contents1"> 
	<div class="con_title"> 
		<p style="margin: 0px; text-align: right">
			<img style="vertical-align: middle" alt="" src="../../images/home_icon.gif" /> &gt; 커뮤니티 &gt; <strong>여행지리뷰</strong>
		</p>
	</div> 

	<form action="./board_modify1_ok.jsp" method="post" name="mfrm" enctype="multipart/form-data">
	<input type="hidden" name="seq" value="<%=seq %>" />
		<div class="contents_sub">
		<!--게시판-->
			<div class="board_write">
				<table>
				<tr>
					<th class="top">글쓴이</th>
					<td class="top" colspan="3"><input type="text" name="writer" value="<%=writer %>" class="board_write_input_100" maxlength="5" readonly/></td>
				</tr>
				<tr>
					<th>제목</th>
					<td colspan="3"><input type="text" name="subject" value="<%=subject %>" class="board_write_input" /></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td colspan="3"><input type="password" name="password" value="" class="board_write_input_100"/></td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3">
						<textarea name="content" class="board_editor_area"><%=content %></textarea>
					</td>
				</tr>
				<tr>
					<th>파일첨부</th>
					<td colspan="3">
						기존 파일 : <%=filename %><br /><br />
						<input type="file" name="upload" value="" class="board_write_input" />
					</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td colspan="3"><input type="text" name="mail1" value="<%=mail1 %>" class="board_write_input_100"/> @ <input type="text" name="mail2" value="<%=mail2 %>" class="board_write_input_100"/></td>
				</tr>
				</table>
			</div>

			<div class="btn_area">
				<div class="align_left">			
					<input type="button" value="목록" class="btn_list btn_txt02" style="cursor: pointer;" onclick="location.href='board_list1.jsp?cpage=<%=cpage %>&seq=<%=seq %>'" />
					<input type="button" value="보기" class="btn_list btn_txt02" style="cursor: pointer;" onclick="location.href='board_view1.jsp?cpage=<%=cpage %>&seq=<%=seq %>'" />
				</div>
				<div class="align_right">			
					<input type="button" id="mbtn" value="수정" class="btn_write btn_txt01" style="cursor: pointer;" />
				</div>	
			</div>	
			<!--//게시판-->
		</div>
	</form>
</div>
<!-- 하단 디자인 -->

</body>
</html>
