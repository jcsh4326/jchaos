package me.jcis.chaos.utils;

import junit.framework.Assert;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2016/2/1
 */
public class NumberUtilsTest {
    String MORE = "([1-9]+\\d*|\\d).\\d{2,}";
    String LESS = "([1-9]+\\d*|\\d).\\d{1,2}";
    String INT = "[1-9]+\\d*|\\d";

    @Test
    public void test(){
        Pattern p = Pattern.compile(MORE);
        Matcher m = p.matcher("10.333");
        Matcher m1 = p.matcher("0.333");
        Matcher m2 = p.matcher("321.33");
        Matcher m3 = p.matcher("4321.1234");
        Assert.assertTrue(m.matches());
        Assert.assertTrue(m1.matches());
        Assert.assertTrue(m2.matches());
        Assert.assertTrue(m3.matches());

        Pattern pl = Pattern.compile(LESS);
        Matcher ml = pl.matcher("10.33"), ml1 = pl.matcher("12.3"), ml2 = pl.matcher("2.3");
        Assert.assertTrue(ml.matches());
        Assert.assertTrue(ml1.matches());
        Assert.assertTrue(ml2.matches());

        Pattern pI = Pattern.compile(INT);
        Assert.assertTrue(pI.matcher("0").matches());
        Assert.assertTrue(pI.matcher("20").matches());
    }

    @Test
    public void test1(){
        double d = 10.2223;
        String n = (d+"").split("\\.")[0].concat(".").concat(((d + "").split("\\.")[1]).substring(0, 2));
        Assert.assertEquals(Double.parseDouble(n), 10.22, 0.00);
    }

    @Test
    public void test2(){
        int int1 = Integer.parseInt("10",2), int2 = Integer.parseInt("100",2);
        String o = "subtract";
        int val = "add".equals(o) ? int1+int2 : "subtract".equals(o) ? int1-int2 : int1 * int2;
        String result = val < 0 ? "-" + Integer.toBinaryString(0 - val) : Integer.toBinaryString(val);
        System.out.print(Integer.toBinaryString(val));
    }

    public static int findSmallest( final int[] numbers, final String toReturn ) {
        //TODO: Add solution here
        int index=0, minIndex = 0, min = numbers[index];
        for(int num : numbers){
            if(num<min){
                min = num;
                minIndex = index;
            }
            index++;
        }

        return "value".equals(toReturn) ? min : minIndex;
    }

    @Test
    public void test3(){
        assertEquals( "The smallest index" , 0 , NumberUtilsTest.findSmallest( new int [] {1, 2, 3} , "index") );
        assertEquals( "The smallest value" , 2 , NumberUtilsTest.findSmallest( new int [] {7, 12, 3, 2, 27} , "value") );
        assertEquals("The smallest index", 3, NumberUtilsTest.findSmallest(new int[]{7, 12, 3, 2, 27}, "index"));
    }

    public static Map drunkenDoodling = new HashMap() {
        {
            put("werewolf", "Silver knife or bullet to the heart");
            put("vampire", "Behead it with a machete");
            put("wendigo", "Burn it to death");
            put("shapeshifter", "Silver knife or bullet to the heart");
            put("angel", "Use the angelic blade");
            put("demon", "Use Ruby's knife, or some Jesus-juice");
            put("ghost", "Salt and iron, and don't forget to burn the corpse");
            put("dragon", "You have to find the excalibur for that");
            put("djinn", "Stab it with silver knife dipped in a lamb's blood");
            put("pagan god", "It depends on which one it is");
            put("leviathan", "Use some Borax, then kill Dick");
            put("ghoul", "Behead it");
            put("jefferson starship", "Behead it with a silver blade");
            put("reaper", "If it's nasty, you should gank who controls it");
            put("rugaru", "Burn it alive");
            put("skinwalker", "A silver bullet will do it");
            put("phoenix", "Use the colt");
            put("witch", "They are humans");
        }
    };

    public static String bob(String beast) {
        return String.format("%s, idjits!",
                drunkenDoodling.containsKey(beast) ? drunkenDoodling.get(beast) :
                        "I have friggin no idea yet");
    }

