package model2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model1.BoardDAO;
import model1.CommentTO;

public class CommentModifyOkAction implements CommentAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String cpage = request.getParameter("cpage");
		String seq = request.getParameter("seq");
		CommentTO comTo = new CommentTO();
		
		comTo.setSeq(request.getParameter("cSeq"));
		comTo.setPassword(request.getParameter("cpassword"));
		comTo.setContent(request.getParameter("ccontent"));
		comTo.setWriter(request.getParameter("cwriter"));
		
		
		BoardDAO dao = new BoardDAO();
		
		boolean flag = dao.commentModifyOk(comTo);
		
		request.setAttribute("flag", flag);
		request.setAttribute("cpage", cpage);
		request.setAttribute("seq", seq);
	}

}
