package com.sist.member.dao;
import java.util.*;
public class ReserveVO {
private int no,reserve;
private String id,title,theater,resday,restime,price,inwon;

public int getNo() {
	return no;
}
public void setNo(int no) {
	this.no = no;
}
public int getReserve() {
	return reserve;
}
public void setReserve(int reserve) {
	this.reserve = reserve;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getTheater() {
	return theater;
}
public void setTheater(String theater) {
	this.theater = theater;
}
public String getResday() {
	return resday;
}
public void setResday(String resday) {
	this.resday = resday;
}
public String getRestime() {
	return restime;
}
public void setRestime(String restime) {
	this.restime = restime;
}
public String getPrice() {
	return price;
}
public void setPrice(String price) {
	this.price = price;
}
public String getInwon() {
	return inwon;
}
public void setInwon(String inwon) {
	this.inwon = inwon;
}
public Date getRegdate() {
	return regdate;
}
public void setRegdate(Date regdate) {
	this.regdate = regdate;
}
private Date regdate;	 

}
