package model2;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model1.BoardDAO;
import model1.BoardTO;

public class BoardModifyOkAction implements BoardAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println( "BoardModifyOkAction" );
		
		String uploadPath = "C:/JSP/jsp-workspace/AlbumModel1Ex01/WebContent/upload";
		int maxFileSize = 1024 * 1024 * 5;
		String encType = "utf-8";
		
		try {
			MultipartRequest multi = new MultipartRequest( request, uploadPath, maxFileSize, encType, new DefaultFileRenamePolicy() );
			String cpage = multi.getParameter("cpage");
			String seq =multi.getParameter("seq");
			
			BoardTO to = new BoardTO();
			to.setSeq(multi.getParameter("seq"));
			
			to.setSubject(multi.getParameter("subject"));
			to.setWriter(multi.getParameter("writer"));
			to.setPassword(multi.getParameter("password"));
			to.setContent(multi.getParameter("content"));
			
			to.setWip(request.getRemoteAddr());
			// 후처리가 필요함
			to.setMail("");
			if(!multi.getParameter("mail1").equals("") && !multi.getParameter("mail2").equals("")) {
				to.setMail(multi.getParameter("mail1") + "@" + multi.getParameter("mail2"));
			}
			
			String newFilename = multi.getFilesystemName("upload");
			to.setImage(newFilename);
			
			File newFile = multi.getFile("upload");
			
			BoardDAO dao = new BoardDAO();
			
			boolean flag = dao.boardModifyOk(to);
			
			request.setAttribute("flag", flag);
			request.setAttribute("seq", seq);
			request.setAttribute("cpage", cpage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
