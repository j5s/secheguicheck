package cc.ccon.heguicheck;

import java.util.Map;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;



/**
 * 自定义Hook回调监听器
 * @author JasonChen
 * @email chenjunsen@outlook.com
 * @createTime 2021/7/6 9:15
 */
public abstract class DumpMethodHook extends XC_MethodHook {
    String p = " - sechegui - %s";
    DumpMethodHook(String pn){
        p = String.format(p, pn);
    }
    /**
     * 该方法会在Hook了指定方法后调用
     * @param param
     */
    @Override
    protected void afterHookedMethod(MethodHookParam param) {
        //在这里，我们dump一下调用的方法栈信息
        //String p = param.getClass().getName();
        dump2(p);
    }

    /**
     * dump模式一:根据线程进行过滤
     */
    private static void dump() {
        for (Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()) {
            Thread thread = (Thread) stackTrace.getKey();
            StackTraceElement[] stack = (StackTraceElement[]) stackTrace.getValue();
            // 进行过滤
            if (thread.equals(Thread.currentThread())) {
                continue;
            }
            XposedBridge.log("[Dump Stack]" + "**********线程名字：" + thread.getName() + "**********");
            int index = 0;
            for (StackTraceElement stackTraceElement : stack) {
                XposedBridge.log("[Dump Stack]" + index + ": " + stackTraceElement.getClassName()
                        + "----" + stackTraceElement.getFileName()
                        + "----" + stackTraceElement.getLineNumber()
                        + "----" + stackTraceElement.getMethodName());
            }
            // 增加序列号
            index++;
        }
        XposedBridge.log("[Dump Stack]" + "********************* over **********************");
    }

    /**
     * dump模式2：类信通院报告模式
     */
    private static void dump2(String p){
        XposedBridge.log("[Dump Stack]: "+"---------------start----------------" + p);
        Throwable ex = new Throwable();

        StackTraceElement[] stackElements = ex.getStackTrace();
        if (stackElements != null) {
            for (int i= 0; i < stackElements.length; i++) {
                StringBuilder sb=new StringBuilder("[方法栈调用]");
                sb.append(i);
                XposedBridge.log("[Dump Stack]"+i+": "+ stackElements[i].getClassName()
                        +"----"+stackElements[i].getFileName()
                        +"----" + stackElements[i].getLineNumber()
                        +"----" +stackElements[i].getMethodName() + p);
            }
        }
        XposedBridge.log("DumpStack: "+ "---------------over----------------" + p);
    }
}
