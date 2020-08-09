package model2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model1.BoardDAO;
import model1.BoardTO;

public class BoardDeleteOkAction implements BoardAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println( "BoardDeleteOkAction" );
		
		
		String cpage = request.getParameter("cpage");
		String seq = request.getParameter("seq");

		BoardTO to = new BoardTO();
		
		to.setSeq(request.getParameter("seq"));
		to.setPassword(request.getParameter("password"));
		
		BoardDAO dao = new BoardDAO();
		
		boolean flag = dao.boardDeleteOk(to);
		
		request.setAttribute("cpage", cpage);
		request.setAttribute("seq", seq);
		request.setAttribute("flag", flag);
	}

}
