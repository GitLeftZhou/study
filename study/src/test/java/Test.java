import com.zhou.vo.Person;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutorService;

public class Test {

    @org.junit.Test
    public void test01(){
        DateFormat df = DateFormat.getDateInstance(2, Locale.getDefault());
        String str = "2018-11-27";
        try {
            System.out.println(df.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @org.junit.Test
    public void test02(){
        ArrayList<Integer> ints = new ArrayList<Integer>();
        ysfdg(30,9,15, ints);
        Collections.sort(ints);
        System.out.println(ints);
    }

    /**
     *
     * @param sum  总人数
     * @param value  标志数
     * @param n    out总人数
     * @return
     */
    private int ysfdg ( int sum, int value, int n, ArrayList<Integer> ints)
    {
        int r = 0;
        if ( n == 1 ){
            r = ( sum + value - 1 ) %sum;
//            System.out.println(r);
            ints.add(r);
        } else{
            r =  ( ysfdg ( sum-1, value,n-1 ,ints) + value ) %sum;
            ints.add(r);
        }
        return r;
    }


    private int count(int x){
        int count = 0;
        while (x != 0) {
            if ((x & 0x01) != 1) {
                count++;
            }
            x >>= 1;
        }
        return count;
    }

    @org.junit.Test
    public void test03(){
        File file = new File("E:\\myfile\\charset\\aaa.txt");
        String targetCharset = "UTF-8";
        byte [] buffer = new byte[1024];
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        ) {
            int byteread = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while ((byteread = bis.read(buffer)) != -1) {
                stringBuilder.append(new String(buffer,0,byteread,"GBK"));
            }
            String txt = stringBuilder.toString();
            System.out.println("original=" + txt);
            writeFile("original",txt);
            String result = charsetConvert(txt,targetCharset);
            System.out.println("base64="+result);
            writeFile("base64",result);
            String result2 = charsetConvert2(txt,targetCharset);
            System.out.println("simple="+result2);
            writeFile("simple",result2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @org.junit.Test
    public void test04(){
//        String str1 = "abc";
//        String str2 = new String("abc");
//        String str3 = new String("abcd");

        String abc = "abc";
        String def = "def";
        String a = "abc" + "def";
        String b = a.intern();//abc + def;
        String c = "abcdef";
        System.out.println(a == b);
        System.out.println(a == c);

    }
    private void writeFile(String filePath ,String content ){
        File file = new File(filePath);
        try (FileOutputStream fop = new FileOutputStream(file)) {
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            // get the content in bytes
            byte[] contentInBytes = content.getBytes();
            fop.write(contentInBytes);
            fop.flush();
            fop.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * gbk与utf-8互转
     * 利用BASE64Encoder/BASE64Decoder实现互转
     * @param str
     * @return
     */
    private String charsetConvert(String str, String targetCharset) {
        try {
            str = Base64.getEncoder().encodeToString(str.getBytes(targetCharset));
            byte[] bytes = Base64.getDecoder().decode(str);
            str = new String(bytes, targetCharset);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return str;
    }
    /**
     * gbk与utf-8互转
     * @param str
     * @return
     */
    private String charsetConvert2(String str, String targetCharset) {
        try {
            str = new String(str.getBytes(targetCharset), targetCharset);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return str;
    }
/*

    @org.junit.Test
    public void test04(){
        try {
            String jsonStr = "{\"Amount\":\"400\",\"AssessFeeAdd1\":\"\",\"AssessFeeAdd2\":\"\",\"AssessFeeAdd3\":\"\",\"AssessFeeAdd4\":\"\",\"AssessFeeAdd5\":\"\",\"AssessFeeMinus1\":\"\",\"AssessFeeMinus2\":\"\",\"AssessFeeMinus3\":\"\",\"AssessFeeMinus4\":\"\",\"AssessFeeMinus5\":\"\",\"CAssessType\":\"MTA\",\"CDptCde\":\"66\",\"CRptNo\":\"BA030050002019009666\",\"Remark\":\"400\"}";
            String auth = "100021269"+"###"+"6512BD43D9CAA6E02C990B0A82652DCA";
            String url = "https://ply.e-acic.com/claim/services/webSiteService?wsdl";

            // 使用RPC方式调用WebService
            RPCServiceClient serviceClient = new RPCServiceClient();
            Options options = serviceClient.getOptions();
            // 指定调用WebService的URL
            EndpointReference targetEPR = new EndpointReference(url);
            options.setTo(targetEPR);
            // 调用方法的参数值
            Object[] entryArgs = new Object[] {auth, jsonStr};
            // 调用方法返回值的数据类型的Class对象
            Class[] classes = new Class[] { String.class };
            // 调用方法名及WSDL文件的命名空间
            // 命名空间是http://localhost:8080/axis2/services/CalculateService?wsdl中wsdl:definitions标签targetNamespace属性
            QName opName = new QName("http://imp.website.autoclaim.pcis.isoftstone.com/", "saveCarTask");
            // 执行方法获取返回值
            // 没有返回值的方法使用serviceClient.invokeRobust(opName, entryArgs)
            Object result = serviceClient.invokeBlocking(opName, entryArgs, classes)[0];
            System.out.println(result);

        }  catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
    }
*/

    @org.junit.Test
    public void test05(){
        Person person = new Person();
        person.setAge("18");
        person.setName("Tommy");
        System.out.println(person);
        String dat = "e:\\myfile\\person.dat";
        //序列化对象到文件中
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dat))) {
            oos.writeObject(person);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //反序列化
        File file = new File(dat);
        Person newUser ;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            newUser = (Person) ois.readObject();
            System.out.println(newUser.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
