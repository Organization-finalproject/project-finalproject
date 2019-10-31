package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Customer;

public class CustomerDao {

	private static CustomerDao instance;
	private Connection connection;
	
	private static String URL = "jdbc:mysql://localhost/itbank3";
	private static String USERNAME = "root";
	private static String PASSWORD = "mysql";
	
	private CustomerDao(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static CustomerDao getInstance(){
		if(instance == null)
			instance = new CustomerDao();
		return instance;
	}
	
	public boolean idCheck(String id) { //아이디 중복 검사 메소드
		if(getCustomer(id)==null) { //중복아님
			return true;
		}
		else { //중복임
			return false;
		}
	}
	
	public boolean insertCustomer(Customer customer){ //회원 가입 메소드
		PreparedStatement pstmt = null;
		String sql = "insert into customer values (null, ?, ? ,?,?,?)";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, customer.getId());
			pstmt.setString(2, customer.getName());
			pstmt.setInt(3, customer.getAge());
			pstmt.setString(4, customer.getTel());
			pstmt.setString(5, customer.getAddr());
			
			int result = pstmt.executeUpdate();
			if(result > 0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if(pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean deleteCustomer(String id){ //회원 삭제 메소드
		PreparedStatement pstmt = null;
		String sql = "delete from customer where id = ?";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, id);

			int result = pstmt.executeUpdate();
			if(result > 0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if(pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public Customer getCustomer(String id) { //회원 한명 가져오는 메소드
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Customer customer = null;
		String sql = "select * from customer where id = '" +id + "'";
		try {
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if( rs.next() )
			{
				customer = new Customer();
				customer.setNo(rs.getInt(1));
				customer.setId( rs.getString(2) );
				customer.setName( rs.getString(3) );
				customer.setAge( rs.getInt(4) );
				customer.setTel( rs.getString(5) );
				customer.setAddr( rs.getString(6) );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if(pstmt != null && !pstmt.isClosed())
					pstmt.close();
				if(rs != null && !rs.isClosed())
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return customer;
	}
	
	public List<Customer> getList(){ //회원 전체 가져오는 메소드
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Customer> list = new ArrayList<Customer>();
		String sql = "select * from customer";
		try {
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while( rs.next() )
			{
				Customer customer = new Customer();
				customer.setNo(rs.getInt(1));
				customer.setId( rs.getString(2) );
				customer.setName( rs.getString(3) );
				customer.setAge( rs.getInt(4) );
				customer.setTel( rs.getString(5) );
				customer.setAddr( rs.getString(6) );
				list.add(customer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if(pstmt != null && !pstmt.isClosed())
					pstmt.close();
				if(rs != null && !rs.isClosed())
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
}
