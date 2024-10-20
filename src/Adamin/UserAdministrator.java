package Adamin;
import java.util.ArrayList;

public class UserAdministrator extends Administrator<ArrayList<User>> {
    //使用静态数据，保证数据的一致性，
    //用类的名字作为存储的文件名
    private static String userFileName = UserAdministrator.class.getSimpleName() + Administrator.storePreix;
    private static ArrayList<User> users;

    //注册错误的枚举类
    public static enum ResigterError {
        ACCOUNT_EXIST("账户已存在"),
        ACCOUNT_ILLEGAL("账户不合法"),
        PASSWORD_ILLEGAL("密码不合法"),
        No_Error("符合条件");
        private final String errorAttention;

        ResigterError(String errorAttention) {
            this.errorAttention = errorAttention;
        }

        public String getErrorAttention() {
            return this.errorAttention;
        }
    }

    public UserAdministrator() {
        super(userFileName);
        users = getCurrentData();
        if (users == null) {
            users = new ArrayList<User>();
        }
    }

    public User login(String account, String password) {
        for (User user : users) {
            if (account.equals(user.account) && password.equals(user.password)) {
                return user;
            }
        }
        return null;
    }

    public ResigterError registerCheck(String account, String password) {
        // 正则表达式检查账号是否只包含字母、数字和下划线，长度在3-13个字符之间
        String accountPattern = "^[a-zA-Z0-9_]{3,13}$";

        // 正则表达式检查密码是否只包含可见字符（不含空格），长度在7-19个字符之间
        String passwordPattern = "^[\\S]{7,19}$";

        // 1. 检查账号是否符合要求
        if (!account.matches(accountPattern)) {
            return ResigterError.ACCOUNT_ILLEGAL;
        }

        // 2. 检查密码是否符合要求 
        if (!password.matches(passwordPattern)) {
            return ResigterError.PASSWORD_ILLEGAL;
        }

        // 3. 检查账号是否已存在（这里只是示例，需要结合数据库或业务逻辑判断）
        if (isAccountExist(account)) {
            return ResigterError.ACCOUNT_EXIST;
        }

        // 如果所有检查都通过，返回null表示没有错误
        return ResigterError.No_Error;
    }

    // 模拟检查账户是否已经存在的方法
    private boolean isAccountExist(String account) {
        // 假设已有账户列表，可以替换为实际的账户查询逻辑
        for (User user : users) {
            if (user.account.equals(account)) {
                return true;
            }
        }
        return false;
    }

    //调用该方法前先调用resigterCheck来确定有无注册的问题
    public User register(String account,String password){
        if(registerCheck(account, password).equals(ResigterError.No_Error)){
            User registerUser = new User(account,password);
            users.add(registerUser);
            store();
            return registerUser;
        }
        return null;
    }

    //持久化
    private void store() {
        this.storeDataToFileDefault(users);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    //测试主函数
    public static void main(String[] args){
        UserAdministrator userAdministrator = new UserAdministrator();
        User user1 = new User("123131","123123");
        User user2 = new User("534543","sddas");
        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user2);
        for(User user:userAdministrator.getUsers()){
            System.out.println(user.account + "  "+ user.password);
        }
        // System.out.println(userAdministrator.getUsers());
        System.out.println(userAdministrator.register("today_","234123d3%@……##"));
        System.out.println(userAdministrator.login(user1.account, user1.password));
    }
}
