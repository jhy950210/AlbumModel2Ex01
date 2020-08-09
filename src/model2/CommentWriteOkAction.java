package model2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model1.BoardDAO;
import model1.CommentTO;

public class CommentWriteOkAction implements CommentAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String seq = request.getParameter("seq");
		String cpage = request.getParameter("cpage");
		CommentTO comTo = new CommentTO();
		
		comTo.setPseq(request.getParameter("seq"));
		comTo.setPassword(request.getParameter("cpassword"));
		comTo.setContent(request.getParameter("ccontent"));
		comTo.setWriter(request.getParameter("cwriter"));
		
		BoardDAO dao = new BoardDAO();
		
		boolean flag = dao.commentWriteOk(comTo);
		
		request.setAttribute("flag", flag);
		request.setAttribute("seq", seq);
		request.setAttribute("cpage", cpage);
	}

}
