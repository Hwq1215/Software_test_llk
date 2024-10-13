package Adamin;


/**
 * manageUtils
 */
public interface AdministratorImf<T> {

    public  T getDataFormFile(String filePath);

    public boolean storeDataToFile(T value,String filePath);

    public T getCurrentData();

}