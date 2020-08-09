package model2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model1.BoardDAO;
import model1.BoardListTO;

public class BoardListAction implements BoardAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println( "BoardListAction" );
		
		int cpage = 1;
		if( request.getParameter( "cpage" ) != null && !request.getParameter("cpage").equals("") ) {
			cpage = Integer.parseInt( request.getParameter( "cpage" ) );
		}

		BoardListTO listTO = new BoardListTO();
		listTO.setCpage( cpage );
		
		BoardDAO dao = new BoardDAO();
		listTO = dao.boardList( listTO );
		
		request.setAttribute( "listTO", listTO );
	}

}
