package model1;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BoardDAO {
	private DataSource dataSource = null;
	
	public BoardDAO() {
		// TODO Auto-generated constructor stub
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			this.dataSource = (DataSource)envCtx.lookup("jdbc/mariadb1");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			System.out.println("[에러]" + e.getMessage());
		}
	}
	
	public ArrayList<BoardTO> boardList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		
		ArrayList<BoardTO> lists = new ArrayList<BoardTO>();
		try{
		    conn = this.dataSource.getConnection();
		    
		    String sql = "select seq, subject, writer, image, DATE(wdate) wdate, hit, datediff(now(), wdate) wgap from album_board1 order by seq desc";
		    pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // 총 게시글 수 알기 위해
		    
		    rs = pstmt.executeQuery();
		   
		    
		    while(rs.next()){
		    	BoardTO to = new BoardTO();
		    	
		    	to.setSeq(rs.getString("seq"));
		    	to.setSubject(rs.getString("subject"));
		    	to.setImage(rs.getString("image"));
		    	to.setWriter(rs.getString("writer"));
		    	to.setWdate(rs.getString("wdate"));
		    	to.setHit(rs.getString("hit"));
		    	to.setWgap(rs.getInt("wgap"));
		    	
		    	lists.add(to);
		    } 
		
		}catch( SQLException e) {
		   System.out.println(" [에러] : " + e.getMessage());
		}finally {
		   if( pstmt != null ) try{pstmt.close();} catch(SQLException e) {}
		   if( conn != null ) try{conn.close();} catch(SQLException e) {}
		   if( rs != null ) try{rs.close();} catch(SQLException e) {}
		}
		
		return lists;
	}
	
	public BoardListTO boardList(BoardListTO listTO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int cpage = listTO.getCpage();
		int recordPerPage = listTO.getRecordPerPage();
		int blockPerPage = listTO.getBlockPerPage();
		
		try{
		    conn = this.dataSource.getConnection();
		    
		    String sql = "select seq, subject, writer, image, DATE(wdate) wdate, hit, datediff(now(), wdate) wgap, cmt from album_board1 order by seq desc";
		    pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // 총 게시글 수 알기 위해
		    
		    rs = pstmt.executeQuery();
		   
		    rs.last();
		    listTO.setTotalRecord(rs.getRow());
		    rs.beforeFirst();
		    
		    listTO.setTotalPage(((listTO.getTotalRecord()-1)/recordPerPage)+1);
		    
		    int skip = ((cpage-1)* recordPerPage);
		    if(skip != 0) rs.absolute( skip );
		    
		    ArrayList<BoardTO> lists = new ArrayList<BoardTO>();
		    for( int i=0; i<recordPerPage && rs.next(); i++ ){
		    	BoardTO to = new BoardTO();
		    	
		    	to.setSeq(rs.getString("seq"));
		    	to.setSubject(rs.getString("subject"));
		    	to.setWriter(rs.getString("writer"));
		    	to.setImage(rs.getString("image"));
		    	to.setWdate(rs.getString("wdate"));
		    	to.setHit(rs.getString("hit"));
		    	to.setWgap(rs.getInt("wgap"));
		    	to.setCmt(rs.getString("cmt"));
		    	
		    	lists.add(to);
		    } 
		
		    listTO.setBoardLists(lists);
		    
		    listTO.setStartBlock(((cpage-1) / blockPerPage) * blockPerPage + 1);
		    listTO.setEndBlock(((cpage-1) / blockPerPage) * blockPerPage + blockPerPage);
		    
		    if( listTO.getEndBlock() >= listTO.getTotalPage() ) {
		    	listTO.setEndBlock(listTO.getTotalPage());
		    }
		}catch( SQLException e) {
		   System.out.println(" [에러] : " + e.getMessage());
		}finally {
		   if( pstmt != null ) try{pstmt.close();} catch(SQLException e) {}
		   if( conn != null ) try{conn.close();} catch(SQLException e) {}
		   if( rs != null ) try{rs.close();} catch(SQLException e) {}
		}
		
		return listTO;
	}

	public BoardTO boardView(BoardTO to) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
		    conn = this.dataSource.getConnection();
		    
		    String sql = "update album_board1 set hit=hit+1 where seq=?" ; // 댓글 수
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setString(1, to.getSeq());
		    
		    pstmt.executeUpdate();
		    pstmt.close();
		    
		    sql = "select subject, writer, image, mail, wip, wdate, hit, content from album_board1 where seq=?";
		    pstmt = conn.prepareStatement(sql);
		    
		    pstmt.setString(1, to.getSeq());
		    rs = pstmt.executeQuery();
		    
		    if(rs.next()){
		    	to.setSubject(rs.getString("subject"));
		    	to.setWriter(rs.getString("writer"));
		    	to.setMail(rs.getString("mail"));
		    	to.setImage(rs.getString("image"));
		    	to.setWip(rs.getString("wip"));
		    	to.setWdate(rs.getString("wdate"));
		    	to.setHit(rs.getString("hit"));
		    	to.setContent(rs.getString("content").replaceAll("\n","<br />")); // 줄 바꿈 보여주기
		    	
		    	}
		    
	
		}catch( SQLException e) {
		 	System.out.println(" [에러] : " + e.getMessage());
		}finally {
			if( pstmt != null ) try{pstmt.close();} catch(SQLException e) {}
			if( conn != null ) try{conn.close();} catch(SQLException e) {}
			if( rs != null ) try{rs.close();} catch(SQLException e) {}
		}
		return to;
	}
	
	public boolean boardWriteOk(BoardTO to) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
		boolean flag = false;
		
		try{
		      conn = this.dataSource.getConnection();
		      
		      String sql = "insert into album_board1 values(0, ?, ?, ?, ?, ?, ?, 0, ?, now(), 0)";//subject, writer, mail, password, content, wip
		      pstmt = conn.prepareStatement(sql);
		      
		      pstmt.setString(1, to.getSubject());
		      pstmt.setString(2, to.getWriter());
		      pstmt.setString(3, to.getMail());
		      pstmt.setString(4, to.getPassword());
		      pstmt.setString(5, to.getImage());
		      pstmt.setString(6, to.getContent());
		      pstmt.setString(7, to.getWip());
		      
		      int result = pstmt.executeUpdate();
		      if(result == 1){
		    	  flag = true;
		      }
		      
		   } catch( SQLException e) {
		      System.out.println(" [에러] : " + e.getMessage());
		   } finally {
		      if( pstmt != null ) try{pstmt.close();} catch(SQLException e) {}
		      if( conn != null ) try{conn.close();} catch(SQLException e) {}
		   }
		return flag;
	}
	
	public BoardTO boardDelete(BoardTO to) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
		    conn = this.dataSource.getConnection();
		    
		    String sql = "select subject, writer from album_board1 where seq=?";
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setString(1, to.getSeq());
		    
		    rs = pstmt.executeQuery();
		    
		    if(rs.next()){
		    	to.setSubject(rs.getString("subject"));
		    	to.setWriter(rs.getString("writer"));
		    }
		   
		}catch( SQLException e) {
			 System.out.println(" [에러] : " + e.getMessage());
		}finally {
			if( pstmt != null ) try{pstmt.close();} catch(SQLException e) {}
			if( conn != null ) try{conn.close();} catch(SQLException e) {}
			if( rs != null ) try{rs.close();} catch(SQLException e) {}
		}
		return to;
	}
	
	public boolean boardDeleteOk(BoardTO to) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean flag = false;
		
		try{
			
		      conn = this.dataSource.getConnection();
		      
		      String filename = "";
		      String sql1 = "select image from album_board1 where seq=?";
			  pstmt = conn.prepareStatement(sql1);
			    
			  pstmt.setString(1, to.getSeq());
			  rs = pstmt.executeQuery();
			  
			  if(rs.next()){
			    filename = rs.getString("image");
			    
		      String sql2 = "delete from album_board1 where seq=? and password=?";
		      pstmt = conn.prepareStatement(sql2);
		      pstmt.setString(1, to.getSeq());
		      pstmt.setString(2, to.getPassword());
		      
		      int result = pstmt.executeUpdate();
		  
		      
		      if(result == 0){
		    	  // 비밀번호를 잘못 기입
		    	  flag = false;
		      }else if(result == 1){
		    	  // 정상
		    	  flag = true;
		    	  
		    	  if( filename != null ){
		    		  // 파일 삭제
		    		  File file = new File("C:/JSP/jsp-workspace/AlbumModel1Ex01/WebContent/upload/" + filename);
		    		  file.delete();
		    	  }
		    	  String sql3 = "delete from album_comment1 where pseq=?";
			      pstmt = conn.prepareStatement(sql3);
			      pstmt.setString(1, to.getSeq());
			      
			      pstmt.executeUpdate();
		      }
		      
			  }
		   } catch( SQLException e) {
		      System.out.println(" [에러] : " + e.getMessage());
		   } finally {
			   if( pstmt != null ) try{pstmt.close();} catch(SQLException e) {}
			   if( conn != null ) try{conn.close();} catch(SQLException e) {}
			   if( rs != null ) try{rs.close();} catch(SQLException e) {}
		   }
		return flag;
	}
	
	public BoardTO boardModify(BoardTO to) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
		      conn = this.dataSource.getConnection();
		      
		      String sql = "select subject, writer, mail, image, content from album_board1 where seq=?";
		      pstmt = conn.prepareStatement(sql);
		      pstmt.setString(1, to.getSeq());
		      
		      rs = pstmt.executeQuery();
		      if(rs.next()){
			    	to.setSubject(rs.getString("subject"));
			    	to.setWriter(rs.getString("writer"));
			    	to.setMail(rs.getString("mail"));
			    	to.setImage(rs.getString("image"));
			    	to.setContent(rs.getString("content"));
			    	
		      }
	
		
		   } catch( SQLException e) {
		      System.out.println(" [에러] : " + e.getMessage());
		   } finally {
			   if( pstmt != null ) try{pstmt.close();} catch(SQLException e) {}
			   if( conn != null ) try{conn.close();} catch(SQLException e) {}
			   if( rs != null ) try{rs.close();} catch(SQLException e) {}
		   }
		return to;
	}
	
	public boolean boardModifyOk(BoardTO to) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean flag = false;
		
		try{
		      conn = this.dataSource.getConnection();
		      
		      String sql = "select image from album_board1 where seq=?";
		      pstmt = conn.prepareStatement(sql);
		      pstmt.setString(1,to.getSeq());
		      
		      rs = pstmt.executeQuery();
		      String filename = null;
		      if(rs.next()) {
		         filename = rs.getString("image");
		      }
		      
		      if(to.getImage() != null) {
		         // 수정될 파일이 있을 때
		         sql = "update album_board1 set subject=?, wip=?, wdate=now(), content=?, mail=?, image=? where seq=? and password=?";
		         pstmt = conn.prepareStatement(sql);
		         pstmt.setString(1, to.getSubject());
		         pstmt.setString(2, to.getWip());
		         pstmt.setString(3, to.getContent());
		         pstmt.setString(4, to.getMail());
		         pstmt.setString(5, to.getImage());
		         
		         pstmt.setString(6, to.getSeq());
		         pstmt.setString(7, to.getPassword());
		      } else {
		         sql = "update album_board1 set subject=?, wip=?, wdate=now(), content=?, mail=? where seq=? and password=?";
		         pstmt = conn.prepareStatement(sql);
		         pstmt.setString(1, to.getSubject());
		         pstmt.setString(2, to.getWip());
		         pstmt.setString(3, to.getContent());
		         pstmt.setString(4, to.getMail());
		         pstmt.setString(5, to.getSeq());
		         pstmt.setString(6, to.getPassword());
		      }
		      
		      
		      int result = pstmt.executeUpdate();
		      if( result == 0 ){
		         flag = false;
		      } else if(result == 1 ) {
		         flag = true;
		         if(to.getImage() != null && filename != null) {
		            File file = new File("C:/JSP/jsp-workspace/AlbumModel1Ex01/WebContent/upload/" + filename);
		            file.delete();
		         }
		      }
	
		   } catch( SQLException e) {
		      System.out.println(" [에러] : " + e.getMessage());
		   } finally {
			   if( pstmt != null ) try{pstmt.close();} catch(SQLException e) {}
			   if( conn != null ) try{conn.close();} catch(SQLException e) {}
			   if( rs != null ) try{rs.close();} catch(SQLException e) {}
		   }
		return flag;
	}

	public ArrayList<CommentTO> commentList(CommentTO comTo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<CommentTO> lists = new ArrayList<CommentTO>();
		try{
			
		    conn = this.dataSource.getConnection();
		    
		    String sql = "select seq, writer, content, wdate from album_comment1 where pseq=? order by seq";
		    pstmt = conn.prepareStatement(sql);
		    
		    pstmt.setString(1, comTo.getPseq());
		    rs = pstmt.executeQuery();
		    
		  
		    while(rs.next()){
		    	CommentTO newComTo = new CommentTO();
		    	
		    	newComTo.setSeq(rs.getString("seq"));
		    	newComTo.setWriter(rs.getString("writer"));
		    	newComTo.setContent(rs.getString("content").replaceAll("\n","<br />")); // 줄 바꿈 보여주기
		    	newComTo.setWdate(rs.getString("wdate"));
		    	
		    	lists.add(newComTo);
		    	}
	
		}catch( SQLException e) {
		 	System.out.println(" [에러] : " + e.getMessage());
		}finally {
			if( pstmt != null ) try{pstmt.close();} catch(SQLException e) {}
			if( conn != null ) try{conn.close();} catch(SQLException e) {}
			if( rs != null ) try{rs.close();} catch(SQLException e) {}
		}
		return lists;
	}
	
	public boolean commentWriteOk(CommentTO comTo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean flag = false;
		
		try{
		    conn = this.dataSource.getConnection();
		    
		    String sql = "insert into album_comment1 values(0, ?, ?, ?, ?, now())";
		    										// seq, pseq, writer, password, content, wdate
		    pstmt = conn.prepareStatement(sql);
		    
		    pstmt.setString(1, comTo.getPseq());
		    pstmt.setString(2, comTo.getWriter());
		    pstmt.setString(3, comTo.getPassword());
		    pstmt.setString(4, comTo.getContent());
		    
		    int result = pstmt.executeUpdate();
		    pstmt.close();
		    
		    if( result == 1) {
		    	flag = true;
		    } 
		    
		    sql = "select count(seq) cnt from album_comment1 where pseq=?";
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setString(1, comTo.getPseq());
		    
		    rs = pstmt.executeQuery();
		    String cnt="";
		    if(rs.next()) {
		    	cnt = rs.getString("cnt");
		    }
		    
		    pstmt.close();

		    sql = "update album_board1 set cmt=? where seq=?" ; // 댓글 수
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setString(1, cnt);
		    pstmt.setString(2, comTo.getPseq());
		    
		    pstmt.executeUpdate();
		    pstmt.close();
		    
		}catch( SQLException e) {
		 	System.out.println(" [에러] : " + e.getMessage());
		}finally {
			if( pstmt != null ) try{pstmt.close();} catch(SQLException e) {}
			if( conn != null ) try{conn.close();} catch(SQLException e) {}
			if( rs != null ) try{rs.close();} catch(SQLException e) {}
		}
		return flag;
	}
	
	public ArrayList<CommentTO> commentModify(CommentTO comTo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<CommentTO> lists = new ArrayList<CommentTO>();
		try{
			
		    conn = this.dataSource.getConnection();
		    
		    String sql = "select seq, writer, content, wdate from album_comment1 where seq=?";
		    pstmt = conn.prepareStatement(sql);
		    
		    pstmt.setString(1, comTo.getSeq());
		    rs = pstmt.executeQuery();
		    
		    
		    while(rs.next()){
		    	CommentTO newComTo = new CommentTO();
		    	
		    	newComTo.setSeq(rs.getString("seq"));
		    	newComTo.setWriter(rs.getString("writer"));
		    	newComTo.setContent(rs.getString("content").replaceAll("\n","<br />")); // 줄 바꿈 보여주기
		    	newComTo.setWdate(rs.getString("wdate"));
		    	
		    	lists.add(newComTo);
		    	}
	
		}catch( SQLException e) {
		 	System.out.println(" [에러] : " + e.getMessage());
		}finally {
			if( pstmt != null ) try{pstmt.close();} catch(SQLException e) {}
			if( conn != null ) try{conn.close();} catch(SQLException e) {}
			if( rs != null ) try{rs.close();} catch(SQLException e) {}
		}
		return lists;
	}
	
	public boolean commentModifyOk(CommentTO comTo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean flag = false;
		
		try{
		      conn = this.dataSource.getConnection();
		      
		      String sql = "update album_comment1 set content=? where seq=? and password=?";
		      pstmt = conn.prepareStatement(sql);
		      
		      pstmt.setString(1, comTo.getContent());
		      
		      pstmt.setString(2, comTo.getSeq());
		      pstmt.setString(3, comTo.getPassword());
		      
		      int result = pstmt.executeUpdate();
		      if(result == 0){
		    	  // 비밀번호를 잘못 기입
		    	  flag = false;
		      }else if(result == 1){
		    	  // 정상
		    	  flag = true;
		      }
	
		   } catch( SQLException e) {
		      System.out.println(" [에러] : " + e.getMessage());
		   } finally {
			   if( pstmt != null ) try{pstmt.close();} catch(SQLException e) {}
			   if( conn != null ) try{conn.close();} catch(SQLException e) {}
		   }
		return flag;
	}
	
	public CommentTO commentDelete(CommentTO comTo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
		    conn = this.dataSource.getConnection();
		    
		    String sql = "select seq, writer, content from album_comment1 where seq=?";
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setString(1, comTo.getSeq());
		    
		    rs = pstmt.executeQuery();
		    
		    if(rs.next()){
		    	comTo.setSeq(rs.getString("seq"));
		    	comTo.setWriter(rs.getString("writer"));
		    	comTo.setContent(rs.getString("content"));
		    }
		   
		}catch( SQLException e) {
			 System.out.println(" [에러 ] : " + e.getMessage());
		}finally {
			if( pstmt != null ) try{pstmt.close();} catch(SQLException e) {}
			if( conn != null ) try{conn.close();} catch(SQLException e) {}
			if( rs != null ) try{rs.close();} catch(SQLException e) {}
		}
		return comTo;
	}
	
	public boolean commentDeleteOk(CommentTO comTo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean flag = false;
		
		try{
			
		      conn = this.dataSource.getConnection();
		      
		      String sql = "delete from album_comment1 where seq=? and password=?";
		      pstmt = conn.prepareStatement(sql);
		      pstmt.setString(1, comTo.getSeq());
		      pstmt.setString(2, comTo.getPassword());
		      
		      int result = pstmt.executeUpdate();
		      
		      if(result == 1){
		    	  // 정상
		    	  flag = true;
		      }
		      
		      sql = "select count(seq) cnt from album_comment1 where pseq=?";
		      pstmt = conn.prepareStatement(sql);
		      pstmt.setString(1, comTo.getPseq());
		    
		      rs = pstmt.executeQuery();
		      String cnt="";
			    if(rs.next()) {
			    	cnt = rs.getString("cnt");
			    }
			    
			    pstmt.close();

			    sql = "update album_board1 set cmt=? where seq=?" ; // 댓글 수
			    pstmt = conn.prepareStatement(sql);
			    pstmt.setString(1, cnt);
			    pstmt.setString(2, comTo.getPseq());
			    
			    pstmt.executeUpdate();
			    pstmt.close();
		
		   } catch( SQLException e) {
		      System.out.println(" [에러] : " + e.getMessage());
		   } finally {
			   if( pstmt != null ) try{pstmt.close();} catch(SQLException e) {}
			   if( conn != null ) try{conn.close();} catch(SQLException e) {}
		   }
		return flag;
	}

}

