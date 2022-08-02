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

	public static void main(String args[]) {
		int[] array = {1, 7, 8, 9, 3,6};
		int[] result;
		//调用冒泡排序
		long beginTime = System.currentTimeMillis();
		System.out.println("开始时间" + beginTime);

		System.out.println(beginTime);
		result = selectionSort(array);
		long endTime = System.currentTimeMillis();

		System.out.println("排序后结果"+result);

		System.out.println("结束时间" + endTime);
		System.out.println("耗时" + (endTime - beginTime));

	}

	//===============================================================================
	//  选择排序,1,7,8,9,3(从左往右排序，排序完成后小的在右边，大的在左边)
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
}
