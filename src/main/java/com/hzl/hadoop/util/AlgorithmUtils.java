package com.hzl.hadoop.util;

import com.hzl.hadoop.exception.CommonException;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * description
 * 算法
 * 数组特点：有序可重复，固定大小
 *
 * @author hzl 2020/04/13 9:49 AM
 */
public class AlgorithmUtils {

	public static void main(String args[]){
		int[] array = {1, 7, 8, 9, 3,6,10,3,12,10};
		int[] result;



		System.out.println("归并排序结果"+Arrays.toString(sort(array)));
	}

	//===============================================================================
	//  选择排序,1,7,8,9,3(从左往右排序，排序完成后小的在右边，大的在左边)，
	// 冒泡排序和选择排序的区别，冒泡每次比较大小后都要交换位置，而选择排序是选出最大最小的后再交换位置
	// 比较次数：params.length*(params.length-1)/2
	//===============================================================================

	public static int[] selectionSort(int[] params) {

		int size = params.length;
		if (size <= 1) {
			throw new CommonException("数组大小必须大于等于2");
		}
		//用于存储排序后的最大值
		//int[] temp = new int[size];
		for (int i = 0; i < size - 1; i++) {
			int right = params[i];
			for (int j = i + 1; j < size; j++) {
				int left = params[j];
				System.out.println("right:" + right + "left:" + left);
				if (right < left) {
					params[i] = left;
					params[j] = right;
					right = left;
				}
			}
			System.out.println("每次外层循环结束后数组情况" + Arrays.toString(params));
		}
		return params;
	}

	//===============================================================================
	//  插入排序，1,2,3,4,5,8,6,9,7,10   其中5是标记位置，五左边的是有序的，右边是无序的。
	// 从5开始左边的数据依次和右边的比较大小，然后插入到右边合适的位置。
	// order为有序数数组（当前方法只支持左小右大），disOrder为无序数组
	//===============================================================================
	public static int[] insertSort(int[] order,int[] disOrder){


		for(int i=0;i<disOrder.length;i++){

			if(disOrder[i]>=order[i]){
				System.out.println("继续比较");
			}else{
				for(int j=0;j<order.length;j++){
					int temp=order[j];
					order[j]=disOrder[i];

				}

			}

		}



		return order;
	}

	//===============================================================================
	//快速排序，选择一个数字为基准，把大于这个数的放左边，小于这个数的放右边，递归重复上述操作，最终就是有序的序列
	//===============================================================================

	public void quick(int[] arrays){



	}

	//===============================================================================
	//  对象排序，1,2,3,4,5,8,6,9,7,10   其中5是标记位置，五左边的是有序的，右边是无序的。
	// 从5开始左边的数据依次和右边的比较大小，然后插入到右边合适的位置。
	// order为有序数数组（当前方法只支持左小右大），disOrder为无序数组
	//===============================================================================



	//===============================================================================
	//  栈，先进后出，子弹入膛
	//
	//
	//===============================================================================

	public void stack(){

	}

	//
	//已经知道父类，查询所有子类，包括子类的子类
	//方法1
	public Set<Long> handsb(List<Long> categoryIds){
		Set<Long> ids=new HashSet<>();
		Set<Long> idf=new HashSet<>();

		ids.addAll(categoryIds);
		idf.addAll(categoryIds);

		do{
			Set<Long> idc=new HashSet<>();

			idf.forEach(a->{
				Set<Long> sonIds=new HashSet<>();

				//根据categoryIds查询子类,如果子类为空就结束,暂时用new HashSet<>()，后期替换成查询语句
				idc.addAll(sonIds);
			});
			if(CollectionUtils.isEmpty(idc)){
				idf=null;
			}else{
				idf=idc;
				ids.addAll(idc);
			}

		}while (!CollectionUtils.isEmpty(idf));

		return ids;
	}

