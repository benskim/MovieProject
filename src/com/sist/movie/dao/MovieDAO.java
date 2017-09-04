package com.sist.movie.dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;// DataSource

import com.sist.member.dao.ReserveVO;

import javax.naming.*;// Context
/*    dbcp가 connection: 객체5개 미리 생성해놓음
 *    =====================
 *     new Connection   0
 *    =====================
 *     new Connection   0
 *    =====================
 *     new Connection   0
 *    =====================
 *     new Connection   0
 *    =====================
 *     new Connection   0
 *    =====================
 *    Map map=new HashMap();
 *    Connection[] conn=new Connection[maxIdle];
 *    for(int i=0;i<maxIdle;i++)
 *    {
 *       conn[i]=DriverManager.getConnection(
 *          driverClassName,username,password);
 *       
 *    }
 *    map.put("jdbc/oracle",conn);
 *    int[] check=new int[maxIdle];
 *    for(int i=0;i<maxIdle;i++)
 *    {
 *       if(check[i]==0)
 *       {
 *          check[i]=1;
 *          return conn[i];
 *          
 *       }
 *    }
 *    
 *    A 동물
 *     aaa() int a=10;
 *    인간           동물
 *    B extends A
 *    {
 *        aaa(){}
 *        bbb(){}
 *        int a=20000000;
 *    }
 *    동물         인간
 *    A a=new B(); a.aaa();
 *    a.a=? 10
 *    
 *    변수 : A a
 *    메소드 :new B()
 *    
 */

public class MovieDAO {
	private Connection conn;
	private PreparedStatement ps;

