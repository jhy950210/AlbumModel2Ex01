package model2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model1.BoardDAO;
import model1.BoardTO;

public class BoardModifyAction implements BoardAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println( "BoardModifyAction" );
		
		String seq = request.getParameter("seq");
		String cpage = request.getParameter("cpage");
		
		BoardTO to = new BoardTO();
		to.setSeq(seq);
		
		BoardDAO dao = new BoardDAO();
		to = dao.boardModify(to);
		
		request.setAttribute("seq", seq);
		request.setAttribute("cpage", cpage);
		request.setAttribute("to", to);
	}

}
