import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Algorithms
{
	private static void swap(double[] arr, int index1, int index2)
	{
		double temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}


	static void BubbleSort(double[] arr)
	{
		for(int i = 0; i < arr.length; i++)
			for(int j = i + 1; j < arr.length; j++)
			{
				SortVisualizer.setActiveElements(i, j);
				if(arr[i] > arr[j])
				{
					swap(arr, i, j);
					SortVisualizer.reDrawWithSleep();
				}
			}
	}


	static void SelectionSort(double[] arr)
	{
		double highestVal; 
		int highestIndex;
		for(int i = 0; i < arr.length; i++)
		{
			highestIndex = i;
			highestVal = arr[i];
			for(int j = i + 1; j < arr.length; j++)
			{
				SortVisualizer.setActiveElements(i, j);
				if(arr[j] < highestVal)
				{
					highestIndex = j;
					highestVal = arr[j];
				}
				SortVisualizer.reDrawWithSleep(15);
			}
			swap(arr, i, highestIndex);
			SortVisualizer.reDrawWithSleep(15);
		}
	}


	static void InsertionSort(double[] arr)
	{
        for (int i=1; i < arr.length; ++i) 
        { 
            double temp = arr[i]; 
            int j = i - 1; 
  
            while (j >= 0 && arr[j] > temp) 
            { 
				SortVisualizer.setActiveElements(i, j);
                arr[j + 1] = arr[j--];
				SortVisualizer.reDrawWithSleep(15);
            } 
			arr[j + 1] = temp; 
			SortVisualizer.reDrawWithSleep(15);
        } 
	}


	static void MergeSort(double[] arr, int left, int right)
	{
		if(left >= right)
			return;
		int mid = (left + right) / 2;
		MergeSort(arr, left, mid);
		MergeSort(arr, mid + 1, right);
		MergeHelper(arr, left, mid, right);
	}


	private static void MergeHelper(double[] arr, int left, int mid, int right)
	{
		double[] leftArr = new double[mid - left + 1];
		double[] rightArr = new double[right - mid];

		for(int i = 0; i < mid - left + 1; i++)
			leftArr[i] = arr[left + i];
		for(int j = 0; j < right - mid; j++)
			rightArr[j] = arr[mid + j + 1];
		
		int i = 0, j = 0, index = left;
		while(i < mid - left + 1 && j < right - mid)
		{
			SortVisualizer.setActiveElements(index+i, index+j);
		 	if(leftArr[i] <= rightArr[j])
				arr[index++] = leftArr[i++];
			else
				arr[index++] = rightArr[j++];
			SortVisualizer.reDrawWithSleep();
		}
		while(i < mid - left + 1)
		{
			SortVisualizer.setActiveElements(index, index+i);
			arr[index++] = leftArr[i++];
			SortVisualizer.reDrawWithSleep();
		}
		while(j < right - mid)
		{
			SortVisualizer.setActiveElements(index, index+j);
			arr[index++] = rightArr[j++];
			SortVisualizer.reDrawWithSleep();
		}
	}
}

public class SortVisualizer extends JFrame
{
	static SortVisualizer s;
	static double[] arr;
	static int width = 10;
	static int size = 51;
	static int maxHeight = 20;
	static int padding = 50;
	/* in milliseconds */
	static int delayBetweenSwap = 25;
	/* to show visually, which elements are being inspected */
	static int comparingLeft = 0, comparingRight = 0;
	static boolean isSorting = false;
	static long startSortingTime = 0;
	static int sortPerformance = 0;
	static Thread t;


	public static void main(String[] args)
	{
		s = new SortVisualizer();

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

		s.add(p);
		s.add(sortButtons, BorderLayout.SOUTH);
		s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		s.setSize(600, 600);
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
			{
				g.setColor(Color.red);
				g.fillRect(padding + i * width - 10, (int)(460 - 20 * arr[i]) + padding, width, (int)(20 * arr[i]));
			}
			else if(i == comparingRight && comparingRight != 0)
			{
				g.setColor(Color.blue);
				g.fillRect(padding + i * width - 10, (int)(460 - 20 * arr[i]) + padding, width, (int)(20 * arr[i]));
			}
			else
			{
				g.setColor(Color.black);
				g.fillRect(padding + i * width - 10, (int)(460 - 20 * arr[i]) + padding, width, (int)(20 * arr[i]));
			}
		}

		if(isSorting)
			sortPerformance = Math.toIntExact(System.currentTimeMillis() - startSortingTime);
		g.setFont(new Font("Monospaced", Font.BOLD, 18));
		g.setColor(Color.black);
		g.drawString("Performance: " + sortPerformance + " milliseconds", 150, 550);
	}
}
