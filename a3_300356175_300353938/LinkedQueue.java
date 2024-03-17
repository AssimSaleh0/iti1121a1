/**
 * @author Marcel Turcotte, Guy-Vincent Jourdan and Mehrdad Sabetzadeh
 *         (University of Ottawa)
 * 
 * 
 */

// TODO:
// 1) keep track of size and implement a size() method
// 2) write a peek method

public class LinkedQueue<D> implements Queue<D> {

	private static class Elem<T> {
		private T value;
		private Elem<T> next;

		private Elem(T value, Elem<T> next) {
			this.value = value;
			this.next = next;
		}
	}

	private Elem<D> front;
	private Elem<D> rear;
    private int size; // Instance variable to keep track of the queue size

	public LinkedQueue() {
		front = rear = null;
		size = 0; // initialize the size to zero
	}

	public boolean isEmpty() {
		return front == null;
	}

	public void enqueue(D newElement) {

		if (newElement == null) {
			throw new NullPointerException("no null object in my queue !");
			// could have been IllegalArgumentException but NPE seems
			// to be the norm
		}

		Elem<D> newElem;
		newElem = new Elem<D>(newElement, null);
		if (isEmpty()) {
			front = newElem;
			rear = newElem;
		} else {
			rear.next = newElem;
			rear = newElem;
		}
		size++; // Increment the size when a new element is added
	}

	public D dequeue() {

		if (isEmpty()) {
			throw new IllegalStateException("Dequeue method called on an empty queue");
		}

		D returnedValue = front.value;
		front = front.next;
	
		if (front == null) { // If the queue is now empty after dequeueing
			rear = null;
		}
	
		size--; // Decrement the size of the queue
	
		return returnedValue;
	}

	public D peek() {
		if (isEmpty()) {
            throw new IllegalStateException("Peek method called on an empty queue");
        }
        return front.value; // Return the value at the front without removing it
    }

	public int size() {
        return size; // Return the size
	}

	public String toString() {

		StringBuffer returnedValue = new StringBuffer("[");

		if (!isEmpty()) {
			Elem<D> cursor = front;
			returnedValue.append(cursor.value);
			while (cursor.next != null) {
				cursor = cursor.next;
				returnedValue.append(", " + cursor.value);
			}
		}

		returnedValue.append("]");
		return returnedValue.toString();

	}
}