    public static boolean comp(int[] a, int[] b) {
        if(null==a||null==b)
            return false;
        Arrays.sort(a);
        Arrays.sort(b);
        if(a.length!=b.length)
            return false;
        if(a.length>0){
            if(a[0]<0){
                for(int i=0;i<a.length;i++){
                    if(a[i]<0)
                        a[i]=-a[i];
                    else {
                        Arrays.sort(a);
                        break;
                    }
                }
            }
        }
        for(int i=0;i<a.length;i++){
            if(b[i]!=a[i]*a[i]) {
                System.out.print(String.format("%d is not a square of %d", b[i], a[i]));
                return false;
            }
        }
        return true;
//        return a != null && b != null && a.length == b.length && Arrays.equals(Arrays.stream(a).map(i -> i * i).sorted().toArray(), Arrays.stream(b).sorted().toArray());
    }

    @Test
    public void angle(){
        int[] a = new int[]{0,-14,191,161,19,144,195,1};
        int[] b = new int[]{1,0,196,36481,25921,361,20736,38025};

        assertEquals(NumberUtilsTest.comp(a, b), false);

    }

    public int preview(int i){
        if(0==i)
            return i;
        else{
            return i+preview(i-1);
        }
    }

    public static String winner(String[] deckSteve, String[] deckJosh) {
        // TODO
        List list = Arrays.asList("2","3","4","5","6","7","8","9","T","J","Q","K","A");
        int winS = 0, tie = 0;
        for(int i=0;i<deckSteve.length;i++){
            if( list.indexOf(deckSteve[i]) == list.indexOf(deckJosh[i]) ){
                tie++;
            }else{
                if( list.indexOf(deckSteve[i]) > list.indexOf(deckJosh[i]) )
                    winS++;
            }
        }
        int winJ = deckSteve.length-winS - tie;
        String val = "%s wins %d to %d", res = winS == winJ ? "Tie" :
                ( winS<winJ?String.format(val, "Josh", winJ, winS) : String.format(val, "Steve", winS, winJ) );
        return res;
    }

    public static long listSquared8(long n){
        long sum = LongStream.rangeClosed(1,n>>1).filter(f -> n%f==0).map(f->f*f).sum() + n*n;
        return sum == (long)Math.sqrt(sum)*Math.sqrt(sum) ? sum : -1;
    }

    public static String squared(long m, long n){
        return String.format("[%s]",LongStream.rangeClosed(m,n).mapToObj(i -> {
            long sum = LongStream.rangeClosed(1,i>>1).filter(f -> i%f==0).map(f->f*f).sum() + i*i;
            return sum == (long)Math.sqrt(sum)*Math.sqrt(sum) ?
                    String.format("[%d, %d]", i, sum) : "";
        }).filter(f ->f!="").collect(Collectors.joining(", ")));
    }

    public static String tugOWar(final int[][] teams) {
        int team1 = IntStream.of(teams[0]).sum(), team2 = IntStream.of(teams[1]).sum();
        if(team1==team2){
            int a1 = teams[0][0], a2 = teams[1][teams[1].length-1];
            return a1==a2 ? "It's a tie!" : String.format("Team %s wins!", a1 > a2 ? "1":"2" );
        }else{
            return String.format("Team %s wins!", team1>team2 ? "1":"2" );
        }
    }

    @Test
    public void testS(){
        assertEquals(listSquared(42, 250),squared(42, 250));
    }

    public static String listSquared(long n){
        long sqr = Math.round(Math.sqrt(n));
        List list = new ArrayList();
        long sum = 0;
        for(long i=sqr;i>0;i--){
            int j = 0;
            while(n>=i*(i+j)){
                if(n==i*(i+j)){
                    sum+=i*i;
                    if(i!=(i+j))
                        sum+=(i+j)*(i+j);
                }
                j++;
            }
        }
        long sqrn = Math.round(Math.sqrt(sum));
        return sqrn*sqrn==sum ? String.format("[%d, %d]",n, sum) : "";
    }

    public static String listSquared(long m, long n) {
        String s="[";
        for (long i=m;i<=n;i++){
            long sq=getSquare(i);
            if (sq!=0){
                s+=s.length()!=1?", ["+i+", "+sq+"]":"["+i+", "+sq+"]";
            }
        }
        return s+="]";
    }
    static long getSquare(long n){
        long sum=n*n;
        for (long i=1;i<=n>>1;i++){
            if (n%i==0) sum+=i*i;
        }
        if ((long)Math.sqrt(sum)*Math.sqrt(sum)==sum) return sum;
        else return 0;
    }

    // 欧几里德算法
    private static int gcd(int a, int b) {
        return (a == 0) ? b : gcd(b % a, a);
    }

