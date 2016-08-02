package taxFormLogin.createUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import taxFormLogin.LogInController;
import taxFormLogin.Main;


public class createUserController extends LogInController{
	@FXML private TextField newUser;
	@FXML private PasswordField newPass;
	@FXML private PasswordField newPassAgain;
	
	@FXML
	private void backButton() throws IOException{
		Main.showLogin();
	}
	
	@FXML
	private void cancelButton(){
		String newUserInput = newUser.getText().trim();
		String newPassInput = newPass.getText().trim();
		String newPassInputAgain = newPassAgain.getText().trim();
		
		if (newUserInput.equals("")&& newPassInput.equals("")&& newPassInputAgain.equals(""))
			alertWindow("You did not enter any info");
		newUser.clear();
		newPass.clear();
		newPassAgain.clear();
	}
	
	@FXML
	private void createUserButton(){
		String newUserInput = newUser.getText().trim();
		String newPassInput = newPass.getText().trim();
		String newPassInputAgain = newPassAgain.getText().trim();
		
		Connection connect = null;
		
		ResultSet rs = null;
		PreparedStatement verifyUser = null;
		String verifyUserNameSql = "select username from login where username = ?";
		
		PreparedStatement insertUser = null;
		String insertSql = "insert into login (username,password) values (?,?)";
		
		try {
			if(newUserInput.equals("") || newPassInput.equals("") || newPassInputAgain.equals("")){
				LogInController.alertWindow("please fill out everything");
			}else{
			connect = LogInController.getConnection();
			verifyUser = connect.prepareStatement(verifyUserNameSql);
			verifyUser.setString(1, newUserInput);
			rs = verifyUser.executeQuery();
			if (rs.next()){
				LogInController.alertWindow("User already existed, please use another user name");
			}else {
			insertUser = connect.prepareStatement(insertSql);
			insertUser.setString(1, newUserInput);
			insertUser.setString(2, newPassInput);
			insertUser.execute();
			alertWindow("user created");
			cancelButton();
			}}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if (verifyUser !=null)try{verifyUser.close();}catch(Exception e){}
			if (rs !=null)try{rs.close();}catch(Exception e){}
			if (insertUser != null)try{insertUser.close();}catch(Exception e){}
			if (connect != null)try{connect.close();}catch(Exception e){}
		}
	}
	

}	
