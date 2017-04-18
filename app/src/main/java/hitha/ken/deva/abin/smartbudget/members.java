package hitha.ken.deva.abin.smartbudget;

/**
 * Created by deva on 16/04/17.
 */

public class members {
    String name;
    String phno;
    String status;
    public members() {}

    public members(String msg,String sender,String time){
        this.name= msg;
        this.phno=sender;
        this.status=time;
    }

}
