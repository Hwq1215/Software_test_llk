package Adamin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Administrator<T> implements AdministratorImf<T> {
    public static final String storePath = "data";
    public static final String storePreix = ".dat";
    public T TData;
    public String fileName;
    public File storeFile;

    public Administrator(String fileName){
        this.fileName = fileName;
        this.storeFile = new File(storePath,fileName);
        TData = getDataFormFile(this.storeFile.getAbsolutePath());
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getDataFormFile(String filePath) {
        // TODO Auto-generated method stub
        T t = null; 
        try{
            File file = new File(filePath);
            if(!file.exists() && !file.createNewFile()){
                System.out.println(filePath+"创建失败");
            }
    
            FileInputStream in = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            t = (T)objectInputStream.readObject();
            objectInputStream.close();
            in.close();
        }catch(IOException e){
            System.out.println("io error in read "+filePath);
        }catch(Exception e){
            System.out.println("type error");
        }
        return t;
    }

    @Override
    public boolean storeDataToFile(T value, String filePath) {
        File file = new File(filePath);
        boolean success = false;
        try{
            if(!file.exists() && !file.createNewFile()){
                    System.out.println(filePath+"创建失败");
            }

            FileOutputStream out = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(value);
            objectOutputStream.close();
            out.close();
            success = true;
        }catch(Exception e){
            System.out.println("error in store "+filePath);
            success = false;
        }

        return success;
    }
    
    public boolean storeDataToFileDefault(T value){
        return storeDataToFile(value,this.storeFile.getAbsolutePath());
    }

    @Override
    public T getCurrentData() {
        // TODO Auto-generated method stub
        return TData;
    }


}
