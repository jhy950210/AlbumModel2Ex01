package model2;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model1.BoardDAO;
import model1.BoardTO;

public class BoardWriteOkAction implements BoardAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println( "BoardWriteOkAction" );

		String uploadPath = "C:/JSP/jsp-workspace/AlbumModel2Ex01/WebContent/upload";
		int maxFileSize = 1024 * 1024 * 5;
		String encType = "utf-8";
		
		
		boolean flag = false;
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest( request, uploadPath, maxFileSize, encType, new DefaultFileRenamePolicy());

			BoardTO to = new BoardTO();
				
			to.setSubject( multi.getParameter( "subject" ) );
			to.setWriter( multi.getParameter( "writer" ) );
			to.setMail( "" );
			if( !multi.getParameter( "mail1" ).equals("") && !multi.getParameter( "mail2" ).equals( "" ) ) {
				to.setMail( multi.getParameter( "mail1" ) + "@" + multi.getParameter( "mail2" ) );	
			}
			to.setPassword( multi.getParameter( "password" ) );
			to.setContent( multi.getParameter( "content" ) );
			to.setImage( multi.getFilesystemName( "upload" ) );
			to.setWip( request.getRemoteAddr() );

			BoardDAO dao = new BoardDAO();
			flag = dao.boardWriteOk( to );

			request.setAttribute( "flag", flag );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
