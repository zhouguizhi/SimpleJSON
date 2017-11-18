package test.com.simplejson;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.json.JSON;
import com.json.util.Logger;
import java.util.ArrayList;
import java.util.List;
import test.com.simplejson.bean.Author;
import test.com.simplejson.bean.ItemNews;
import test.com.simplejson.bean.News;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick1(View view){
        News news = new News();
        news.set_isVip(false);
        news.setAge(30);
        news.set_endTime("2018-12-31");
        news.setContent("新闻工作者");
        news.setCount(4);
        news.setNumber((short) 11);
        news.setMoney(0.9f);
        news.setIshaha("笑你妹");
        news.setVip(true);
        news.setName("zhouguizhi");
        Author author = new Author();
        author.setName("新闻发言人");
        author.setDepartment("新闻部");
        author.setAge(60);
        news.setAuthor(author);
        //添加集合
        List<ItemNews> items = new ArrayList<>();
        for(int i=0;i<5;i++){
            ItemNews itemNews = new ItemNews();
            itemNews.setName("新闻头条--"+i);
            itemNews.setAuthor("自媒体人---"+i);
            itemNews.setTitle("要生死大事了");
            items.add(itemNews);
        }
        news.setItem(items);
        String json = JSON.toJson(news);
        Logger.e("json="+json);

    }
    public void onClick2(View view){
        test1();
        test2();
    }

    private void test2() {
        News news = new News();
        news.set_isVip(false);
        news.setAge(30);
        news.set_endTime("2018-12-31");
        news.setContent("新闻工作者");
        news.setCount(4);
        news.setNumber((short) 11);
        news.setMoney(0.9f);
        news.setIshaha("笑你妹");
        news.setVip(true);
        news.setName("zhouguizhi");
        Author author = new Author();
        author.setAge(100);
        author.setDepartment("研发部门");
        author.setName("程序员");
        news.setAuthor(author);
        List<ItemNews> list = new ArrayList<>();
        for(int i=0;i<4;i++){
            ItemNews  itemNews = new ItemNews();
            itemNews.setName("新闻记者");
            itemNews.setTitle("今日头条");
            list.add(itemNews);
        }
        news.setItem(list);
        String json2 = JSON.toJson(news);
        Logger.e("json2="+json2);
        News news2 = (News) JSON.jsonToObj(json2,News.class);
        Logger.e("news2="+news2);
    }

    private void test1() {
        News news = new News();
        news.set_isVip(false);
        news.setAge(30);
        news.set_endTime("2018-12-31");
        news.setContent("新闻工作者");
        news.setCount(4);
        news.setNumber((short) 11);
        news.setMoney(0.9f);
        news.setIshaha("笑你妹");
        news.setVip(true);
        news.setName("zhouguizhi");
        String json = JSON.toJson(news);
        News news1 = (News) JSON.jsonToObj(json,News.class);
        Logger.e("news1="+news1);
    }
}
