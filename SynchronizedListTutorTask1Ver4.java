import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/**
 * 1) Попробуйте запустить программу. Почему программа (периодически) падает с
 * ArrayIndexOutOfBoundException? Что надо сделать, чтобы этого не происходило?
 * 2) Теперь попробуйте уменьшить количество циклов в run() до 10 и добавить
 * вывод на печать print() после добавления нового элемента. Почему происходит
 * ConcurrentModificationException? Что сделать, чтобы этого не происходило?
 *
 */
public class SynchronizedListTutorTask1Ver4 {
	static String[] langs = { "SQL", "PHP", "XML", "Java", "Scala", "Python",
			"JavaScript", "ActionScript", "Clojure", "Groovy", "Ruby", "C++" };

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
			List<String> bufferList = new LinkedList<String>(randomLangs);
			for (int i = 0; i < 10; i++) {
				bufferList.add(getRandomLang());
				print(randomLangs);
			}
			randomLangs = bufferList;

		}
	}

	// =======================================================
	public void print(Collection<?> c) {

		StringBuilder builder = new StringBuilder();
		Collection<?> list = new ArrayList<>(c);
	
		Iterator<?> iterator = list.iterator();
		while (iterator.hasNext()) {
			builder.append(iterator.next());
			builder.append(" ");
		}

		// System.out.println(builder.toString());
	}

	@Test
	public void testThreadVer4() {
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