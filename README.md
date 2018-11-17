```java
//Java数组练习
public class ArrayUtils{

    //1.计算数组中最大值
    public static int arrayMaxElement(int[] data){
		int max = data[0];
		for(int i = 0; i<data.length; i++){
			if(data[i] > max){
				max = data[i];
			}
		}
        return max;
    }
    
    //2.计算数组中最小值
    public static int arrayMinElement(int[] data){
		int min = data[0];
		for(int i = 0; i<data.length; i++){
			if(data[i] < min){
				min = data[i];
			}
		}
        return min;
    }
    
    
    //3.计算数组值之和
    public static int arrayElementSum(int[] data){
        int sum = 0;
		for(int i = 0; i<data.length; i++){
			sum += data[i];
		}
        return sum;
    }
    
    //4.数组拼接
    public static int[] arrayJoin(int[] a, int[] b){
		int[] c = new int[a.length + b.length];
		int i = 0;
		for(i=0; i<a.length; i++){
			c[i] = a[i];
		}
		for(i=a.length; i<c.length; i++){
			c[i] = b[i-a.length];
		}
		System.out.print("数组拼接：");
        return c;
    } 

    //5.数组截取
    //[start, end)
    public static int[] arraySub(int[] data, int start , int end){
        int[] c = new int[end - start];
		int i = 0;
		for(i=start; i<end-start; i++){
			c[i] = data[i];
		}
		System.out.print("数组截取：");
        return c;
    }
    
    //6.数组打印
    public static void printArray(int[] data){
        int i = 0;
		for(i=0; i<data.length; i++){
			System.out.print(data[i] +"\t");
		}
		System.out.println();
    }
    
    //7.数组反转
    // 比如：[1,2,3,4] => [4,3,2,1]
    public static void printReversal(int[] data){
        int left = 0;
		int right = data.length - 1;
		while(left < right){
			int tmp = data[left];
			data[left] = data[right];
			data[right] = tmp;
			left++;
			right--;
		}
		System.out.print("数组反转：");
		printArray(data);	
    }
    
    public static void main(String[] args){
		int[] x = new int[] {1,3,5,6};
		int[] y = new int[] {2,4,7,8};
		
		System.out.print("数组打印：");
		printArray(x);
		printReversal(x);
		printArray(arrayJoin(x,y));	
		printArray(arraySub(x, 0, 3));
		System.out.println("数组x的和："+arrayElementSum(x));
		System.out.println("数组x的最大值："+arrayMaxElement(x));
		System.out.println("数组x的最小值："+arrayMinElement(x));	
    }
}
```
