import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

/**
 * 1) ���������� ��������� ���������. ������ ��������� (������������) ������ �
 * ArrayIndexOutOfBoundException? ��� ���� �������, ����� ����� �� �����������?
 * 2) ������ ���������� ��������� ���������� ������ � run() �� 10 � ��������
 * ����� �� ������ print() ����� ���������� ������ ��������. ������ ����������
 * ConcurrentModificationException? ��� �������, ����� ����� �� �����������?
 *
 */
public class SynchronizedListTutorTask1Ver3 {
	static String[] langs = { "SQL", "PHP", "XML", "Java", "Scala", "Python",
			"JavaScript", "ActionScript", "Clojure", "Groovy", "Ruby", "C++" };

	Object monitor = new Object();

	List<String> randomLangs = new ArrayList<String>();

	public String getRandomLang() {
		int index = (int) (Math.random() * langs.length);
		return langs[index];
	}

	// ============== TestThread =========================================
	class TestThread implements Runnable {
		String threadName;

		public TestThread(String threadName) {
			this.threadName = threadName;
		}

		@Override
		public void run() {

			for (int i = 0; i < 10; i++) {
				synchronized (monitor) {
					randomLangs.add(getRandomLang());
					print(randomLangs);
				}
			}

		}
	}

	// =======================================================
	public void print(Collection<?> c) {

		StringBuilder builder = new StringBuilder();
		
		Iterator<?> iterator = c.iterator();
		while (iterator.hasNext()) {
			builder.append(iterator.next());
			builder.append(" ");
		}

		// System.out.println(builder.toString());
	}

	@Test
	public void testThreadVer3() {
		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < 100; i++) {
			threads.add(new Thread(new TestThread("t" + i)));
		}

		System.out.println("Starting threads");
		for (int i = 0; i < 100; i++) {
			threads.get(i).start();
		}

		System.out.println("Waiting for threads");
		try {
			for (int i = 0; i < 100; i++) {
				threads.get(i).join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();

		}

	}

}