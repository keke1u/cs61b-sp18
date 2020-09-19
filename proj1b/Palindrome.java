public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque deque = new LinkedListDeque();
        int length = word.length();
        for (int i = length - 1; i >= 0; i -= 1) {
            char charAtIndex = word.charAt(i);
            deque.addFirst(charAtIndex);
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isCharEqual(wordDeque, 1);
    }

    private boolean isCharEqual(Deque<Character> items, int start) {
        if (items.size() == 0 || items.size() == 1) {
            return true;
        }
        char front = items.removeFirst();
        char last = items.removeLast();
        if (front == last) {
            return isCharEqual(items, start + 1);
        } else {
            return false;
        }
    }

    private boolean isCharEqual(Deque<Character> items, int start, CharacterComparator cc) {
        if (items.size() == 0 || items.size() == 1) {
            return true;
        }
        char front = items.removeFirst();
        char last = items.removeLast();
        if (cc.equalChars(front, last)) {
            return isCharEqual(items, start + 1, cc);
        } else {
            return false;
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isCharEqual(wordDeque, 1, cc);
    }

}