	//方法2
	//所有子类集合
	static Set<Long> allSon=new HashSet<>();

	public Set<Long> getAllSon(Set<Long> categoryIds){
		categoryIds.forEach(parentId->{
			//根据categoryIds查询子类,如果子类为空就结束,暂时用new HashSet<>()，后期替换成查询语句
			Set<Long> sonIds=new HashSet<>();
			if(CollectionUtils.isEmpty(sonIds)){
				return;
			}else{
				getAllSon(sonIds);
				allSon.addAll(sonIds);
			}
		});
		return allSon;
	}
	//方法2结束


	//归并排序，裂变程最小的数组大小为2，然后进行排序，然后把最小的单元从下往上合并排序
	// {1, 7, 8, 9, 3,6,10};


	public static int[] sort(int[] array){

		int length=array.length;

		if(length<2){
			return array;
		}

		int splitPosition=length/2;
		System.out.println(splitPosition);
		int[] left=Arrays.copyOfRange(array,0,splitPosition);
		int[] right=Arrays.copyOfRange(array,splitPosition,length);

		System.out.println("left:"+Arrays.toString(left)+"---"+"right:"+Arrays.toString(right));
		return merge(sort(left),sort(right));

	}

	public static int[] merge(int[] left,int[] right){
         int[] array=new int[left.length+right.length];
		//两个数组合并排序

		//判断left和right哪个大

		int leftI=0;
		int rightJ=0;
		int j=0;
		while (j<array.length){

			if(leftI!=left.length&&rightJ!=right.length){
				if(left[leftI]>right[rightJ]){
					array[j]=right[rightJ];
					rightJ++;
				}else if (left[leftI]<right[rightJ]){

					array[j]=left[leftI];
					leftI++;
				}else {
					array[j]=right[rightJ];
					j++;
					array[j]=left[leftI];
					rightJ++;
					leftI++;
				}
			}else if(rightJ==right.length){

				array[j]=left[leftI];
				leftI++;


			}else if(leftI==left.length){
				array[j]=right[rightJ];
				rightJ++;
			}
			j++;
		}

		return array;
	}








	//归并排序结束

	//堆排序 int[] array = {1, 7, 8, 9, 3,6,10};
	public static void heapSort(int arr[]) {
		int temp;

		for (int i = arr.length / 2 - 1; i >= 0; i--) {
			adjustHeap(arr, i, arr.length);
		}
		/**
		 * 将堆项元素与末尾元素交换，将最大元素"沉"到数组末端;
		 * 重新调整结构，使其满足堆定义，然后继续交换堆项元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序。
		 */

		for (int j = arr.length - 1; j > 0; j--) {
			temp = arr[j];
			arr[j] = arr[0];
			arr[0] = temp;
			adjustHeap(arr, 0, j);

		}


	}


	/**
	 * 将一个数组（二叉树）调整成一个大根堆
	 * 功能：完成将以i对应的非叶子结点的树调整成大顶堆
	 * 举例int arr[]={4, 6,8,5,9};=>i=1=> adjustHeap=>得到{4,9,8,5, 6}
	 * 如果我们再次调用adjustHeap 传入的是i=0=>得到{4,9, 8,5,6}=> {9,6,8,5, 4}
	 *
	 * @param arr    待调整的数组
	 * @param i      表示非叶子结点在数组中索引
	 * @param length 表示对多少个元素继续调整，length 是在逐渐的减少
	 */
	public static void adjustHeap(int arr[], int i, int length) {

		int temp = arr[i];//先取出当前元素的值，保存在临时变量
		//开始调整.
		//说明:k=i*2+1k是i结点的左子结点
		for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
			if (k + 1 < length && arr[k] < arr[k + 1]) {
				k++;
			}
			if (arr[k] > temp) {
				arr[i] = arr[k];
				i = k;
			} else {
				break;
			}
		}
		arr[i] = temp;


	}

	//堆排序结束

}
