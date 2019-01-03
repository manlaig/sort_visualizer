class Algorithms
{
	static void BubbleSort(double[] arr)
	{
		for(int i = 0; i < arr.length; i++)
			for(int j = i + 1; j < arr.length; j++)
			{
				SortVisualizer.setActiveElements(i, j);
				if(arr[i] > arr[j])
				{
					Helpers.swap(arr, i, j);
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
				SortVisualizer.reDrawWithSleep(13);
			}
			Helpers.swap(arr, i, highestIndex);
			SortVisualizer.reDrawWithSleep(13);
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
				SortVisualizer.reDrawWithSleep(13);
            } 
			arr[j + 1] = temp; 
			SortVisualizer.reDrawWithSleep(13);
        } 
	}


	static void MergeSort(double[] arr, int left, int right)
	{
		if(left >= right)
			return;
		int mid = (left + right) / 2;
		MergeSort(arr, left, mid);
		MergeSort(arr, mid + 1, right);
		Helpers.MergeHelper(arr, left, mid, right);
	}
  
  
    static void QuickSort(double arr[], int low, int high) 
    { 
        if (low < high) 
        { 
            /* pi is partitioning index, arr[pi] is  
              now at right place */
            int pi = Helpers.partition(arr, low, high); 
  
            // Recursively sort elements before 
            // partition and after partition 
            QuickSort(arr, low, pi-1); 
            QuickSort(arr, pi+1, high); 
        } 
    }
}

class Helpers
{
    static void swap(double[] arr, int index1, int index2)
	{
		double temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}


    static void MergeHelper(double[] arr, int left, int mid, int right)
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


    static int partition(double arr[], int low, int high) 
    { 
        double pivot = arr[high];  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) 
        { 
            SortVisualizer.setActiveElements(i, j);
            if (arr[j] <= pivot) 
            { 
                i++; 
                swap(arr, i, j);
                SortVisualizer.reDrawWithSleep(50);
            } 
        } 

        swap(arr, i+1, high);
        SortVisualizer.reDrawWithSleep(50);
  
        return i+1; 
    } 
}