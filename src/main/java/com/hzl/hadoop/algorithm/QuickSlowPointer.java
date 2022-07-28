package com.hzl.hadoop.algorithm;

import java.util.Arrays;

/**
 * description
 *
 * @author hzl 2022/07/22 5:58 PM
 */
public class QuickSlowPointer {


	/*	给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。

		请注意 ，必须在不复制数组的情况下原地对数组进行操作。 
     	示例 1:

		输入: nums = [0,1,0,3,12]
		输出: [1,3,12,0,0]
		示例 2:

		输入: nums = [0]
		输出: [0]

		作者：力扣 (LeetCode)
		链接：https://leetcode.cn/leetbook/read/all-about-array/x9rh8e/
		来源：力扣（LeetCode）
		著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

		*/
	public static void moveZeroes(int[] nums) {
		int zeroTop = 0;
		for (int top = 0; top < nums.length; top++) {
			if (nums[top] != 0 && top == zeroTop) {
				nums[zeroTop] = nums[top];
				zeroTop++;
			} else if (nums[top] != 0 && top != zeroTop) {
				nums[zeroTop] = nums[top];
				nums[top] = 0;
				zeroTop++;
			} else if (nums[top] == 0 && top == 0) {
				zeroTop = top;
			}
		}

	}

	public static void moveZeroes1(int[] nums) {
		int zeroTop = 0;
		for (int top = 0; top < nums.length; top++) {
			if (nums[top] != 0) {
				int temp = nums[zeroTop];
				nums[zeroTop] = nums[top];
				nums[top] = temp;
				zeroTop++;
				//System.out.println(Arrays.toString(nums));
			}
		}
	}


	/*
	* 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。

	不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。

	元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。

	作者：力扣 (LeetCode)
	链接：https://leetcode.cn/leetbook/read/all-about-array/x9p1iv/
	来源：力扣（LeetCode）
	著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
	*
	*
	*
	*
	* */
	public static int removeElement(int[] nums, int val) {
		int position = 0;
		int size = 0;
		for (int num : nums) {
			if (num != val) {
				int temp = nums[size];
				nums[size] = nums[position];
				nums[position] = temp;
				size++;
			}
			position++;
		}
		return size;
	}

	public static int removeDuplicates(int[] nums) {
		int position = 0;
		int size = 0;
		for (int num : nums) {
			if (nums[position] == nums[size]) {
				nums[position] = nums[size];
			}else{
				position++;
				nums[position] = nums[size];
			}
			size++;
		}
		return position+1;

	}


/*	给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。

	不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。

	作者：力扣 (LeetCode)
	链接：https://leetcode.cn/leetbook/read/all-about-array/x9nivs/
	来源：力扣（LeetCode）
	著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
	*/
	public static int removeDuplicates2(int[] nums) {
		int range=0;
		int position = 0;
		int size=0;
		for (int num : nums) {
			if (nums[position] == num) {
				if(range==0){
					nums[position] =num;
					size++;
				}
				if(range==1){
					nums[position+1]=num;
					size++;
				}
				range++;

			}else{
				if(range>1){
					range=1;
					position=position+2;
					nums[position] = num;
					size++;
				}else if(range==1) {
					range=1;
					position=position+1;
					nums[position] =num;
					size++;

				}
			}
		}
		return size;

	}

	//todo 待后面在研究
	public static int removeDuplicates3(int[] nums) {

		int position=0;

		for(int i=1;i<nums.length;i++){

			if(nums[position]==nums[i]){

				nums[position++]=nums[i];
			}

		}
		return 1;
	}


	public static void main(String args[]) {

		//int arrays[]=new int[]{0,1,0,3,12};
		//int arrays[]=new int[]{1};
		//int arrays[] = new int[]{0,0,1,1,1,1,2,3,3};
		int arrays[] = new int[]{1,1,1};

		//moveZeroes1(arrays);

		int size = removeDuplicates2(arrays);

		System.out.println(Arrays.toString(arrays));
		System.out.println(size);
	}
}
