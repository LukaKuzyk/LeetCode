import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {

    }

    public static class Test{
        int value;

        public void setValue(int value) {
            this.value = value;
        }

        public Test(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    public static int[] maxSlidingWindow(int[] nums, int k) {
        List<Integer> res = new ArrayList<>();

        for(int left = 0;left+k<=nums.length;left++) {
            int right = left + k, max = Integer.MIN_VALUE;
            for(int i = left; i<right; i++)
                max = Math.max(max,nums[i]);

            res.add(max);
        }
        int[] arr = new int[res.size()];
        for(int i = 0; i<res.size();i++)
            arr[i] = res.get(i);

        return arr;
    }
    public static String minWindow(String s, String t) {
        int lenS = s.length();
        int lenT = t.length();
        String output = "";

        if(lenT>lenS || s.isEmpty() || t.isEmpty()) return output;

        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i<lenT; i++)
            map.put(t.charAt(i), map.getOrDefault(t.charAt(i), 0)+1);

        int count = map.size(), min = s.length();

        int j = 0, i = 0;

        while (j<lenS){
            char rightChar = s.charAt(j++);
            if(map.containsKey(rightChar)){
                map.put(rightChar, map.get(rightChar)-1);
                if(map.get(rightChar)==0) count-=1;
            }
            if (count>0) continue;

            while (count==0){
                char leftChar = s.charAt(i++);
                if(map.containsKey(leftChar)){
                    map.put(leftChar, map.get(leftChar)+1);
                    if(map.get(leftChar)>0) count+=1;
                }
            }

            if(j-i<min){
                min = j-i;
                output = s.substring(i-1, j);
            }
        }


        return output;
    }
    public static boolean checkInclusion(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        if(len1>len2) return false;

        int[] target = new int[26];
        int[] window = new int[26];

        for(int i = 0; i<len1;i++){
            target[s1.charAt(i)-'a']++;
            window[s2.charAt(i)-'a']++;
        }
        for (int i =len1; i<len2;i++){
            if(Arrays.equals(target,window))
                return true;
            window[s2.charAt(i)-'a']++;
            window[s2.charAt(i-len1)-'a']--;
        }

        return Arrays.equals(target,window);
    }
    public static int lengthOfLongestSubstring(String s) {
        int max = 0;
        Map<Character, Integer> map = new HashMap<>();
        for(int left = 0, right = 0; right<s.length(); right++){
            char currChar = s.charAt(right);
            if(map.containsKey(currChar) && map.get(currChar)>=left){
                left=map.get(currChar)+1;
            }
            map.put(currChar, right);
            max = Math.max(max,right-left+1);
        }
        return max;
    }
    public static boolean searchMatrix(int[][] matrix, int target) {
        for(int i = 0; i<matrix.length; i++){
            if(search(matrix[i], target) != -1)
                return true;
        }
        return false;
    }
    public static int search(int[] nums, int target) {
        int len = nums.length, left = 0, right = len-1;
        while (right>=left){
            int middle = left + (right-left)/2;
            if(nums[middle] == target)
                return middle;
            else if(nums[middle]>target)
                right = middle-1;
            else
                left= middle+1;
        }
        return -1;
    }
    public static int largestRectangleArea(int[] heights) {
        int square = heights[0];
        for(int i = 0; i < heights.length;i++){
            int curr1 = heights[i], curr2 =0;
            if(i+1<heights.length)
                curr2 = Math.min(heights[i], heights[i+1])*2;
            int curr = Math.max(curr2,curr1);
            if(curr >= square)
                square = curr;

        }
        return square;
    } // ne hotovo
    public static int carFleet(int target, int[] position, int[] speed) {
        Map<Integer, Double> cars = new TreeMap<>(Collections.reverseOrder());
        for(int i = 0; i<position.length;i++)
            cars.put(position[i], (double) (target-position[i])/speed[i]);
        int fleet = 0; double time = 0;
        for(double current: cars.values()){
            if(current>time){
                time = current;
                fleet++;
            }
        }
        return fleet;
    }
    public static int[] dailyTemperatures(int[] temperatures) {
        int[] result = new int[temperatures.length];
        Stack stack = new Stack();
        for(int currDay = 0; currDay<temperatures.length;currDay++){
            while (!stack.isEmpty() && temperatures[currDay] > temperatures[(int)stack.peek()]){
                int prevDay = (int) stack.pop();
                result[prevDay] = currDay-prevDay;
            }
            stack.push(currDay);
        }
        return result;
    }
    public static List<String> generateParenthesis(int n) {
        List<String> output = new ArrayList<>();
        backtrack(output,"", 0,0, n);
        return output;
    }
    public static void backtrack(List<String> list, String currentString, int open, int close, int max){
        if(currentString.length() == max*2){
            list.add(currentString);
            return;
        }
        if(open<max) backtrack(list, currentString+"(",open+1, close, max );
        if(close<open) backtrack(list,currentString+")",open,close+1,max);
    }
    public static int evalRPN(String[] tokens) {
        Stack<Integer> nums = new Stack<>();
        for(int i = 0;i<tokens.length;i++){
            if(tokens[i] != "*" && tokens[i] != "/" && tokens[i] != "-" && tokens[i] != "+")
                nums.push(Integer.parseInt(tokens[i]));
            else{
                switch (tokens[i]){
                    case "+" ->{
                        int a = nums.pop();
                        int b = nums.pop();
                        nums.push(a+b);
                    }
                    case "-" ->{
                        int a = nums.pop();
                        int b = nums.pop();
                        nums.push(a-b);
                    }
                    case "*" ->{
                        int a = nums.pop();
                        int b = nums.pop();
                        nums.push(a*b);
                    }
                    case "/" ->{
                        int a = nums.pop();
                        int b = nums.pop();
                        nums.push(b/a);
                    }
                }
            }
        }
        return nums.peek();
    }
    public static boolean isValid(String s) {
        if(s.isEmpty() || s.length()%2!=0) return false;
        Stack<Character> stack = new Stack<>();
        for(char i:s.toCharArray()){
            if(i=='(' || i=='{' || i == '[')
                stack.push(i);
            else{
                if(stack.isEmpty())
                    return false;

                if((i==')' && stack.peek() == '(') || (i=='}' && stack.peek() == '{') ||(i==']' && stack.peek() == '['))
                    stack.pop();
                else
                    return false;
            }

        }
        return stack.isEmpty();
    }
    public static int trap(int[] height) {
        int water = 0;
        int left = 0, right = height.length-1, maxLeft = 0, maxRight = 0;

        while (left<right){
            if(height[left]<height[right]){
                if(height[left]>=maxLeft)
                    maxLeft=height[left];
                else
                    water+=maxLeft-height[left];
                left++;
            }else{
                if(height[right]>=maxRight)
                    maxRight=height[right];
                else
                    water+=maxRight-height[right];
                right--;
            }
        }
        return water;
    }
    public static boolean theBiggestNum(int[] nums, int target){
        int max = nums[0];
        for(int i = 0; i<nums.length;i++){
            if(nums[i]>max)
                max=nums[i];
        }
        return max == target;
    }
    public static int maxArea(int[] height) {
        int area = 0;
        int left = 0;
        int right = height.length-1;

        while (right>=left){
            int local_area = Math.min(height[right],height[left])*(right-left);
            if(local_area>area)
                area=local_area;
            if(height[left] < height[right])
                left++;
            else
                right--;
        }

        return area;
    }
    public static List<List<Integer>> threeSum(int[] nums) {
        int len = nums.length;
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        for(int i = 0;i<len-1;i++){
            int j = i+1;
            int k=len-1;
            int target = nums[i];
            while (j<k){
                if(nums[j] + nums[k] == -target){
                    List<Integer> local = new ArrayList<>(3);
                    local.add(nums[i]);
                    local.add(nums[j]);
                    local.add(nums[k]);
                    list.add(local);
                    break;
                } else if (nums[j]+nums[k]<=target) {
                    k--;
                }else
                    j++;
            }
        }
        return list.stream().map(el->el.stream().sorted().collect(Collectors.toList())).distinct().collect(Collectors.toList());
    }
    public static int[] twoSum(int[] numbers, int target) {

        int len = numbers.length;
        int left = 0;
        int right = len-1;

        while(left!=right){
            if(numbers[left] + numbers[right] == target)
                break;
            else if(numbers[left] + numbers[right] > target)
                right--;
            else
                left++;
        }

        return new int[] {left+1,right+1};
    }
    public static boolean isPalindrome(String s) {
        s = s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        for (short i = 0; i<s.length()/2;i++)
            if(s.charAt(i)!=s.charAt(s.length()-1-i))
                return false;
        return true;
    }
    public static int longestConsecutive(int[] nums) {
        int len = nums.length;
        if(len==0)return 0;

        Arrays.sort(nums);

        for (int x:nums)
            System.out.print(x+" ");
        System.out.println();

        short count = 1;
        short output = 1;
        for(short i =0;i<len; i++){
            if(i+1<len && nums[i+1]-nums[i]==0)continue;
            if(i+1<len && nums[i+1]-nums[i]==1) {
                count++;
                if(count>output)
                    output = count;
            }else
                count=1;
        }
        return output;
    }
    public static boolean isValidSudoku(char[][] board) {
        char[] checker = {'1','2','3','4','5','6','7','8','9'};

        //CHECKING OF COLUMNS
        for(short x = 0; x<9; x++){ // rows
            for (short y = 0; y<9;y++){ // columns
                if(board[x][y] != '.'){
                    if(!check(board[x][y], checker))
                        return false;
                }
            }
            recharge(checker);
        }

        //CHECKING OF ROWS
        for(short x = 0; x<9; x++){ // rows
            for (short y = 0; y<9;y++){ // columns
                if(board[y][x] != '.'){
                    if(!check(board[y][x], checker))
                        return false;
                }
            }
            recharge(checker);
        }
        //CHECKING OF EACH 3x3
        for(short a = 1; a<=3; a++) { // navigation between 3x3 cubes a=rows, b=columns
            for(short b = 1; b<=3; b++) {

                char[] basket = new char[9];
                for (int x = (3 * (a - 1)), I =0; x < 3 * a; x++) { // rows
                    for (int y = (3 * (b - 1)); y < 3 * b; y++) { // columns
                            basket[I++] = board[x][y];
                    }
                }
                for(char x: basket)
                    if(x!='.')
                        if(!check(x, checker))
                            return false;
                recharge(checker);
            }
        }




        return true;
    }
    public static boolean check(char x, char [] checker){
        for(short i = 0; i<9;i++){
            if(checker[i] == x){
                checker[i] = '_';
                return true;
            }
        }
        return false;
    }
    public static void recharge(char [] checker){
       checker[0] = '1';
       checker[1] = '2';
       checker[2] = '3';
       checker[3] = '4';
       checker[4] = '5';
       checker[5] = '6';
       checker[6] = '7';
       checker[7] = '8';
       checker[8] = '9';
    }
    public static int[] productExceptSelf(int[] nums) {
        int len = nums.length;
        int[] products = new int[len];
        products[0] = 1;
        for(int i=1;i<len;i++){
            int x = products[i-1]*nums[i-1];
            products[i]=x;
        }

        int rigth = nums[len-1];
        for(int i = len-2; i>=0; i--){
            products[i]*=rigth;
            rigth*=nums[i];
        }
        return products;
    }
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, List<Integer>> map = new HashMap<>();

        for(int num : nums){
            if(!map.containsKey(num))
                map.put(num,new ArrayList<>());
            map.get(num).add(num);
        }
        int[] res = new int[k];
        List<Integer> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys, (a,b) -> map.get(b).size() - map.get(a).size());
        for(int i = 0; i<k;i++)
            res[i] = keys.get(i);
        return res;
    }
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String,List<String>> map = new HashMap<>();
        for(String word: strs){

            char[] s = word.toCharArray();
            Arrays.sort(s);
            String sorted = new String(s);

            if(!map.containsKey(sorted))
                map.put(sorted, new ArrayList<>());

            map.get(sorted).add(word);
        }
        return new ArrayList<>(map.values());
    }
    static String sortString(String s){
        char[] s2 = s.toCharArray();
        Arrays.sort(s2);
        return Arrays.toString(s2);
    }
    public static boolean isAnagram(String s, String t) {
        char[] s2 = s.toCharArray();
        char[] t2 = t.toCharArray();
        Arrays.sort(s2);
        Arrays.sort(t2);
        return Arrays.equals(t2,s2);
    }
    public static boolean containsDuplicate(int[] nums) {
        return Arrays.stream(nums).distinct().count() < nums.length;
    }
    // Symbol       Value
    //I             1
    //IV            4
    //V             5
    //IX            9
    //X             10
    //XL            40
    //L             50
    //XC            90
    //C             100
    //D             500
    //CM            900
    //M             1000
    public static String intToRoman(int num) {
        StringBuilder output = new StringBuilder();
        while (num>=1000){
            num-=1000;
            output.append("M");
        }
        while (num>=900){
            num-=900;
            output.append("CM");
        }
        while (num>=500){
            num-=500;
            output.append("D");
        }
        while (num>=400){
            num-=400;
            output.append("CD");
        }
        while (num>=100){
            num-=100;
            output.append("C");
        }
        while (num>=90){
            num-=90;
            output.append("XC");
        }
        while (num>=50){
            num-=50;
            output.append("L");
        }
        while (num>=40){
            num-=40;
            output.append("XL");
        }
        while (num>=10){
            num-=10;
            output.append("X");
        }
        while (num>=9){
            num-=9;
            output.append("IX");
        }
        while (num>=5){
            num-=5;
            output.append("V");
        }
        while (num>=4){
            num-=4;
            output.append("IV");
        }
        while (num>=1){
            num-=1;
            output.append("I");
        }

        return output.toString();
    }
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        List list = new ArrayList<Integer>();
        for(int x:nums1)
            list.add(x);
        for(int x:nums2)
            list.add(x);
//        System.arraycopy();
        int[] arr = new int[list.size()];
        for (short i = 0; i < list.size(); i++)
            arr[i] = (int)list.get(i);
        Arrays.sort(arr);

        if(arr.length%2!=0)
            return arr[arr.length/2];
        else
            return (double) (arr[arr.length/2]+arr[arr.length/2-1])/2;
    }
}