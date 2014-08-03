<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 공백제거--%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	request.setCharacterEncoding("UTF-8");
	String name = request.getParameter("name");
	String address = request.getParameter("address");
	
	System.out.println("이름: " + name);
	System.out.println("주소: " + address + "\n");
	
	out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	out.println("<result>");
		
	if(name!=null && address!=null){
		out.println("<code>success</code>");
	}else{
		out.println("<code>failure</code>");
	}	
	out.println("</result>");	
%>
