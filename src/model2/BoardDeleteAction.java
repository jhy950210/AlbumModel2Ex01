package model2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model1.BoardDAO;
import model1.BoardTO;

public class BoardDeleteAction implements BoardAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println( "BoardDeleteAction" );
		
		BoardTO to = new BoardTO();
		String cpage = request.getParameter("cpage");
		String seq = request.getParameter("seq");
		
		to.setSeq(seq);
		
		BoardDAO dao = new BoardDAO();
		to = dao.boardDelete(to);
		
		request.setAttribute("to", to);
		request.setAttribute("cpage", cpage);
		request.setAttribute("seq", seq);
		
	}

}
