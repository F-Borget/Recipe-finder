package Controller;

import Model.UserService;

public class UserController {
    private UserService userService = new UserService();
    public int registerUser(String username,String email, String password){
        return userService.register(username,email,password);
    }
    public void updateUserAccount(String username,String email, String password, String id){
        userService.updateUser(username,email,password,id);
    }
    public void deleteAccount(int id){
        userService.deleteAccount(id);
    }
    public int checkUserExistance(String username,String email, String password){
        return userService.checkUserExistance(username,email,password);
    }
    public boolean checkUserEmail(String email){
        int emailExists = userService.checkUserEmail(email);
        if (emailExists>0){
            return true;
        }else {
            return false;
        }
    }
}
