package com.thinkgem.jeesite.test;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

import com.google.common.collect.Maps;

public class ThreadTest {

	public static void main(String[] args) {
		/*
		 * A a = new A(); a.getA().put("a", "aa");
		 * 
		 * A b = new A(); String aaaa = (String) b.getA().get("a");
		 * System.out.println();
		 */

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					A1.init();
				}
			}
		}).start();
		
		while (true) {
			A1.init();
		}
	}

}

class A1 {
	private int left = 100;
	private static A1 a1;

	static Lock lock = new ReentrantLock();
	private A1() {
	};

	public static A1 init() {
		
		lock.lock();
		try {
			if (a1 == null)
				a1 = new A1();
			a1.del();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			lock.unlock();
		}
		return a1;
	}

	private void del() {
		try {
			Thread.currentThread().sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (left >= 0) {
			System.out.println("线程 " + Thread.currentThread() + " 卖掉一张,还剩 "
					+ left-- + " 张票");
		}
	}
}

/*
 * class A{ private static HashMap<Object, Object> a = Maps.newHashMap();
 * 
 * public static HashMap<Object, Object> getA() { return a; }
 * 
 * public static void setA(HashMap<Object, Object> a) { A.a = a; } }
 */