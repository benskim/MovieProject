package com.sist.member.dao;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	   private Connection conn;
	   private PreparedStatement ps;
	  
	   public void getConnection()
	   {
		   try // RMI
		   {
			
			   Context init=new InitialContext();//탐색기 열기 
	           // c 드라이브
	           Context root=(Context)init.lookup("java://comp/env");
			   // 원하는 폴더 
	           DataSource ds=(DataSource)root.lookup("jdbc/oracle");
			   conn=ds.getConnection();
			  
		   }catch(Exception ex)
		   {
			   System.out.println(ex.getMessage());
		   }
	   }
	   // 연결 종료 ==> 반환 
	   public void disConnection()
	   {
		   try
		   {
			   if(ps!=null) ps.close();
			   if(conn!=null) conn.close();
		   }catch(Exception ex) {}
		   // POJO
	   }
	   
	   //idcheck
	   public int memberIdcheck(String id) {
		   int count=0;
		   try {
			getConnection();
			String sql="SELECT COUNT(*) FROM member WHERE id=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			disConnection();
		}
		   return count;
	   }
	   public List<ZipcodeVO> postFindData(String dong){//사용자가 보낸 값이 매개변수다
		   List<ZipcodeVO> list= new ArrayList<ZipcodeVO>();
		   try {
				getConnection();
				String sql="SELECT zipcode, sido,gugun,dong,NVL(bunji,' ') FROM zipcode"
						+ " WHERE dong LIKE '%'||?||'%' ";
				ps=conn.prepareStatement(sql);
				ps.setString(1, dong);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					ZipcodeVO vo = new ZipcodeVO();
					vo.setZipcode(rs.getString(1));
					vo.setSido(rs.getString(2));
					vo.setGugun(rs.getString(3));
					vo.setDong(rs.getString(4));
					vo.setBunji(rs.getString(5));
					list.add(vo);
				}
				rs.close();
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}finally {
				disConnection();
			}
		   return list;
	   }
	   public int postFindCount(String dong)
	   {
		   int list=0;
		   try  {
			   getConnection();
			   String sql="SELECT COUNT(*) FROM zipcode WHERE dong LIKE '%'||?||'%'";
			   ps=conn.prepareStatement(sql);
			   ps.setString(1, dong);
			   ResultSet rs=ps.executeQuery();
			   rs.next();
			   list=rs.getInt(1);
			   rs.close();
		   }catch(Exception ex)  {
			   System.out.println(ex.getMessage());
		   }
		   finally {
			   disConnection();
		   }
		   return list;
	   }
	   public void memberInsert(MemberVO vo) {
		   try  {
			   getConnection();
			   String sql="INSERT INTO member VALUES(?,?,?,?,?, ?,?,?,?,?, ?,SYSDATE,0)";
			   ps=conn.prepareStatement(sql);
			   ps.setString(1, vo.getId());
			   ps.setString(2, vo.getPwd());
			   ps.setString(3, vo.getName());
			   ps.setString(4, vo.getSex());
			   ps.setString(5, vo.getBirth());
			   ps.setString(6, vo.getEmail());
			   ps.setString(7, vo.getPost());
			   ps.setString(8, vo.getAddr1());
			   ps.setString(9, vo.getAddr2());
			   ps.setString(10, vo.getTel());
			   ps.setString(11, vo.getContent());
			   ps.executeUpdate(); 
			   
		   }catch(Exception ex)  {
			   System.out.println(ex.getMessage());
		   }
		   finally {
			   disConnection();
		   } 
	   }
	   public MemberVO isLogin(String id,String pwd)
	   {
		   MemberVO vo=new MemberVO();
		   try
		   {
			   getConnection();
			   //ID체크 
			   String sql="SELECT COUNT(*) "
					     +"FROM member "
					     +"WHERE id=?";
			   ps=conn.prepareStatement(sql);
			   ps.setString(1, id);
			   ResultSet rs=ps.executeQuery();
			   rs.next();
			   int count=rs.getInt(1);
			   rs.close();
			   if(count==0)
			   {
				  vo.setMsg("NOID"); 
			   }
			   else
			   {
				  sql="SELECT id,name,admin,pwd "
				     +"FROM member "
					 +"WHERE id=?";
				  ps=conn.prepareStatement(sql);
				  ps.setString(1, id);
				  rs=ps.executeQuery();
				  rs.next();
				  vo.setId(rs.getString(1));
				  vo.setName(rs.getString(2));
				  vo.setAdmin(rs.getInt(3));
				  String db_pwd=rs.getString(4);
				  if(db_pwd.equals(pwd))
				  {
					  vo.setMsg("OK");
				  }
				  else
				  {
					  vo.setMsg("NOPWD");
				  }
			   }
			   //PWD체크 
		   }catch(Exception ex)
		   {
			   System.out.println(ex.getMessage());
		   }
		   finally
		   {
			  disConnection(); 
		   }
		   return vo;
	   }
}
