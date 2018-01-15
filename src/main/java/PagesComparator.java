import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Методы класса создают список (удаленных, добавленных, модифицированных) url'ов "со вчера" соответсвенно
 */
public class PagesComparator {

    public static List<String> deletedPages(Map yesterday, Map today){
        List<String> deletedPages = new ArrayList<>();
        for(Object key: yesterday.keySet()){
            if(!today.keySet().contains(key)){
                deletedPages.add(key.toString());
            }
        }
        return deletedPages;
    }

    public static List<String> addedPages(Map yesterday, Map today){
        List<String> addedPages = new ArrayList<>();
        for(Object key: today.keySet()){
            if(!yesterday.keySet().contains(key)){
                addedPages.add(key.toString());
            }
        }
        return addedPages;
    }

    public static List<String> modifiedPages(Map yesterday, Map today){
        List<String> modifiedPages = new ArrayList<>();
        for(Object key: yesterday.keySet()){
            if(today.keySet().contains(key) && !today.get(key).equals(yesterday.get(key))){
                modifiedPages.add(key.toString());
            }
        }
        return modifiedPages;
    }
}
