package test;

/**
 * Created by lushuqin on 2016/8/22.
 */
public class Test {


    public static void main(String[] args) throws Exception{
        try{
            main1();
        }catch(Exception e){
            System.out.println("main e");
            throw e;
        }

    }

    public static int main1() throws Exception{
        int result=0;
        try{
           mainExt();
            return result;
        }catch(Exception e){
            System.out.println("main1 e");
            throw e;
        }
    }

    public static int mainExt() throws Exception{
        int result=0;
        try{
            String dd=null;
            dd.equals("");
            return result;
        }catch(Exception e){
            System.out.println("mainExt e");
            throw e;
        }finally {
            return result;
        }


    }


}
