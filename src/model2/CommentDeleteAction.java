package model2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model1.BoardDAO;
import model1.CommentTO;

public class CommentDeleteAction implements CommentAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String cSeq = request.getParameter("cSeq");
		String mode = request.getParameter("mode");
		CommentTO dComTo = new CommentTO();
		BoardDAO dao = new BoardDAO();
		
		dComTo.setSeq(request.getParameter("cSeq"));
		
		dComTo= dao.commentDelete(dComTo);
		
		request.setAttribute("dComTo", dComTo);
		request.setAttribute("mode", mode);
		request.setAttribute("cSeq", cSeq);
	}

}
