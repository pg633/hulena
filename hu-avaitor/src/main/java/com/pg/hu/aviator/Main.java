//package com.pg.hu.aviator;
//
//import com.googlecode.aviator.AviatorEvaluator;
//import com.googlecode.aviator.Expression;
//import com.googlecode.aviator.runtime.JavaMethodReflectionFunctionMissing;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author lianzheng04
// * @version 1.0
// * @date 2020/9/17 5:45 下午
// */
//public class Main {
//    //AviatorEvaluator.setMathContext(MathContext.DECIMAL64); 设置精度
//    public static void main(String[] args) {
////        FUNC1();
////        FUNC2();
////        FUNC3();
////        AviatorEvaluator.addFunction(new AddFunction());
////        System.out.println(AviatorEvaluator.execute("add(1, 2)"));           // 3.0
////        System.out.println(AviatorEvaluator.execute("add(add(1, 2), 100.0)")); // 103.0
////
////        System.out.println(AviatorEvaluator.exec("a>0? 'yes':'no'", 1));
//
////        FUNC4();
////        FUNC5();
////        FUNC6();
////        FUNC7();
//
//
////        var list = new ArrayList<String>();
////        list.add("asd");
////
////        System.out.println(list);
//////        var x = new Main();
////
////        for (var x : list) {
////            System.out.println(x);
////        }
////
////        try (var x = File.listRoots) {
////
////        } catch (Exception e) {
//
//
//    }
//
//    private static void FUNC7() {
//        Map<String, Object> env = new HashMap<String, Object>();
//        ArrayList<Integer> list = new ArrayList<Integer>();
//        list.add(3);
//        list.add(20);
//        list.add(10);
//        env.put("list", list);
//        Object result = AviatorEvaluator.execute("count(list)", env);
//        System.out.println(result);  // 3
//        result = AviatorEvaluator.execute("reduce(list,+,0)", env);
//        System.out.println(result);  // 33
//        result = AviatorEvaluator.execute("filter(list,seq.gt(9))", env);
//        System.out.println(result);  // [10, 20]
//        result = AviatorEvaluator.execute("include(list,10)", env);
//        System.out.println(result);  // true
//        result = AviatorEvaluator.execute("sort(list)", env);
//        System.out.println(result);  // [3, 10, 20]
//        AviatorEvaluator.execute("map(list,println)", env);
//
//    }
//
//    private static void FUNC6() {
//
////        String email = "killme2008@gmail.com";
////        Map<String, Object> env = new HashMap<String, Object>();
////        env.put("email", email);
////        String username = (String) AviatorEvaluator.execute("email=~/([\\w0-8]+)@\\w+[\\.\\w+]+/ ? $1 : 'unknow' ", env);
////        System.out.println(username);
//
//        Object rt = AviatorEvaluator.exec("9223372036854775807100.356M * 2");
//        System.out.println(rt + " " + rt.getClass());  // 18446744073709551614200.712 class java.math.BigDecimal
//        rt = AviatorEvaluator.exec("92233720368547758074+1000");
//        System.out.println(rt + " " + rt.getClass());  // 92233720368547759074 class java.math.BigInteger
//        BigInteger a = new BigInteger(String.valueOf(Long.MAX_VALUE) + String.valueOf(Long.MAX_VALUE));
//        BigDecimal b = new BigDecimal("3.2");
//        BigDecimal c = new BigDecimal("9999.99999");
//        rt = AviatorEvaluator.exec("a+10000000000000000000", a);
//        System.out.println(rt + " " + rt.getClass());  // 92233720368547758089223372036854775807 class java.math.BigInteger
//        rt = AviatorEvaluator.exec("b+c*2", b, c);
//        System.out.println(rt + " " + rt.getClass());  // 20003.19998 class java.math.BigDecimal
//        rt = AviatorEvaluator.exec("a*b/c", a, b, c);
//        System.out.println(rt + " " + rt.getClass());  // 2.951479054745007313280155218459508E+34 class java.math.BigDecimal
//
//
//    }
//
//    private static void FUNC5() {
//        final List<String> list = new ArrayList<String>();
//        list.add("hello");
//        list.add(" world");
//        final int[] array = new int[3];
//        array[0] = 0;
//        array[1] = 1;
//        array[2] = 3;
//        final Map<String, Date> map = new HashMap<String, Date>();
//        map.put("date", new Date());
//        Map<String, Object> env = new HashMap<String, Object>();
//        env.put("list", list);
//        env.put("array", array);
//        env.put("mmap", map);
//        System.out.println(AviatorEvaluator.execute("list[0]+list[1]", env));   // hello world
//        System.out.println(AviatorEvaluator.execute("'array[0]+array[1]+array[2]=' + (array[0]+array[1]+array[2])", env));  // array[0]+array[1]+array[2]=4
//        System.out.println(AviatorEvaluator.execute("'today is ' + mmap.date ", env));  // today is Wed Feb 24 17:31:45 CST 2016
//    }
//
//    private static void FUNC4() {
//        String expression = "a-(b-c)>100";
//        // 编译表达式
//        Expression compiledExp = AviatorEvaluator.compile(expression);
//        Map<String, Object> env = new HashMap<String, Object>();
//        env.put("a", 100.3);
//        env.put("b", 45);
//        env.put("c", -199.100);
//        // 执行表达式
//        Boolean result = (Boolean) compiledExp.execute(env);
//        System.out.println(result);
//    }
//
//    private static void FUNC3() {
//        System.out.println(AviatorEvaluator.execute("   123  +  345  + '123'    "));
//        System.out.println(AviatorEvaluator.execute("  '123  +  345'     "));
//        String name = "dennis";
//        System.out.println(AviatorEvaluator.exec(" 'hello ' + yourName ", name));
//        System.out.println(AviatorEvaluator.execute("string.length('hello')"));
//        System.out.println(AviatorEvaluator.execute("string.substring('hello', 1, 2)"));
//
//        System.out.println(AviatorEvaluator.execute("string.contains(\"test\", string.substring('hello', 1, 2))"));
//
////        System.out.println(AviatorEvaluator.execute(" 'a\"b' "));
////        System.out.println(AviatorEvaluator.execute(" \"a\'b\" "));
////        System.out.println(AviatorEvaluator.execute(" 'hello ' + 3 "));
////        System.out.println(AviatorEvaluator.execute(" 'hello '+ unknow "));
//
//    }
//
//    private static void FUNC2() {
//        String yourName = "Michael";
//        Map<String, Object> env = new HashMap<String, Object>();
//        env.put("yourName", yourName);
//        String result = (String) AviatorEvaluator.execute(" 'hello ' + yourName ", env);
//        System.out.println(result);  // hello Michael
//
//
//    }
//
//    private static void FUNC1() {
//        // 启用基于反射的方法查找和调用
//        AviatorEvaluator.setFunctionMissing(JavaMethodReflectionFunctionMissing.getInstance());
//        // 调用 String#indexOf
//        System.out.println(AviatorEvaluator.execute("indexOf('hello world', 'w')"));
//        // 调用 Long#floatValue
//        System.out.println(AviatorEvaluator.execute("floatValue(3)"));
//        // 调用 BigDecimal#add
//        System.out.println(AviatorEvaluator.execute("add(3M, 4M)"));
//
//    }
//}