	// DB 연결 ==> 주소값 얻기
	/*
	 * A a=new A(); bind("aaa",a) bind("jdbc/oracle",new Connection()) 100 101
	 * 
	 * int a=100
	 */
	public void getConnection() {
		try // RMI
		{
			// 이름 저장 => 객체 이름 Connection 주소값
			/*
			 * ===================== java://env/comp JNDI =========jdbc/oracle 이름 주소
			 * ========= ==> 디렉토리 =====================
			 */
			Context init = new InitialContext();// 탐색기 열기
			// c 드라이브
			Context root = (Context) init.lookup("java://comp/env");
			// 원하는 폴더
			DataSource ds = (DataSource) root.lookup("jdbc/oracle");
			conn = ds.getConnection();
			// lookup ==> 이름으로 객체주소를 찾을 때 사용하는 메소드
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	// 연결 종료 ==> 반환
	public void disConnection() {
		try {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (Exception ex) {
		}
		// POJO
	}

	// 동일 => selectList(SQL)
	public List<MovieVO> movieListData(int page) {
		List<MovieVO> list = new ArrayList<MovieVO>();
		try {
			getConnection();// 미리 설정된 Connection주소 얻기
			int rowSize = 9;
			int start = (rowSize * page) - (rowSize - 1);
			int end = rowSize * page;
			// 오라클 => R => 1
			// 1-9, 10-18 , 19-27 28-,
			String sql = "SELECT mno,title,poster,director,num "
					+ "FROM (SELECT mno,title,poster,director,rownum as num "
					+ "FROM (SELECT mno,title,poster,director FROM movie " + "ORDER BY mno ASC)) "
					+ "WHERE num BETWEEN " + start + " AND " + end;
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				MovieVO vo = new MovieVO();
				vo.setMno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
				vo.setDirector(rs.getString(4));
				list.add(vo);
			}
			rs.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			disConnection();// 반환
		}
		return list;
	}

	/*
	 * 데이터형 ~ 예외처리
	 * 
	 * 데이터베이스 : 컬렉션 클래스 (List=>제네릭타입)
	 * 
	 * 스프링 : 어노테이션 , XML
	 * 
	 * 빅데이터 : Pattern
	 */
	public int movieTotalPage() {
		int total = 0;
		try {
			getConnection();
			String sql = "SELECT CEIL(count(*)/9) FROM movie";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total = rs.getInt(1);
			rs.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			disConnection();
		}
		return total;
	}

	// 기존 방식과 연결방식과 다르다. 이미 생성되어서 주소값만 가져오면 된다.
	// dbcp: 속도가 빠르다. connectio객체생성 제한가능하다.
	public MovieVO movieDetailData(int mno) {
		MovieVO vo = new MovieVO();
		try {
			getConnection();// 주소값을 얻는다. (과거: 연결개폐지 -> 현재: 주소값 사용/반환이다)
			String sql = "SELECT mno,title,poster,actor,director,regdate,genre,grade,story " + "FROM movie WHERE mno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setMno(rs.getInt(1));
			vo.setActor(rs.getString(4));
			vo.setDirector(rs.getString(5));
			vo.setGenre(rs.getString(7));
			vo.setGrade(rs.getString(8));
			vo.setPoster(rs.getString(3));
			vo.setRegdate(rs.getString(6));
			vo.setStory(rs.getString(9));
			vo.setTitle(rs.getString(2));
			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			disConnection();// 주소값 반환
		}
		return vo;
	}
	//예약관련
	public List<MovieinfoVO> reserveMovieListData(){
		List<MovieinfoVO> list = new ArrayList<MovieinfoVO>();
		try {
			getConnection();// 주소값을 얻는다. (과거: 연결개폐지 -> 현재: 주소값 사용/반환이다)
			String sql = "SELECT mno,title,poster,actor FROM movie_info";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				MovieinfoVO vo=new MovieinfoVO();
				   vo.setMno(rs.getInt(1));
				   vo.setTitle(rs.getString(2));
				   vo.setPoster(rs.getString(3));
				   list.add(vo);
			}
			rs.close();			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			disConnection();
		}
		
		return list;
	}
	//thaeter_no
	public String reserveTheater_noData(int mno) {
		String data="";
		try {
			getConnection();
			String sql = "SELECT theater_no FROM movie_info WHERE mno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, mno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			data=rs.getString(1);
			rs.close();
		}  catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			disConnection();
		}
		return data;
	}
	//theaterVO : tno'들'을 찾아와서 vo를 담은 list를 가져와 theater_info에 뿌린다
	public theaterinfoVO reserveTheaterInfoData(int tno) {
		theaterinfoVO vo =new theaterinfoVO();
		try {
			getConnection();
			//tno는 극장번호아닌가? 영화번호 불러와야 하는거 아닌가
			String sql="SELECT tno,tname,loc FROM theater_info WHERE tno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, tno);
			ResultSet rs =ps.executeQuery();
			rs.next();
			vo.setTno(rs.getInt(1));
			vo.setTname(rs.getString(2));
			vo.setLoc(rs.getString(3));
			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			disConnection();
		}
		return vo;
	}
	
