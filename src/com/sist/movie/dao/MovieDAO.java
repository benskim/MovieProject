package com.sist.movie.dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;// DataSource

import com.sist.member.dao.ReserveVO;

import javax.naming.*;// Context
/*    dbcp�� connection: ��ü5�� �̸� �����س���
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
 *    A ����
 *     aaa() int a=10;
 *    �ΰ�           ����
 *    B extends A
 *    {
 *        aaa(){}
 *        bbb(){}
 *        int a=20000000;
 *    }
 *    ����         �ΰ�
 *    A a=new B(); a.aaa();
 *    a.a=? 10
 *    
 *    ���� : A a
 *    �޼ҵ� :new B()
 *    
 */

public class MovieDAO {
	private Connection conn;
	private PreparedStatement ps;

	// DB ���� ==> �ּҰ� ���
	/*
	 * A a=new A(); bind("aaa",a) bind("jdbc/oracle",new Connection()) 100 101
	 * 
	 * int a=100
	 */
	public void getConnection() {
		try // RMI
		{
			// �̸� ���� => ��ü �̸� Connection �ּҰ�
			/*
			 * ===================== java://env/comp JNDI =========jdbc/oracle �̸� �ּ�
			 * ========= ==> ���丮 =====================
			 */
			Context init = new InitialContext();// Ž���� ����
			// c ����̺�
			Context root = (Context) init.lookup("java://comp/env");
			// ���ϴ� ����
			DataSource ds = (DataSource) root.lookup("jdbc/oracle");
			conn = ds.getConnection();
			// lookup ==> �̸����� ��ü�ּҸ� ã�� �� ����ϴ� �޼ҵ�
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	// ���� ���� ==> ��ȯ
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

	// ���� => selectList(SQL)
	public List<MovieVO> movieListData(int page) {
		List<MovieVO> list = new ArrayList<MovieVO>();
		try {
			getConnection();// �̸� ������ Connection�ּ� ���
			int rowSize = 9;
			int start = (rowSize * page) - (rowSize - 1);
			int end = rowSize * page;
			// ����Ŭ => R => 1
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
			disConnection();// ��ȯ
		}
		return list;
	}

	/*
	 * �������� ~ ����ó��
	 * 
	 * �����ͺ��̽� : �÷��� Ŭ���� (List=>���׸�Ÿ��)
	 * 
	 * ������ : ������̼� , XML
	 * 
	 * ������ : Pattern
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

	// ���� ��İ� �����İ� �ٸ���. �̹� �����Ǿ �ּҰ��� �������� �ȴ�.
	// dbcp: �ӵ��� ������. connectio��ü���� ���Ѱ����ϴ�.
	public MovieVO movieDetailData(int mno) {
		MovieVO vo = new MovieVO();
		try {
			getConnection();// �ּҰ��� ��´�. (����: ���ᰳ���� -> ����: �ּҰ� ���/��ȯ�̴�)
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
			disConnection();// �ּҰ� ��ȯ
		}
		return vo;
	}
	//�������
	public List<MovieinfoVO> reserveMovieListData(){
		List<MovieinfoVO> list = new ArrayList<MovieinfoVO>();
		try {
			getConnection();// �ּҰ��� ��´�. (����: ���ᰳ���� -> ����: �ּҰ� ���/��ȯ�̴�)
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
	//theaterVO : tno'��'�� ã�ƿͼ� vo�� ���� list�� ������ theater_info�� �Ѹ���
	public theaterinfoVO reserveTheaterInfoData(int tno) {
		theaterinfoVO vo =new theaterinfoVO();
		try {
			getConnection();
			//tno�� �����ȣ�ƴѰ�? ��ȭ��ȣ �ҷ��;� �ϴ°� �ƴѰ�
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
	
	//���೯¥
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
			   data=rs.getString(1);//time_no�ѱ��
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
			   data=rs.getString(1); //time�ѱ��: tno�� time�� �Ǵٸ� �̸��ϻ�
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
	   //������
	   public void reserveInsert(ReserveVO vo) {
		   try {
				getConnection();
				String sql="INSERT INTO movie_reserve VALUES(mr_no_seq.nextval,"
						+ "?,?,?,?,?,?,?,SYSDATE,0)";//SESSION�� ��ϵǾ��մ�?
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
	   //���������� �̵�
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
	 //��Ȳüũ
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
	   //������� : 1
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
