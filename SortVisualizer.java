import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SortVisualizer extends JFrame
{
	static SortVisualizer s;
	static double[] arr;
	static int width = 10;
	static int size = 51;
	static int maxHeight = 20;
	static int padding = 50;
	/* in milliseconds */
	static int delayBetweenSwap = 26;
	/* to show visually, which elements are being inspected */
	static int comparingLeft = 0, comparingRight = 0;
	static boolean isSorting = false;
	static long startSortingTime = 0;
	static int sortPerformance = 0;
	static Thread t;

	SortVisualizer()
	{
		setLayout(new FlowLayout());
		JPanel p = new JPanel();
		JButton b1 = new JButton("Shuffle Array");
		p.add(b1);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(t != null && t.isAlive())
					return;
				fillArray();
				reDraw();
			}
		});

		add(p);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 600);
	}


	public static void main(String[] args)
	{
		s = new SortVisualizer();

		JPanel sortButtons = new JPanel();
		JButton bubbleSort = new JButton("Bubble Sort");
		sortButtons.add(bubbleSort);
		bubbleSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(t != null && t.isAlive())
					return;
				t = new Thread()
				{
					public void run()
					{
						sortingStarted();
						Algorithms.BubbleSort(arr);
						comparingLeft = comparingRight = 0;
						sortingEnded();
						reDraw();
					}
				};
				t.start();
			}
		});
		
		JButton selectionSort = new JButton("Selection Sort");
		sortButtons.add(selectionSort);
		selectionSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(t != null && t.isAlive())
					return;
				t = new Thread()
				{
					public void run()
					{
						sortingStarted();
						Algorithms.SelectionSort(arr);
						comparingLeft = comparingRight = 0;
						sortingEnded();
						reDraw();
					}
				};
				t.start();
			}
		});

		JButton insertionSort = new JButton("Insertion Sort");
		sortButtons.add(insertionSort);
		insertionSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(t != null && t.isAlive())
					return;
				t = new Thread()
				{
					public void run()
					{
						sortingStarted();
						Algorithms.InsertionSort(arr);
						comparingLeft = comparingRight = 0;
						sortingEnded();
						reDraw();
					}
				};
				t.start();
			}
		});

		JButton mergeSort = new JButton("Merge Sort");
		sortButtons.add(mergeSort);
		mergeSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(t != null && t.isAlive())
					return;
				t = new Thread()
				{
					public void run()
					{
						sortingStarted();
						Algorithms.MergeSort(arr, 0, arr.length - 1);
						comparingLeft = comparingRight = 0;
						sortingEnded();
						reDraw();
					}
				};
				t.start();
			}
		});

		JButton quickSort = new JButton("Quick Sort");
		sortButtons.add(quickSort);
		quickSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(t != null && t.isAlive())
					return;
				t = new Thread()
				{
					public void run()
					{
						sortingStarted();
						Algorithms.QuickSort(arr, 0, arr.length - 1);
						comparingLeft = comparingRight = 0;
						sortingEnded();
						reDraw();
					}
				};
				t.start();
			}
		});

		s.add(sortButtons);
		s.setVisible(true);
		
		fillArray();
	}


	private static void fillArray()
	{
		arr = new double[size];
		for(int i = 0; i < arr.length; i++)
			arr[i] = Math.random() * maxHeight;
	}


	private static void sortingStarted()
	{
		isSorting = true;
		startSortingTime = System.currentTimeMillis();
	}


	private static void sortingEnded()
	{
		isSorting = false;
		startSortingTime = 0;
	}


	synchronized static void reDrawWithSleep()
	{
		reDraw();
		sleepMilliseconds(delayBetweenSwap);
	}


	synchronized static void reDrawWithSleep(int delay)
	{
		reDraw();
		sleepMilliseconds(delay);
	}


	synchronized static void setActiveElements(int left, int right)
	{
		comparingLeft = left;
		comparingRight = right;
	}


	synchronized private static void sleepMilliseconds(int delay)
	{
		long timeStart = System.currentTimeMillis();
		while(System.currentTimeMillis() - timeStart < delay);
	}


	synchronized static void reDraw()
	{
		Thread t = new Thread()
		{
			public void run()
			{
				s.paint(s.getGraphics());
			}
		};
		t.start();
	}


	public void paint(Graphics g)
	{
		super.paint(g);
		for(int i = 1; i < arr.length; i++)
		{
			if(i == comparingLeft && comparingLeft != 0)
				g.setColor(Color.red);
			else if(i == comparingRight && comparingRight != 0)
				g.setColor(Color.blue);
			else
				g.setColor(Color.black);

			g.fillRect(padding + i * width - 10, (int)(475 - 20 * arr[i]) + padding, width, (int)(20 * arr[i]));
		}

		if(isSorting)
			sortPerformance = Math.toIntExact(System.currentTimeMillis() - startSortingTime);
		g.setFont(new Font("Monospaced", Font.BOLD, 18));
		g.setColor(Color.black);
		g.drawString("Performance: " + sortPerformance + " milliseconds", 150, 550);
	}
}
