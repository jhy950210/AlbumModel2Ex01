package model2;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model1.BoardDAO;
import model1.BoardTO;
import model1.CommentTO;

public class BoardViewAction implements BoardAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println( "BoardViewAction" );
			
		BoardTO to = new BoardTO();
		to.setSeq( request.getParameter( "seq" ) );
		
		BoardDAO dao = new BoardDAO();
		to = dao.boardView( to );
		
		CommentTO cTo = new CommentTO();
		cTo.setPseq(request.getParameter( "seq" ));
		ArrayList<CommentTO> cLists = dao.commentList( cTo );
		
		request.setAttribute( "cpage", request.getParameter( "cpage" ) );
		request.setAttribute("seq", request.getParameter( "seq" ));
		request.setAttribute( "to", to );
		request.setAttribute( "cLists", cLists );
	}

}
