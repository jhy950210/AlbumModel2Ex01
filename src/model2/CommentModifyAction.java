package model2;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model1.BoardDAO;
import model1.CommentTO;

public class CommentModifyAction implements CommentAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String cSeq = request.getParameter("cSeq");
		String mode = request.getParameter("mode");
		CommentTO uComTo = new CommentTO();
		BoardDAO dao = new BoardDAO();
		
		
		uComTo.setSeq(request.getParameter("cSeq"));
		
		ArrayList<CommentTO> uLists = dao.commentModify(uComTo);
		
		request.setAttribute("uLists", uLists);
		request.setAttribute("mode", mode);
		request.setAttribute("cSeq", cSeq);
	}

}
