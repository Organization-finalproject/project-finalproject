package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import model.Customer;

@WebServlet("*.do")
public class CustomerServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String contextPath = req.getContextPath() + "/";

		if (req.getRequestURI().equals(contextPath + "idCheck.do")) { //아이디 체크 요
			String id = req.getParameter("id");
			String resultstr = "";
			boolean result = CustomerDao.getInstance().idCheck(id);
			if(result) { //중복 하는게 없음
				resultstr = " { \"result\": " +"\"yes"+"\" }";
			}
			else { //중복됨
				resultstr = " { \"result\": " +"\"no"+"\" }";
			}
			resp.setContentType("text/xml; charset=UTF-8"); //해줘야 한글 제대로 날아감
			PrintWriter pw = resp.getWriter(); //PrintWriter로 텍스트 파일 실어서 돌아감
			
			pw.println(resultstr);
			pw.flush();

		}
		
		if (req.getRequestURI().equals(contextPath + "join.do")) { //가입 요청
			String id = req.getParameter("id");
			String name = req.getParameter("name");
			String agearr = req.getParameter("age");
			String tel = req.getParameter("tel");
			String addr = req.getParameter("addr");
			String resultstr = "";
			if(id.length()==0 ||name.length()==0 ||agearr.length()==0 ||tel.length()==0 ||addr.length()==0) { //빈칸
				resultstr = " { \"result\": " +"\"no"+"\" }";
			}
			else {
				int age = Integer.parseInt(agearr);
				boolean result = false;
				Customer check = CustomerDao.getInstance().getCustomer(id); //해당 id가 있는지 체크하기
				if(check == null) {	 //해당id가 안들어있다면 insert
					Customer c = new Customer();
					c.setId(id);
					c.setName(name);
					c.setAge(age);
					c.setTel(tel);
					c.setAddr(addr);
					
					result = CustomerDao.getInstance().insertCustomer(c);
				}		
				if(result) { //중복 하는게 없음
					resultstr = " { \"result\": " +"\"yes"+"\" }";
				}
				else { //중복됨
					resultstr = " { \"result\": " +"\"no"+"\" }";
				}
			}
			resp.setContentType("text/xml; charset=UTF-8"); //해줘야 한글 제대로 날아감
			PrintWriter pw = resp.getWriter(); //PrintWriter로 텍스트 파일 실어서 돌아감
			
			pw.println(resultstr);
			pw.flush();
		}
		
		if (req.getRequestURI().equals(contextPath + "list.do")) { //리스트 요청
			List<Customer> list = new ArrayList<Customer>();
			list = CustomerDao.getInstance().getList();
			
			resp.setContentType("text/xml; charset=UTF-8"); //해줘야 한글 제대로 날아감
			PrintWriter pw = resp.getWriter(); //PrintWriter로 텍스트 파일 실어서 돌아감
			int length = list.size();
			int check = 0;
			String resultstr = "";
			resultstr+="  {\"result\":[";			
			for(Customer c:list) {
				resultstr+="{";
				resultstr+="\"no\":" +"\""+ c.getNo() +"\""+ ",";
				resultstr+="\"id\":" +"\""+ c.getId() +"\""+ ",";
				resultstr+="\"name\":" +"\""+ c.getName() +"\""+ ",";
				resultstr+="\"age\":" +"\""+ c.getAge() +"\""+ ",";
				resultstr+="\"tel\":" +"\""+ c.getTel() +"\""+ ",";
				resultstr+="\"addr\":" +"\""+ c.getAddr()+ "\"";
				resultstr+="}";
				check++;
				if(check != length) {
					resultstr+=",";
				}
			}
			resultstr+="]}";
			pw.println(resultstr);
			pw.flush();
		}
		
		if (req.getRequestURI().equals(contextPath + "delete.do")) { //삭제 요청
			String id = req.getParameter("id");
			String resultstr = "";
			boolean result = CustomerDao.getInstance().deleteCustomer(id);
			if(result) { //삭제 완료
				resultstr = " { \"result\": " +"\"yes"+"\" }";
			}
			else { //삭제 안됨
				resultstr = " { \"result\": " +"\"no"+"\" }";
			}
			resp.setContentType("text/xml; charset=UTF-8"); //해줘야 한글 제대로 날아감
			PrintWriter pw = resp.getWriter(); //PrintWriter로 텍스트 파일 실어서 돌아감
			
			pw.println(resultstr);
			pw.flush();
			
		}
	}

}
