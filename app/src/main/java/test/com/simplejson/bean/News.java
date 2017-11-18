package test.com.simplejson.bean;
import java.util.List;
/**
 * Created by zhouguizhi on 2017/11/17.
 */
public class News extends Reader{
    private String name;
    private String time;
    private String content;
    private boolean isVip;
    private int age;
    private double saraly;
    private Author author;
    private String _endTime;
    private String ishaha;
    private boolean _isVip;
    private Integer count;
    private String _isHahaa;
    private short number;
    private float money;
    private List<ItemNews> item;

    public String get_isHahaa() {
        return _isHahaa;
    }

    public void set_isHahaa(String _isHahaa) {
        this._isHahaa = _isHahaa;
    }

    public List<ItemNews> getItem() {
        return item;
    }

    public void setItem(List<ItemNews> item) {
        this.item = item;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public short getNumber() {
        return number;
    }

    public void setNumber(short number) {
        this.number = number;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public boolean is_isVip() {
        return _isVip;
    }

    public void set_isVip(boolean _isVip) {
        this._isVip = _isVip;
    }

    public String getIshaha() {
        return ishaha;
    }

    public void setIshaha(String ishaha) {
        this.ishaha = ishaha;
    }

    public String get_endTime() {
        return _endTime;
    }

    public void set_endTime(String _endTime) {
        this._endTime = _endTime;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSaraly() {
        return saraly;
    }

    public void setSaraly(double saraly) {
        this.saraly = saraly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "News{" +
                "name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", isVip=" + isVip +
                ", age=" + age +
                ", saraly=" + saraly +
                ", author=" + author +
                ", _endTime='" + _endTime + '\'' +
                ", ishaha='" + ishaha + '\'' +
                ", _isVip=" + _isVip +
                ", count=" + count +
                ", number=" + number +
                ", money=" + money +
                ", item=" + item +
                '}';
    }
}