	//예약날짜
	public String reserveDate(int tno) {
		String data="";
		try {
			getConnection();
			String sql = "SELECT date_no FROM theater_info WHERE tno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, tno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			data=rs.getString(1);
			rs.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			disConnection();
		}
		return data;
	}
	public String reserveTime(int dno)
	   {
		   String data="";
		   try
		   {
			   getConnection();
			   String sql="SELECT time_no FROM date_info WHERE dno=?";
			   ps=conn.prepareStatement(sql);
			   ps.setInt(1, dno);
			   ResultSet rs=ps.executeQuery();
			   rs.next();
			   data=rs.getString(1);//time_no넘기기
			   rs.close();
		   }catch(Exception ex)
		   {
			   System.out.println(ex.getMessage());
		   }
		   finally
		   {
			   disConnection();
		   }
		   return data;
	   }
	   public String reserveTimeData(int tno)
	   {
		   String data="";
		   try
		   {
			   getConnection();
			   String sql="SELECT time FROM time_info WHERE tno=?";
			   ps=conn.prepareStatement(sql);
			   ps.setInt(1, tno);
			   ResultSet rs=ps.executeQuery();
			   rs.next();
			   data=rs.getString(1); //time넘기기: tno는 time의 또다른 이름일뿐
			   rs.close();
		   }catch(Exception ex)
		   {
			   System.out.println(ex.getMessage());
		   }
		   finally
		   {
			   disConnection();
		   }
		   return data;
	   }
	   //예약등록
	   public void reserveInsert(ReserveVO vo) {
		   try {
				getConnection();
				String sql="INSERT INTO movie_reserve VALUES(mr_no_seq.nextval,"
						+ "?,?,?,?,?,?,?,SYSDATE,0)";//SESSION에 등록되어잇다?
				 ps=conn.prepareStatement(sql);
				   ps.setString(1, vo.getId());
				   ps.setString(2, vo.getTitle());
				   ps.setString(3, vo.getTheater());
				   ps.setString(4, vo.getResday());
				   ps.setString(5, vo.getRestime());
				   ps.setString(6, vo.getInwon());
				   ps.setString(7, vo.getPrice());
				ps.executeUpdate();
				
		   }catch(Exception ex){
			   System.out.println(ex.getMessage());
		   }
		   finally{
			   disConnection();
		   }
	   }
	   //마이페이지 이동
	   public List<ReserveVO> reserveMypage(String id){
		   List<ReserveVO> list = new ArrayList<ReserveVO>();
		   try {
			   getConnection();
				String sql="SELECT no,title,theater,resday,restime,inwon,price,reserve "
						+ "FROM movie_reserve WHERE id=?";
				ps=conn.prepareStatement(sql);
				   ps.setString(1, id);
				   ResultSet rs=ps.executeQuery();
				   while(rs.next())
				   {
					  ReserveVO vo=new ReserveVO();
					  vo.setNo(rs.getInt(1));
					  vo.setTitle(rs.getString(2));
					  vo.setTheater(rs.getString(3));
					  vo.setResday(rs.getString(4));
					  vo.setRestime(rs.getString(5));
					  vo.setInwon(rs.getString(6));
					  vo.setPrice(rs.getString(7));
					  vo.setReserve(rs.getInt(8));
					  list.add(vo);
				   }
				   rs.close();
		   }catch(Exception ex){
			   System.out.println(ex.getMessage());
		   }
		   finally{
			   disConnection();
		   }
		   return list;
	   }
	 //현황체크
	   public List<ReserveVO> reserveAdminPage(){
		   List<ReserveVO> list = new ArrayList<ReserveVO>();
		   try {
			   getConnection();
				String sql="SELECT no,id,title,theater,resday,restime,inwon,price,reserve "
						+ "FROM movie_reserve ORDER BY reserve ASC, regdate DESC";
				ps=conn.prepareStatement(sql);
				   ResultSet rs=ps.executeQuery();
				   while(rs.next())
				   {
					  ReserveVO vo=new ReserveVO();
					  vo.setNo(rs.getInt(1));
					  vo.setId(rs.getString(2));
					  vo.setTitle(rs.getString(3));
					  vo.setTheater(rs.getString(4));
					  vo.setResday(rs.getString(5));
					  vo.setRestime(rs.getString(6));
					  vo.setInwon(rs.getString(7));
					  vo.setPrice(rs.getString(8));
					  vo.setReserve(rs.getInt(9));
					  list.add(vo);
				   }
				   rs.close();
		   }catch(Exception ex){
			   System.out.println(ex.getMessage());
		   }
		   finally{
			   disConnection();
		   }
		   return list;
	   }
	   //예약승인 : 1
	   public void reserveOk(int no) {
		   try {
			   getConnection();
				String sql="UPDATE movie_reserve SET reserve=1 WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
				
		   }catch(Exception ex){
			   System.out.println(ex.getMessage());
		   }
		   finally{
			   disConnection();
		   }
	   }
}
