package taxFormLogin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LogInController extends Main{
	@FXML private TextField userName;
	@FXML private PasswordField passWord;
	
	public static Connection getConnection(){
		String host = "jdbc:mysql://localhost/javadb";
		String driver = "com.mysql.jdbc.Driver";
		String dbUserName = "root";
		String dbPassword = "";
		Connection connect = null;
		
		try{
			Class.forName(driver);
			connect = DriverManager.getConnection(host,dbUserName,dbPassword);
		}catch(Exception e){
			e.printStackTrace();
		}
		return connect;
	}
	
	@FXML
	private void verifySql(){
		String userNameInput = userName.getText().trim();
		String passWordInput = passWord.getText().trim();
		Connection connect = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select username, password from login where username= ? and password = ?";
		
		try{
			if (userNameInput.equals("")||passWordInput.equals("")){
				alertWindow("please enter your user name and password");
			}else{
			connect = getConnection();
			pstmt = connect.prepareStatement(sql);
			pstmt.setString(1, userNameInput);
			pstmt.setString(2, passWordInput);
			rs = pstmt.executeQuery();
			if (rs.next() == true) homePage();
			else alertWindow("user not found");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (pstmt != null)try{pstmt.close();}catch(Exception e){}
			if (rs != null)try{rs.close();}catch(Exception e){}
			if (connect != null)try{connect.close();}catch(Exception e){}
		}
	}
	
	@FXML
	private void goNewUser() throws IOException{
		newUserWindow();
	}
	
	public static void alertWindow(String a){
		Alert alertWindow = new Alert(AlertType.INFORMATION);
		alertWindow.setTitle("Information Window");
		alertWindow.setHeaderText(null);
		alertWindow.setContentText(a);
		alertWindow.showAndWait();
	}
}