    private static int lcm(int a, int b) {
        return a * b / gcd(a,b);
    }

    public static String fromNb2Str(int n, int[] bases) {
        int product = Arrays.stream(bases).reduce(1, (a,b) -> a*b);
        int baseLcm = Arrays.stream(bases).reduce(1, NumberUtilsTest::lcm);
        if (baseLcm != product || product <= n) return "Not applicable";

        return Arrays.stream(bases)
                .map(base -> n % base)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining("--", "-", "-"));
    }

    @Test
    public void testNb2Str(){
        int[] lst = new int[] {8, 7, 5, 3};
        assertEquals("-3--5--2--1-",
                fromNb2Str(187, lst));
        assertEquals("Not applicable", fromNb2Str(6,new int[]{2, 3, 4}));
        assertEquals("Not applicable", fromNb2Str(7, new int[]{2, 3}));
        assertEquals("-1--2--1-", fromNb2Str(11, new int[]{2, 3, 5}));
        assertEquals("Not applicable", fromNb2Str(15, new int[]{8, 6, 5}));
    }

    @Test
    public void testPreview(){
        Date start = new Date();
        String f = "";
        for(long x=42;x<=250;x++){
            String res = listSquared(x);
            f += "".equals(res) ? "" : res + ", ";
        }
        assertEquals(listSquared(42, 250), String.format("[%s]", f.endsWith(", ") ? f.substring(0, f.length() - 2) : f));
        Date end = new Date();
        System.out.print(end.getTime() - start.getTime());

    }

    public static double getK(double[] pnts){
        return (pnts[3]-pnts[1])/(pnts[2]-pnts[0]);
    }

    public static double getX(double k, double y1, double x1, double y2){
        return x1-(y1-y2)/k;
    }

    public static double getArea(int w){
        double k = 0, c = (w+1)/2, x = (w+1)/2, area = 0;
        if(w%2==1){
            for(int i=1;i<9;i++){
                double[] pnts = new double[]{c,9,1,i};
                k = getK(pnts);
                area += (x-1)*(9-i)/2;
                // next x
                x = getX(k,9,c,i+1);
            }
        }
        return area;
    }

    public static int c(int a,int b){
        if(b>a/2){
            return c(a,a-b);
        }
        return up(a,b)/up(b,b);
    }

    public static int up(int a,int b){
        int c = 1;
        for(int i=0;i<b;i++){
            c = c*a;
            a--;
        }
        return c;
    }

    public static int[][] makeMatrix(int digit, int high){
        IntStream.rangeClosed(1, digit).mapToObj(f ->
            Arrays.asList((Integer[]) IntStream.rangeClosed(0, high).mapToObj(g -> new int[]{g, f}).toArray())
        ).flatMap(child->child.stream()).toArray();

        return null;
    }

    public static boolean getBouncy(int num){
        return false;
    }

    @Test
    public void testArea(){
        makeMatrix(3, 1);
    }

    public static Map frequency(String s){
        Map res = new HashMap<>();
        Stream.of(s.split("")).filter(f->f.compareTo("a")>=0&&f.compareTo("z")<=0).
                collect(Collectors.groupingBy(f->f)).forEach((key, val) -> res.put(key, val.stream().collect(Collectors.joining(""))));
        return new TreeMap<>(res);
    }

    public static String mix(String s1, String s2) {
        // your code
        Map m1 = frequency(s1), m2 = frequency(s2);
        Iterator it1 = m1.entrySet().iterator();
        Map mixed = new HashMap<>();
        while(it1.hasNext()){
            Map.Entry en1 = (Map.Entry) it1.next();
            if(en1.getValue().toString().length()>1){
                if(m2.containsKey(en1.getKey())){
                    int l1 = en1.getValue().toString().length(), l2 = m2.get(en1.getKey()).toString().length();
                    mixed.put(en1.getKey(), (l1==l2?"=:":l1>l2?"1:":"2:")+en1.getValue());
                    m2.remove(en1.getKey());
                }
            }
        }
        Iterator it2 = m2.entrySet().iterator();
        while (it2.hasNext()){
            Map.Entry en = (Map.Entry) it2.next();
            if(en.getValue().toString().length()>1){
                mixed.put(en.getKey(), "2:" + en.getValue());
            }
        }
        return "";
    }

    @Test
    public void testFrequency(){
        Map map = frequency("my friend John has many many friends &");
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()){

        }
    }

}

